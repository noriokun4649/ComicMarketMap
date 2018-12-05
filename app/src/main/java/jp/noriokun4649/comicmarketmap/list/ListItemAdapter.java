/*
 * Copyright (c) 2018 noriokun4649.
 */

package jp.noriokun4649.comicmarketmap.list;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.view.IconicsImageView;

import java.util.List;

import io.multimoon.colorful.ColorfulKt;
import jp.noriokun4649.comicmarketmap.R;

/**
 * リストアイテムのアダプタです.
 * これでは、主に設定項目のリストViewで使用されている
 */
public class ListItemAdapter extends ArrayAdapter<Import> {
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
    public ListItemAdapter(@NonNull final Context context, @NonNull final List<Import> objects) {
        super(context, 0, objects);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @SuppressLint("ResourceAsColor")
    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull final ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.list_item, parent, false);
        }
        Import i = getItem(position);
        IconicsImageView iconicsImageView = view.findViewById(R.id.image_icon);
        int color = ColorfulKt.Colorful().getAccentColor().getColorPack().normal().asInt();
        iconicsImageView.setIcon(new IconicsDrawable(getContext(), i.getiIcon()).sizeDp(21).color(color));
        TextView textView = view.findViewById(R.id.text_listname);
        textView.setText(i.getName());
        return view;
    }
}