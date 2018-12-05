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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import jp.noriokun4649.comicmarketmap.R;

/**
 * 設定項目のアイテムアダプタです.
 */
public class SettingItemAdapter extends ArrayAdapter<Setting> {

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
    public SettingItemAdapter(@NonNull final Context context, @NonNull final List<Setting> objects) {
        super(context, 0, objects);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @SuppressLint("ResourceAsColor")
    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull final ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.setting_item, parent, false);
        }
        TextView info = view.findViewById(R.id.text_setting_info);
        TextView title = view.findViewById(R.id.text_setting_title);
        TextView now = view.findViewById(R.id.text_setting_now);
        CheckBox checkBox = view.findViewById(R.id.checkBox_setting);
        Setting i = getItem(position);
        title.setText(i.getTitle());
        info.setText(i.getInfo());
        now.setText(i.getNow());
        now.setVisibility(i.isMode() ? View.GONE : View.VISIBLE);
        checkBox.setVisibility(i.isMode() ? View.VISIBLE : View.GONE);
        checkBox.setChecked(i.isCheckBoxNow());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
                ((ListView) parent).performItemClick(buttonView, position, R.id.checkBox_setting);
            }
        });
        return view;
    }
}