/*
 * Copyright (c) 2018 noriokun4649.
 */

package jp.noriokun4649.comicmarketmap.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import javax.annotation.Nullable;

import io.realm.Realm;
import io.realm.RealmResults;
import jp.noriokun4649.comicmarketmap.R;
import jp.noriokun4649.comicmarketmap.db.DBListAdapter;
import jp.noriokun4649.comicmarketmap.db.DBObject;

/**
 * サークルリスト編集のフラグメントです.
 */
public class CircleListEditFragment extends Fragment {

    /**
     * Realmデータベースのインスタンス.
     */
    private Realm realm;
    /**
     * Realmデータベースのアダプタ.
     */
    private DBListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.circle_list_edit_layout, container, false);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.CircleListEdit));
        toolbar.inflateMenu(R.menu.circle_list_edit_menu);
        realm = Realm.getDefaultInstance();
        final RealmResults<DBObject> dbObjects = realm.where(DBObject.class).sort("block").findAll();
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete:
                        realm.executeTransactionAsync(new Realm.Transaction() {
                            @Override
                            public void execute(final Realm realms) {
                                realms.deleteAll();
                            }
                        });
                        break;
                    case R.id.search:
                        break;
                    case R.id.sort:
                        break;
                    default:
                }
                return false;
            }
        });
        //toolbar.set
        adapter = new DBListAdapter(dbObjects);
        ListView listView = view.findViewById(R.id.list_edit);
        listView.setAdapter(adapter);
        listView.setEmptyView(view.findViewById(R.id.textView));


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        realm.close();
    }
}
