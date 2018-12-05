package jp.noriokun4649.comicmarketmap.db;

import javax.annotation.Nullable;

import io.realm.OrderedRealmCollection;
import jp.noriokun4649.comicmarketmap.R;

public class DBInfoAdapter extends DBAdapter {
    /**
     * コンストラクタ.
     *
     * @param data DBの内容
     */
    public DBInfoAdapter(@Nullable final OrderedRealmCollection<DBObject> data) {
        super(data);
    }

    @Override
    int getMemoText() {
        return R.string.info;
    }

    @Override
    int getColorText() {
        return R.string.notification;
    }
}
