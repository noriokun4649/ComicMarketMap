/*
 * Copyright (c) 2018 noriokun4649.
 */

package jp.noriokun4649.comicmarketmap.list;

import jp.noriokun4649.comicmarketmap.getcirclespaceinfo.GetCircleSpaceInfo;

/**
 * Circle情報の雛形です、リストビューに表示されるサークルの情報はすべてこのフォーマットを使用します.
 */
public class Circle {
    /**
     * Twitterのユーザー名です.
     */
    private String userName;
    /**
     * TwitterのID部分です.
     */
    private String screenName;
    /**
     * Twitterのアイコン画像のURLです.
     */
    private String iconUrl;
    /**
     * サークル情報として抽出できた開催日もしくは手動で記入した開催日です.
     */
    private String day;
    /**
     * サークル情報として抽出できたサークルのブロックです.
     */
    private String block;
    /**
     * サークル情報として抽出できたホールです.
     */
    private String hall;
    /**
     * 壁サークルかどうかです.
     */
    private boolean isWall;
    /**
     * メモの情報です.
     */
    private String memo;
    /**
     * 目印になる色の設定です。カラーコードで入れてください.
     */
    private int color;
    /**
     * 購入済みかどうかの情報です.
     */
    private boolean check;

    /**
     * サークルのジャンルコードの情報です.
     */
    private int genreCode;

    /**
     * サークルのURLの情報です.
     */
    private String url;

    /**
     * サークル情報をパターンマッチングするためのインスタンスでしゅ.
     */
    private GetCircleSpaceInfo getCircleSpaceInfo;

    /**
     * サークル情報のリストに表示する情報のひな型のコンストラクタです.
     *
     * @param userName   Twitterのユーザー名です
     * @param screenName TwitterのID部分です
     * @param iconUrl    Twitterのアイコン画像のURLです
     * @param day        サークル情報として抽出できた開催日もしくは手動で記入した開催日です
     * @param block      サークル情報として抽出できたサークルのブロックです
     * @param hall       サークル情報として抽出できたホールです
     * @param isWall     壁サークルかどうかです
     * @param memo       メモの情報です
     * @param color      目印になる色の設定です。カラーコードで入れてください
     * @param check      購入済みかどうかの情報です
     * @param url        URLです
     * @param genreCode  ジャンルコードです
     */
    public Circle(final String userName, final String screenName, final String iconUrl,
                  final String day, final String block, final String hall, final boolean isWall,
                  final String memo, final int color, final boolean check, final int genreCode, final String url) {
        this.userName = userName;
        this.screenName = screenName;
        this.iconUrl = iconUrl;
        this.day = day;
        this.block = block;
        this.hall = hall;
        this.isWall = isWall;
        this.memo = memo;
        this.color = color;
        this.check = check;
        this.genreCode = genreCode;
        this.url = url;
        this.getCircleSpaceInfo = new GetCircleSpaceInfo();
    }

    /**
     * サークル情報のリストに表示する情報のひな型のコンストラクタです.
     *
     * @param userName   Twitterのユーザー名です
     * @param screenName TwitterのID部分です
     * @param iconUrl    Twitterのアイコン画像のURLです
     * @param day        サークル情報として抽出できた開催日もしくは手動で記入した開催日です
     * @param block      サークル情報として抽出できたサークルのブロックです
     * @param memo       メモの情報です
     * @param color      目印になる色の設定です。カラーコードで入れてください
     * @param check      購入済みかどうかの情報です
     * @param url        URLです
     * @param genreCode  ジャンルコードです
     */
    public Circle(final String userName, final String screenName, final String iconUrl, final String day, final String block, final String memo, final int color, final boolean check, final int genreCode, final String url) {
        this.getCircleSpaceInfo = new GetCircleSpaceInfo();
        this.userName = userName;
        this.screenName = screenName;
        this.iconUrl = iconUrl;
        setDay(day);
        setBlock(block);
        this.memo = memo;
        this.color = color;
        this.check = check;
        this.genreCode = genreCode;
        this.url = url;
    }

    /**
     * Twitterのユーザーネームを取得するメソッド.
     *
     * @return ユーザーネーム
     */
    public String getUserName() {
        return userName;
    }

    /**
     * ユーザーネームを設定するメソッド.
     * このメソッドを実行すると、同時に開催日、ブロック、ホール、壁サークルの情報も更新される。
     *
     * @param userName 設定するユーザーネーム
     */
    public void setUserName(final String userName) {
        this.userName = userName;
        this.day = getCircleSpaceInfo.getDay(userName);
        String blockDeta = getCircleSpaceInfo.getBlock(userName);
        this.block = blockDeta;
        this.hall = getCircleSpaceInfo.getHole(blockDeta);
        this.isWall = getCircleSpaceInfo.isWall(blockDeta);
    }

    /**
     * TwitterのユーザーIDを取得するメソッド.
     *
     * @return TwitterのユーザーIDを返す
     */
    public String getScreenName() {
        return screenName;
    }

