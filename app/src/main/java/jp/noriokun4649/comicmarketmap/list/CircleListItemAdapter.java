/*
 * Copyright (c) 2018 noriokun4649.
 */

package jp.noriokun4649.comicmarketmap.list;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import jp.noriokun4649.comicmarketmap.R;
import jp.noriokun4649.comicmarketmap.glide.MyGlideApp;

/**
 * サークルリストのアイテムAdapterです。サークルリストの雛形を使用してAdapterを作成します.
 */
public class CircleListItemAdapter extends ArrayAdapter<Circle> {
    /**
     * レイアウト.
     */
    private final LayoutInflater inflater;

    /**
     * コンストラクタ.
     *
     * @param context コンテキスト
     * @param objects リストViewで使用する情報のひな型
     */
    public CircleListItemAdapter(@NonNull final Context context, @NonNull final List<Circle> objects) {
        super(context, 0, objects);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull final ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.circle_list_item, parent, false);
        }
        Circle i = getItem(position);
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
        getContext().getTheme().resolveAttribute(android.R.attr.colorForeground, typedValue, true);
        int resourceId = typedValue.resourceId;
        int colors = ContextCompat.getColor(getContext(), resourceId);
        int color = i.isWall() ? Color.RED : colors;
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
