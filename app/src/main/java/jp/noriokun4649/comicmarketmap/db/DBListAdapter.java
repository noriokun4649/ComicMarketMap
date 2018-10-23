package jp.noriokun4649.comicmarketmap.db;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;

import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;
import jp.noriokun4649.comicmarketmap.R;
import jp.noriokun4649.comicmarketmap.glide.MyGlideApp;

/**
 * Realmデータベースの情報を表示する際のリストView用Adapterです.
 */
public class DBListAdapter extends DBAdapter {
    /**
     * コンストラクタ.
     *
     * @param data DBの内容
     */
    public DBListAdapter(@Nullable final OrderedRealmCollection<DBObject> data) {
        super(data);
    }

    @Override
    int getMemoText() {
        return R.string.memo_edit;
    }

    @Override
    int getColorText() {
        return R.string.color_edit;
    }
}
