/*
 * Copyright (c) 2018 noriokun4649.
 */

package jp.noriokun4649.comicmarketmap.activity;

import jp.noriokun4649.comicmarketmap.R;
import jp.noriokun4649.comicmarketmap.list.CircleListItemAdapter;
import jp.noriokun4649.comicmarketmap.twitter.GetFollow;
import twitter4j.AsyncTwitter;

/**
 * フォローからインポートする際のアクティビティです.
 */
public class ImportFollowActivity extends ImportBase {
    @Override
    void getListData(final AsyncTwitter asyncTwitter, final CircleListItemAdapter adapter, final int count, final long id) {
        GetFollow getFollow = new GetFollow(this, asyncTwitter, adapter);
        getFollow.getFollow();
    }

    @Override
    int getTitles() {
        return R.string.follow_import;
    }

    @Override
    int getLoadingText() {
        return R.string.getting_follow_user_now;
    }

    @Override
    public void onItemClick(final int which, final String tag) {

    }
}
