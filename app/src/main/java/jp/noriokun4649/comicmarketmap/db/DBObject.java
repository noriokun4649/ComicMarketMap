/*
 * Copyright (c) 2018 noriokun4649.
 */

package jp.noriokun4649.comicmarketmap.db;

import android.graphics.Color;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * データベースのオブジェクトでしゅ.
 */
public class DBObject extends RealmObject {
    /**
     * Twitterのユーザー名です.
     * このパラメータはデータベースの主キーとして設定されています
     */
    @PrimaryKey
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
     * このパラメータはインデックスが作成されます
     */
    @Index
    private String day;
    /**
     * サークル情報として抽出できたサークルのブロックです.
     * このパラメータはインデックスが作成されます
     */
    @Index
    private String block;
    /**
     * サークル情報として抽出できたホールです.
     * このパラメータはインデックスが作成されます
     */
    @Index
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
    @Index
    private int color;
    /**
     * 購入済みかどうかの情報です.
     */
    private boolean check;

    /**
     * ジャンルコードの情報.
     */
    private int genreCode;

    /**
     * URLの情報.
     */
    private String url;

    /**
     * 空のコンストラクタ.
     */
    public DBObject() {
    }

    /**
     * データベースのコンストラクタだお.
     *
     * @param userName   ユーザーネーム
     * @param screenName TwitterのID
     * @param iconUrl    TwitterのアイコンURL
     * @param day        サークルの開催日
     * @param block      サークルのブロック
     * @param hall       サークルのホール
     * @param isWall     サークルが壁かどうか
     * @param memo       メモ
     * @param color      色
     * @param check      購入済みかどうか
     * @param genreCode  ジャンルコード
     * @param url        URLになるもの
     */
    public DBObject(final String userName, final String screenName, final String iconUrl,
                    final String day, final String block, final String hall, final boolean isWall,
                    final String memo, final int color, final boolean check, final int genreCode, final String url) {
        setUserName(userName);
        setScreenName(screenName);
        setIconUrl(iconUrl);
        setDay(day);
        setBlock(block);
        setHall(hall);
        setIsWall(isWall);
        setMemo(memo);
        setColor(color);
        setCheck(check);
        setGenreCode(genreCode);
        setUrl(url);
    }

    /**
     * データベースのコンストラクタだお.
     *
     * @param userName   ユーザーネーム
     * @param screenName TwitterのID
     * @param iconUrl    TwitterのアイコンURL
     * @param day        サークルの開催日
     * @param block      サークルのブロック
     * @param hall       サークルのホール
     * @param isWall     サークルが壁かどうか
     */
    public DBObject(final String userName, final String screenName, final String iconUrl,
                    final String day, final String block, final String hall, final boolean isWall) {
        setUserName(userName);
        setScreenName(screenName);
        setIconUrl(iconUrl);
        setDay(day);
        setBlock(block);
        setHall(hall);
        setIsWall(isWall);
        setMemo("");
        setColor(Color.WHITE);
        setCheck(false);
    }

    /**
     * メモを取得.
     *
     * @return メモ
     */
    public String getMemo() {
        return memo;
    }

    /**
     * メモを設定.
     *
     * @param memo メモ
     */
    public void setMemo(final String memo) {
        this.memo = memo;
    }

    /**
     * 色を取得.
     *
     * @return 色
     */
    public int getColor() {
        return color;
    }

    /**
     * 色を設定.
     *
     * @param color 色
     */
    public void setColor(final int color) {
        this.color = color;
    }

    /**
     * 購入済みかどうかを取得.
     *
     * @return 購入済みかどうか
     */
    public boolean isCheck() {
        return check;
    }

    /**
     * 購入済みかどうかを設定.
     *
     * @param check 購入済みかどうか
     */
    public void setCheck(final boolean check) {
        this.check = check;
    }

    /**
     * ユーザーネームを取得.
     *
     * @return ユーザーネーム
     */
    public String getUserName() {
        return userName;
    }

    /**
     * ユーザーネームを設定.
     *
     * @param userName ユーザーネーム
     */
    public void setUserName(final String userName) {
        this.userName = userName;
    }

    /**
     * TwitterのIDを取得.
     *
     * @return TwitterのID
     */
    public String getScreenName() {
        return screenName;
    }

    /**
     * TwitterのIDを設定.
     *
     * @param screenName TwitterのID
     */
    public void setScreenName(final String screenName) {
        this.screenName = screenName;
    }

    /**
     * TwitterのアイコンURLを取得.
     *
     * @return アイコンURL
     */
    public String getIconUrl() {
        return iconUrl;
    }

    /**
     * TwitterのアイコンURLを設定.
     *
     * @param iconUrl アイコンURL
     */
    public void setIconUrl(final String iconUrl) {
        this.iconUrl = iconUrl;
    }

    /**
     * 開催日を取得.
     *
     * @return 開催日
     */
    public String getDay() {
        return day;
    }

    /**
     * 開催日を設定.
     *
     * @param day 開催日
     */
    public void setDay(final String day) {
        this.day = day;
    }

    /**
     * ブロックを取得.
     *
     * @return ブロック
     */
    public String getBlock() {
        return block;
    }

    /**
     * ブロックを設定.
     *
     * @param block ブロック
     */
    public void setBlock(final String block) {
        this.block = block;
    }

    /**
     * ホールを取得.
     *
     * @return ホール
     */
    public String getHall() {
        return hall;
    }

    /**
     * ホールを設定.
     *
     * @param hall ホール
     */
    public void setHall(final String hall) {
        this.hall = hall;
    }

    /**
     * 壁かどうかを取得.
     *
     * @return 壁かどうか
     */
    public boolean getIsWall() {
        return isWall;
    }

    /**
     * 壁かどうかを設定.
     *
     * @param isWall 壁かどうか
     */
    public void setIsWall(final boolean isWall) {
        this.isWall = isWall;
    }

    /**
     * ジャンルコードを取得.
     *
     * @return value of genreCode
     */
    public int getGenreCode() {
        return genreCode;
    }

    /**
     * ジャンルコードを設定.
     *
     * @param genreCode ジャンルコード
     */
    public void setGenreCode(final int genreCode) {
        this.genreCode = genreCode;
    }

    /**
     * URLを取得.
     *
     * @return value of url
     */
    public String getUrl() {
        return url;
    }

    /**
     * URLを設定.
     *
     * @param url URL
     */
    public void setUrl(final String url) {
        this.url = url;
    }
}
