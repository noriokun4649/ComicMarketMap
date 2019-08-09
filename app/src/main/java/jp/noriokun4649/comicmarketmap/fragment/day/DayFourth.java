package jp.noriokun4649.comicmarketmap.fragment.day;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import javax.annotation.Nullable;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import io.realm.Realm;
import io.realm.RealmResults;
import jp.noriokun4649.comicmarketmap.R;
import jp.noriokun4649.comicmarketmap.db.DBInfoAdapter;
import jp.noriokun4649.comicmarketmap.db.DBObject;

/**
 * サークル情報のフラグメントです.
 */
public class DayFourth extends Fragment {
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
        realm = Realm.getDefaultInstance();
        final RealmResults<DBObject> dbObjects = realm.where(DBObject.class).equalTo("day","4日目").sort("block").findAll();
        dbInfoAdapter = new DBInfoAdapter(dbObjects);
        ListView listView = view.findViewById(R.id.list_info);
        listView.setAdapter(dbInfoAdapter);
        listView.setEmptyView(view.findViewById(R.id.textView2));
        return view;

    }
}
