/*
 * Copyright (c) 2018 noriokun4649.
 */

package jp.noriokun4649.comicmarketmap.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import io.realm.Realm;
import io.realm.RealmResults;
import jp.noriokun4649.comicmarketmap.R;
import jp.noriokun4649.comicmarketmap.db.DBInfoAdapter;
import jp.noriokun4649.comicmarketmap.db.DBObject;

<<<<<<<HEAD
        =======
        >>>>>>>3700306d2900654023d8c70c55f43507ef178354

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