    /**
     * ユーザーのTwitterIDを設定するメソッド.
     *
     * @param screenName 設定するTwitterID
     */
    public void setScreenName(final String screenName) {
        this.screenName = screenName;
    }

    /**
     * TwitterのユーザーアイコンのURLを取得するメソッド.
     *
     * @return TwitterのユーザーアイコンのURLを返す
     */
    public String getIconUrl() {
        return iconUrl;
    }

    /**
     * ユーザーのアイコン画像のURLを設定するメソッド.
     *
     * @param iconUrl URLなんでも行ける
     */
    public void setIconUrl(final String iconUrl) {
        this.iconUrl = iconUrl;
    }

    /**
     * サークル情報の開催日を取得するメソッド.
     *
     * @return 開催日を返す　このデータは正規表現でマッチングされた結果のみ格納されている。
     */
    public String getDay() {
        return day;
    }

    /**
     * サークル開催日を設定するメソッド.
     *
     * @param day 開催日　このデータは正規表現でパターンマッチングされる。
     */
    public void setDay(final String day) {
        this.day = getCircleSpaceInfo.getDay(day);
    }

    /**
     * サークル情報のホールを取得するメソッド.
     *
     * @return サークル情報のホールを返す。　このデータは正規表現でマッチングされた結果のみ格納されている。
     */
    public String getHall() {
        return hall;
    }

    /**
     * サークルのホールを設定するメソッド.
     *
     * @param hall ホールの情報　このデータは正規表現でパターンマッチングされる
     */
    public void setHall(final String hall) {
        this.hall = getCircleSpaceInfo.getHole(hall);
    }

    /**
     * サークル情報のメモを取得するメソッド.
     *
     * @return メモを返す。　このデータは改行コードも含まれている複数行で形成されている。
     */
    public String getMemo() {
        return memo;
    }

    /**
     * メモを設定するメソッド.
     *
     * @param memo メモ
     */
    public void setMemo(final String memo) {
        this.memo = memo;
    }

    /**
     * サークルのブロックを取得するメソッド.
     *
     * @return ブロック。
     */
    public String getBlock() {
        return block;
    }

    /**
     * サークルのブロックを設定するメソッド.
     * このメソッドを実行すると同時に、ホール、壁かの情報も更新される。
     *
     * @param block ブロックのデータ このデータは正規表現でパターンマッチングされる
     */
    public void setBlock(final String block) {
        this.block = getCircleSpaceInfo.getBlock(block);
        this.hall = getCircleSpaceInfo.getHole(block);
        this.isWall = getCircleSpaceInfo.isWall(block);
    }

    /**
     * サークル情報のカラーコードを取得するメソッド.
     *
     * @return サークル情報のカラーコードを返す。　16進数のint
     */
    public int getColor() {
        return color;
    }

    /**
     * 色を設定するメソッド.
     *
     * @param color 16進数のカラーコードで指定もしくは、Android標準のカラーコードライブラリを使用しても可
     */
    public void setColor(final int color) {
        this.color = color;
    }

    /**
     * このサークルの購入済みかどうかを取得するメソッド.
     *
     * @return 購入済みの場合はtrue、そうでない場合はfalse
     */
    public boolean isCheck() {
        return check;
    }

    /**
     * サークルで購入済みかどうかを設定するメソッド.
     *
     * @param check trueの場合購入済み、falseの場合は購入済みではない
     */
    public void setCheck(final boolean check) {
        this.check = check;
    }

    /**
     * このサークルが壁サークルかどうかを取得するメソッド.
     *
     * @return このサークルが壁サークルの場合はtrue、そうでない場合はfalse
     */
    public boolean isWall() {
        return isWall;
    }

    /**
     * サークルの壁サークルかどうかを設定するメソッド.
     * パラメータがString型の場合パラメータの情報に基づいて、壁サークルかどうかを判断し、代入
     *
     * @param wall そのサークルのブロック情報
     */
    public void setWall(final String wall) {
        this.isWall = getCircleSpaceInfo.isWall(wall);
    }

    /**
     * サークルの壁サークルかどうかを設定するメソッド.
     * パラメータがboole型の場合直接設定する
     *
     * @param wall trueの場合は壁サークル、falseの場合は壁サークルではない。
     */
    public void setWall(final boolean wall) {
        this.isWall = wall;
    }

    /**
     * ジャンルコードの情報を取得するメソッドです.
     *
     * @return ジャンルコードの情報
     */
    public int getGenreCode() {
        return genreCode;
    }

    /**
     * ジャンルコードの情報を設定するメソッドです.
     *
     * @param genreCode ジャンルコードの情報
     */
    public void setGenreCode(final int genreCode) {
        this.genreCode = genreCode;
    }

    /**
     * URLの情報を取得するメソッドです.
     *
     * @return URLの情報
     */
    public String getUrl() {
        return url;
    }

    /**
     * URLの情報を設定するメソッドです.
     *
     * @param url url
     */
    public void setUrl(final String url) {
        this.url = url;
    }
}
