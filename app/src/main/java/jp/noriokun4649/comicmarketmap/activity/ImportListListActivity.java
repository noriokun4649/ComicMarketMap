/*
 * Copyright (c) 2018 noriokun4649.
 */

package jp.noriokun4649.comicmarketmap.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.context.IconicsLayoutInflater2;

import java.util.ArrayList;

import jp.noriokun4649.comicmarketmap.R;
import jp.noriokun4649.comicmarketmap.list.ListList;
import jp.noriokun4649.comicmarketmap.list.ListListItemAdapter;
import jp.noriokun4649.comicmarketmap.twitter.TwitterConnect;
import twitter4j.AccountSettings;
import twitter4j.AsyncTwitter;
import twitter4j.ResponseList;
import twitter4j.TwitterAdapter;
import twitter4j.TwitterException;
import twitter4j.TwitterMethod;
import twitter4j.UserList;

/**
 * リストからインポートする際のアクティビティです.
 * こっちはリストの一覧が出る
 */
public class ImportListListActivity extends AppCompatActivity {
    /**
     * ハンドラー.
     * このインスタンスを通して、じゃないとアプリの画面等を操作できません.
     */
    private static Handler mHandler = new Handler();
    /**
     * リストViewのアダプタです.
     */
    private ListListItemAdapter adapter;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory2(getLayoutInflater(), new IconicsLayoutInflater2(getDelegate()));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_list_import_layout);
        final LinearLayout linearLayout = findViewById(R.id.progress);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.list_import);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_arrow_back).sizeDp(16).color(Color.WHITE));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                finish();
            }
        });
        getSupportActionBar().hide();
        final ArrayList<ListList> listList = new ArrayList<>();
        adapter = new ListListItemAdapter(this, listList);
        final ListView listView = findViewById(R.id.list_import_list);
        listView.setAdapter(adapter);
        TwitterConnect twitterConnect = new TwitterConnect(this);
        twitterConnect.login();
        final AsyncTwitter asyncTwitter = twitterConnect.getmTwitter();
        asyncTwitter.addListener(new TwitterAdapter() {
            @Override
            public void gotAccountSettings(final AccountSettings settings) {
                asyncTwitter.getUserLists(settings.getScreenName());
            }

            @Override
            public void gotUserLists(final ResponseList<UserList> userLists) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        for (UserList list : userLists) {
                            ListList listList1 = new ListList(list.getName(), list.getMemberCount(), list.getId(), list.getUser().getName(), list.getUser().get400x400ProfileImageURL(), list.getDescription(), list.isPublic());
                            adapter.add(listList1);
                            adapter.notifyDataSetChanged();
                        }
                        linearLayout.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void onException(final TwitterException te, final TwitterMethod method) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        linearLayout.setVisibility(View.GONE);
                        Snackbar snackbar = Snackbar.make(getCurrentFocus(), R.string.api_limit, Snackbar.LENGTH_LONG).setAction(R.string.close, new View.OnClickListener() {
                            @Override
                            public void onClick(final View v) {
                                finish();
                            }
                        });
                        snackbar.show();
                    }
                });
                super.onException(te, method);
            }
        });
        asyncTwitter.getAccountSettings();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
                Intent intent = new Intent(ImportListListActivity.this, ImportListActivity.class);
                intent.putExtra("listId", adapter.getItem(position).getId());
                intent.putExtra("count", adapter.getItem(position).getMemberCount());
                startActivity(intent);

            }
        });
    }
}
