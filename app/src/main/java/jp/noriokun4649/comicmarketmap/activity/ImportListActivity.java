/*
 * Copyright (c) 2018 noriokun4649.
 */

package jp.noriokun4649.comicmarketmap.activity;

import jp.noriokun4649.comicmarketmap.R;
import jp.noriokun4649.comicmarketmap.list.CircleListItemAdapter;
import jp.noriokun4649.comicmarketmap.twitter.GetList;
import twitter4j.AsyncTwitter;

/**
 * リストのユーザーからインポートする際のアクティビティです.
 */
public class ImportListActivity extends ImportBase {

    @Override
    void getListData(final AsyncTwitter asyncTwitter, final CircleListItemAdapter adapters, final int counts, final long id) {
        GetList getList = new GetList(this, asyncTwitter, id, counts, adapters);
        getList.getList();
    }

    @Override
    int getTitles() {
        return R.string.list_import;
    }

    @Override
    int getLoadingText() {
        return R.string.getting_list_user_now;
    }


    @Override
    public void onItemClick(final int which, final String tag) {

    }
}