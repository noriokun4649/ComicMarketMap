/*
 * Copyright (c) 2018 noriokun4649.
 */

package jp.noriokun4649.comicmarketmap.asycktasks;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import jp.noriokun4649.comicmarketmap.R;
import jp.noriokun4649.comicmarketmap.list.Circle;
import jp.noriokun4649.comicmarketmap.list.CircleListItemAdapter;

/**
 * Androidの非同期処理を行うメソッドです.
 * CSVファイルの読み込み、結果をデータに入れる処理を非同期で行います。
 * 例外が発生した際は、実行結果の文字列に例外内容が返されます。
 */
public class AsyncTaskClass extends AsyncTask<InputStream, String, String> {
    /**
     * linearLayout.
     */
    @SuppressLint("StaticFieldLeak")
    private LinearLayout linearLayout;
    /**
     * progressBar.
     */
    @SuppressLint("StaticFieldLeak")
    private ProgressBar progressBar;
    /**
     * textView.
     */
    @SuppressLint("StaticFieldLeak")
    private TextView textView;

    /**
     * アダプタです。データの追加や更新の通知などを行う際につかうインスタンスです.
     */
    private CircleListItemAdapter adapter;
    /**
     * 読み込み対象のCSVファイルがコミケの仕様書に準拠したCSVファイルかを調べます.
     */
    private boolean isComicMarketCsv = false;
    /**
     * コミケ専用のCSVを読み込んだ際、colorの設定を行うレコードがあるため.
     * そのレコードの内容をArrayListに入れて置き、circleのレコードを
     * 読み込む際に、そのカラーコードを参照します。
     */
    private ArrayList<Integer> color;
    /**
     * CSVファイルのエンコードを指定します.
     * 非対応のエンコードの場合はUnsupportedEncodingExceptionが発生します
     * 継承元のIOExceptionの場合もあり
     */
    private String encode;
    /**
     * 設定を取得するためのインスタンスです.
     */
    private SharedPreferences preferences;
    /**
     * 実行結果です。例外の際はこの文字列に例外内容が入ります.
     */
    private String isOk = "OK";

    /**
     * 非同期処理のコンストラクタです.
     *
     * @param linearLayout プログレスを表示するプログレスバーとテキストビューのペアレントViewです
     * @param adapter      アダプタです。データの追加や更新の通知などを行う際につかうインスタンスです
     * @param textView     テキストビューです。メッセージなど表示します
     * @param progressBar  プログレスバーです。進捗状況を表します
     * @param encode       CSVファイルのエンコードを指定します。
     *                     非対応のエンコードの場合はUnsupportedEncodingExceptionが発生します
     *                     継承元のIOExceptionの場合もあり
     * @param preferences  設定を取得するためのインスタンスです。
     */
    public AsyncTaskClass(final LinearLayout linearLayout, final CircleListItemAdapter adapter, final TextView textView, final ProgressBar progressBar, final String encode, final SharedPreferences preferences) {
        this.linearLayout = linearLayout;
        this.adapter = adapter;
        this.encode = encode;
        this.textView = textView;
        this.progressBar = progressBar;
        this.preferences = preferences;
        color = new ArrayList<>();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(final InputStream... inputStreams) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStreams[0], encode))) {
            String line;
            while ((line = reader.readLine()) != null) {
                publishProgress(line);
            }
        } catch (IOException er) {
            isOk = er.toString();
        }
        return isOk;
    }

    @Override
    protected void onProgressUpdate(final String... values) {
        super.onProgressUpdate(values);
        String[] strings = values[0].split(",");
        switch (strings[0]) {
            case "Header":
                isComicMarketCsv = true;
                encode = strings[3];
                break;
            case "Color":
                color.add(Integer.parseInt(strings[1]) - 1, Color.parseColor("#" + strings[2]));
                break;
            case "Circle":
                String haiti = strings[21].equals("0") ? "a" : "b";
                String name = preferences.getInt("setting2", 0) == 0 ? strings[10] : strings[12];
                adapter.add(new Circle(name, "",
                        "https://dl.dropboxusercontent.com/s/3jab8zdsv36isam/gazo-.png",
                        strings[5], strings[7] + strings[8] + haiti, values[0],
                        color.get(Integer.parseInt(strings[2]) - 1), false, Integer.parseInt(strings[9]), strings[14]));
                break;
            default:
        }
    }

    @Override
    protected void onPostExecute(final String result) {
        progressBar.setVisibility(View.GONE);
        if (isComicMarketCsv) {
            linearLayout.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        } else {
            if (isOk.equals("OK")) {
                textView.setText(R.string.load_error_detanotfound);
            } else {
                String message = "LOAD ERROR...\n\n" + isOk;
                textView.setText(message);
            }
        }
    }
}
