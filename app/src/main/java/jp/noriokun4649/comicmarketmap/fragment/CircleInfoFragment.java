/*
 * Copyright (c) 2018 noriokun4649.
 */

package jp.noriokun4649.comicmarketmap.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import io.realm.Realm;
import io.realm.RealmResults;
import jp.noriokun4649.comicmarketmap.R;
import jp.noriokun4649.comicmarketmap.db.DBInfoAdapter;
import jp.noriokun4649.comicmarketmap.db.DBObject;

/**
 * サークル情報のフラグメントです.
 */
public class CircleInfoFragment extends Fragment {
    /**
     * Realmデータベースのインスタンス.
     */
    private Realm realm;

    private DBInfoAdapter dbInfoAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.circle_info_layout, container, false);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.CircleInfo));
        toolbar.getMenu().clear();
        realm = Realm.getDefaultInstance();
        final RealmResults<DBObject> dbObjects = realm.where(DBObject.class).sort("block").findAll();
        dbInfoAdapter = new DBInfoAdapter(dbObjects);
        ListView listView = view.findViewById(R.id.list_info);
        listView.setAdapter(dbInfoAdapter);
        listView.setEmptyView(view.findViewById(R.id.textView2));
        return view;

    }
}
