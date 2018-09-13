/*
 * Copyright (c) 2018 noriokun4649.
 */

package jp.noriokun4649.comicmarketmap.activity;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import jp.noriokun4649.comicmarketmap.R;
import jp.noriokun4649.comicmarketmap.list.CircleListItemAdapter;
import twitter4j.AsyncTwitter;

/**
 * バックアップからインポートする際のアクティビティです.
 */
public class ImportBackupActivity extends ImportBase {

    @Override
    void getListData(final AsyncTwitter asyncTwitter, final CircleListItemAdapter adapters, final int counts, final long id) {
        //adapters.add(new Circle("", "", "", "", "", "", true, "現在この機能はサポートされていません", Color.RED, false));
        //LinearLayout linearLayout = findViewById(R.id.progress);
        TextView textView = findViewById(R.id.textView4);
        ProgressBar progressBar = findViewById(R.id.progressBar3);
        textView.setText("現在この機能はサポートされていません");
        progressBar.setVisibility(View.GONE);
    }

    @Override
    int getTitles() {
        return R.string.backup_import;
    }

    @Override
    int getLoadingText() {
        return R.string.aboutlibrary_lib_version;
    }

    @Override
    public void onItemClick(final int which, final String tag) {

    }

    @Override
    public void onOKClick(final int dialogId, final int position, @Nullable final String returnMemo, final String tag) {

    }
}