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
public class DBListAdapter extends RealmBaseAdapter<DBObject> implements ListAdapter {

    /**
     * コンストラクタ.
     *
     * @param data DBの内容
     */
    public DBListAdapter(@Nullable final OrderedRealmCollection<DBObject> data) {
        super(data);
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.circle_list_item, parent, false);
        }
        DBObject i = getItem(position);
        TextView textName = view.findViewById(R.id.text_name);
        TextView textDay = view.findViewById(R.id.text_day);
        TextView textMemo = view.findViewById(R.id.text_memo);
        TextView textTwitter = view.findViewById(R.id.text_twitter);
        TextView textSpace = view.findViewById(R.id.text_space);
        ImageView imageColor = view.findViewById(R.id.image_color);
        ImageView imageIcon = view.findViewById(R.id.image_icon);
        BootstrapButton colorButton = view.findViewById(R.id.color_button);
        BootstrapButton memoButton = view.findViewById(R.id.memo_button);
        CardView cardView = view.findViewById(R.id.card);
        textDay.setText(i.getDay());
        textName.setText(i.getUserName());
        String twitterMassage = "{cmd-twitter} " + i.getScreenName();
        textTwitter.setText(twitterMassage);
        textMemo.setText(i.getMemo());
        String spaceMassage = i.getHall() + "\n" + i.getBlock();
        textSpace.setText(spaceMassage);
        TypedValue typedValue = new TypedValue();
        parent.getContext().getTheme().resolveAttribute(android.R.attr.colorForeground, typedValue, true);
        int resourceId = typedValue.resourceId;
        int colors = ContextCompat.getColor(parent.getContext(), resourceId);
        int color = i.getIsWall() ? Color.RED : colors;
        textDay.setTextColor(color);
        textSpace.setTextColor(color);
        imageColor.setBackgroundColor(i.getColor());
        MyGlideApp.with(view).load(i.getIconUrl()).circleCrop().into(imageIcon);
        memoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ((ListView) parent).performItemClick(v, position, R.id.memo_button);
            }
        });
        colorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ((ListView) parent).performItemClick(v, position, R.id.color_button);
            }
        });
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ((ListView) parent).performItemClick(v, position, R.id.card);
            }
        });
        return view;
    }
}
