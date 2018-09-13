/*
 * Copyright (c) 2018 noriokun4649.
 */

package jp.noriokun4649.comicmarketmap.list;

import com.mikepenz.iconics.typeface.IIcon;

/**
 * インポートクラス.
 * インポートのメニューに使用する情報のひな型です.
 */
public class Import {
    /**
     * アイコンの情報.
     */
    private IIcon iIcon;
    /**
     * インポートメニューのタイトルの情報l.
     */
    private String name;

    /**
     * インポートメニューの情報のひな型.
     *
     * @param icon アイコンの情報
     * @param name インポートメニューのタイトル
     */
    public Import(final IIcon icon, final String name) {
        this.iIcon = icon;
        this.name = name;
    }

    /**
     * アイコン取得するメソッド.
     *
     * @return アイコン
     */
    public IIcon getiIcon() {
        return iIcon;
    }

    /**
     * アイコン設定するメソッド.
     *
     * @param iIcon アイコン
     */
    public void setiIcon(final IIcon iIcon) {
        this.iIcon = iIcon;
    }

    /**
     * インポートメニューのタイトル取得するメソッド.
     *
     * @return タイトル
     */
    public String getName() {
        return name;
    }

    /**
     * インポートメニューのタイトル設定するメソッド.
     *
     * @param name タイトル
     */
    public void setName(final String name) {
        this.name = name;
    }
}