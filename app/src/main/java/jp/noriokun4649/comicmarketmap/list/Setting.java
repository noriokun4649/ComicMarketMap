/*
 * Copyright (c) 2018 noriokun4649.
 */

package jp.noriokun4649.comicmarketmap.list;

/**
 * 設定項目のひな型.
 */
public class Setting {
    /**
     * 設定項目のタイトル.
     */
    private String title;
    /**
     * 初期の選択中の設定の表示.
     */
    private String now;
    /**
     * 設定項目の詳細情報.
     */
    private String info;
    /**
     * チェックボックスを非表示.
     */
    private boolean mode;
    /**
     * チェックボックスの初期状態.
     */
    private boolean checkBoxNow;
    /**
     * 初期の選択中の設定のインデックス.
     */
    private int index;

    /**
     * コンストラクタ.
     * 設定のListViewで追加する項目がチェックボックスじゃない際に使用されるコンストラクタ
     *
     * @param title 設定項目のタイトル
     * @param index 初期の選択中の設定のインデックス
     * @param info  設定項目の詳細情報
     * @param mode  チェックボックスを非表示
     * @param now   初期の選択中の設定の表示
     */
    public Setting(final String title, final int index, final String info, final boolean mode, final String now) {
        this.title = title;
        this.now = now;
        this.info = info;
        this.mode = mode;
        this.index = index;
    }


    /**
     * コンストラクタ.
     * 設定のListViewで追加する項目がチェックボックスの際に使用されるコンストラクタ
     *
     * @param title       設定項目のタイトル
     * @param checkBoxNow チェックボックスの初期状態
     * @param info        設定項目の詳細情報
     * @param mode        チェックボックスを表示
     */
    public Setting(final String title, final boolean checkBoxNow, final String info, final boolean mode) {
        this.title = title;
        this.checkBoxNow = checkBoxNow;
        this.info = info;
        this.mode = mode;
    }

    /**
     * 設定のタイトルを取得するメソッド.
     *
     * @return 設定のタイトル
     */
    public String getTitle() {
        return title;
    }

    /**
     * 設定のタイトルを設定するメソッド.
     *
     * @param title タイトルにぃれるやつぅ「
     */
    public void setTitle(final String title) {
        this.title = title;
    }

    /**
     * 現在の設定中のパラメータの文字列を取得するメソッド.
     *
     * @return 設定中のパラメータの文字列
     */
    public String getNow() {
        return now;
    }

    /**
     * 現在設定中のパラメータの文字列を設定するメソッド.
     *
     * @param now 設定中のパラメータの文字列
     */
    public void setNow(final String now) {
        this.now = now;
    }

    /**
     * 設定の詳細情報を取得するメソッド.
     *
     * @return 詳細情報
     */
    public String getInfo() {
        return info;
    }

    /**
     * 設定の詳細情報を設定するメソッド.
     *
     * @param info 詳細情報
     */
    public void setInfo(final String info) {
        this.info = info;
    }

    /**
     * 現在のモードを取得するメソッド.
     *
     * @return trueはチェックボックスのモード、falseはダイアログ選択式のモード
     */
    public boolean isMode() {
        return mode;
    }

    /**
     * モードを設定するメソッド.
     *
     * @param mode trueの場合は、
     */
    public void setMode(final boolean mode) {
        this.mode = mode;
    }

    /**
     * チェックボックスの状態を取得するメソッド.
     *
     * @return trueはチェック中、falseはチェックしてない。
     */
    public boolean isCheckBoxNow() {
        return checkBoxNow;
    }

    /**
     * チェックボックスの状態を設定するメソッド.
     *
     * @param checkBoxNow trueの場合はON、falseの場合はOFF
     */
    public void setCheckBoxNow(final boolean checkBoxNow) {
        this.checkBoxNow = checkBoxNow;
    }

    /**
     * 設定のインデックスを取得するメソッド.
     *
     * @return インデックス返す
     */
    public int getIndex() {
        return index;
    }

    /**
     * 設定のインデックを設定するメソッド.
     *
     * @param index インデックス
     */
    public void setIndex(final int index) {
        this.index = index;
    }

}
