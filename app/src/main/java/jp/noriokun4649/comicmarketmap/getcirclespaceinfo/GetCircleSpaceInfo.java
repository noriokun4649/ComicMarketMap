/*
 * Copyright (c) 2018 noriokun4649.
 */

package jp.noriokun4649.comicmarketmap.getcirclespaceinfo;

import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.noriokun4649.comicmarketmap.list.Circle;
import jp.noriokun4649.comicmarketmap.list.CircleListItemAdapter;

/**
 * サークルのパターンマッチングを行うクラス.
 */
public class GetCircleSpaceInfo {
    /**
     * ハンドラー.
     * このインスタンスを通して、じゃないとアプリの画面等を操作できません.
     */
    private static Handler mHandler = new Handler();

    /**
     * 文字列からブロックの情報を抽出するメソッド.
     *
     * @param indata 抽出対象の文字列を渡します。
     * @return ブロックの抽出結果
     */
    @SuppressWarnings("LoopStatementThatDoesntLoop")
    public String getBlock(final String indata) {
        String indataNew = indata;
        indataNew = Normalizer.normalize(indataNew, Normalizer.Form.NFKC);
        indataNew = indataNew
                //.replaceAll("C95","")
                .replaceAll("C96","")
                .replaceAll("c96","")
                .replaceAll("”", "")
                .replaceAll("“", "")
                .replaceAll("　", "")
                .replaceAll(" ", "")
                .replaceAll("「", "")
                .replaceAll("」", "")
                .replaceAll("゙", "")
                .replaceAll("‐", "")
                .replaceAll("−", "")
                .replaceAll("–", "")
                .replaceAll("-", "")
                .replaceAll("ー", "")
                .replaceAll("—", "")
                .replaceAll("―", "")
                .replaceAll("ｰ", "")
                .replaceAll("‑", "");
        Matcher matcher = Pattern.compile("[A-Rア-リあ-れ][0-9]{2}(ab|[ab])?").matcher(indataNew);
        while (matcher.find()) {
            return matcher.group();
        }
        return "不明";
    }


    /**
     * ブロックの文字列からホールの情報を抽出するメソッド.
     *
     * @param indata 抽出対象のブロックの文字列を渡します
     * @return ホールの抽出結果
     */
    public String getHole(final String indata) {
        String indataNew = Normalizer.normalize(indata, Normalizer.Form.NFKC);
        if (indataNew.matches("[A-R][0-9]{2}[ab]?")) {
            return "西34";
        } else if (indataNew.matches("[あ-れ][0-9]{2}[ab]?")) {
            return "西12";
        } else if (indataNew.matches("[ア-ト][0-9]{2}[ab]?")) {
            return "南12";
        } else if (indataNew.matches("[ナ-リ][0-9]{2}[ab]?")) {
            return "南34";
        }
        return "ホール";
    }

    /**
     * 文字列から開催日の情報を抽出するメソッド.
     *
     * @param indata 抽出対象の文字列を渡します
     * @return 開催日の抽出結果
     */
    public String getDay(final String indata) {
        String indataNew = Normalizer.normalize(indata, Normalizer.Form.NFKC);
        if (indataNew.matches(".*(([1一]日目)|(金)|(金曜)|(8/9)|(\\(金\\))).*")) {
            return "1日目";
        } else if (indataNew.matches(".*(([2二]日目)|(土)|(土曜)|(8/10)|(\\(土\\))).*")) {
            return "2日目";
        } else if (indataNew.matches(".*(([4四]日目)|(月)|(月曜)|(8/12)|(\\(月\\))).*")) {
            return "4日目";
        } else if (indataNew.matches(".*(([3三]日目)|(日)|(日曜)|(8/11)|(\\(日\\))).*")) {//日曜の判定は最後必ずな！
            return "3日目";
        }
        return "開催日";
    }

    /**
     * 文字列からコミケ参加者かどうかを抽出するメソッド.
     *
     * @param indata 抽出対象の文字列を渡します
     * @return コミケ参加者かどうかを判定します
     */
    private boolean isComicMarket(final String indata) {
        String indataNew = Normalizer.normalize(indata, Normalizer.Form.NFKC);
        return indataNew.matches(".*(([1-4一二三四]日目)|([金土日月]曜)|(8/(9|1[0-2]))|(\\([金土日月]\\))).*");
    }

    /**
     * ユーザー名から開催日やブロックなどの情報以外を抽出するメソッド.
     *
     * @param indata 抽出対象の文字列を渡します
     * @return ユーザー名から開催日やブロックなどの情報以外を返します
     */
    @SuppressWarnings("LoopStatementThatDoesntLoop")
    private String getUser(final String indata) {
        String indataNew = Normalizer.normalize(indata, Normalizer.Form.NFKC);
        Matcher matcher = Pattern.compile(".*(?!.*(([1-4一二三四]日目)|([金土日月]曜)|(8/(9|1[0-2]))|(\\([金土日月]\\)).*)).*").matcher(indataNew);
        while (matcher.find()) {
            return matcher.group();
        }
        return "";
    }

    /**
     * ブロックの文字列から壁サークルかどうかを抽出するメソッド.
     *
     * @param string 抽出対象のブロックの文字列を渡します
     * @return 壁サークルかどうかを返します
     */
    public boolean isWall(final String string) {
        String stringNew = Normalizer.normalize(string, Normalizer.Form.NFKC);
        return stringNew.matches(".*[Aaアナれ].*");
    }

    /**
     * リストViewのアダプタに抽出したそれぞれの情報を格納するメソッドです.
     *
     * @param mode         モード trueの場合はコミケ参加かどうかにかかわらずリストに追加します
     * @param arrayList    ユーザー情報が含まれるArrayListです
     * @param adapter      リストViewのアダプタです
     * @param linearLayout 進捗状況を示すレイアウトで、すべての処理が完了後に非表示にします
     */
    public void getData(final boolean mode, final ArrayList<String[]> arrayList,
                        final CircleListItemAdapter adapter, final LinearLayout linearLayout) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                for (String[] list : arrayList) {
                    String name = list[0];
                    String screenName = list[1];
                    String url = list[2];
                    if (!mode || isComicMarket(name)) {

                        String day = getDay(name);
                        String block = getBlock(name);
                        String hall = getHole(block);
                        boolean isWall = isWall(block);
                        Circle circle = new Circle(name, screenName, url, day, block, hall,
                                isWall, "メモ欄", Color.GREEN, false, 000, "");
                        adapter.add(circle);
                        adapter.notifyDataSetChanged();
                    }
                }
                linearLayout.setVisibility(View.GONE);
            }
        });
    }
}
