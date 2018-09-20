/*
 * Copyright (c) 2018 noriokun4649.
 */

package jp.noriokun4649.comicmarketmap.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.ListView;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.context.IconicsLayoutInflater2;

import java.util.ArrayList;

import io.multimoon.colorful.CAppCompatActivity;
import io.multimoon.colorful.ColorfulKt;
import jp.noriokun4649.comicmarketmap.R;
import jp.noriokun4649.comicmarketmap.dialogfragment.DialogsListener;
import jp.noriokun4649.comicmarketmap.dialogfragment.FragmentSettingAlertDialog;
import jp.noriokun4649.comicmarketmap.list.Setting;
import jp.noriokun4649.comicmarketmap.list.SettingItemAdapter;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/**
 * 設定のアクティビティ.
 */
public class SettingActivity extends CAppCompatActivity implements DialogsListener {

    /**
     * アダプタ.
     */
    private SettingItemAdapter adapter;
    /**
     * 設定項目をいれておくやーつ.
     * 簡単に設定をいれたり、呼び出したりできるから便利なやーつ.
     */
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory2(getLayoutInflater(), new IconicsLayoutInflater2(getDelegate()));
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        setContentView(R.layout.setting_layout);
        final Toolbar toolbar = findViewById(R.id.toolbar3);
        toolbar.setTitle(getString(R.string.Setting));
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_arrow_back).sizeDp(16).color(Color.WHITE));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                finish();
            }
        });
        editor = sharedPreferences.edit();
        final String[] items2 = new String[]{getString(R.string.circle_name), getString(R.string.author_name)};
        final String[] items3 = new String[]{getString(R.string.day_of_the_week), getString(R.string.data), getString(R.string.number_of_times)};
        final String[] items45 = new String[]{getString(R.string.open_map), getString(R.string.open_twitter), getString(R.string.checked_purchased), getString(R.string.always_listen)};
        final String[] items6 = new String[]{getString(R.string.normal), getString(R.string.nighttime)};
        final String[] defo = new String[]{"こかこーら", "ぺぷしこーら", "めっつこーら", "こーらきらい"};
        ListView listView = findViewById(R.id.list_setting);
        ArrayList<Setting> settings = new ArrayList<>();
        int setting2 = sharedPreferences.getInt("setting2", 0);
        int setting3 = sharedPreferences.getInt("setting3", 2);
        int setting4 = sharedPreferences.getInt("setting4", 0);
        int setting5 = sharedPreferences.getInt("setting5", 2);
        int setting6 = sharedPreferences.getInt("setting6", 0);
        settings.add(new Setting(getString(R.string.setting_refine1), sharedPreferences.getBoolean("setting0", true), getString(R.string.setting_refine1_info), true));
        settings.add(new Setting(getString(R.string.setting_refine2), sharedPreferences.getBoolean("setting1", false), getString(R.string.setting_refine2_info), true));
        settings.add(new Setting(getString(R.string.setting_csv_import), setting2, getString(R.string.setting_csv_import_info), false, items2[setting2]));
        settings.add(new Setting(getString(R.string.setting_event_fomart), setting3, getString(R.string.setting_event_fomart_info), false, items3[setting3]));
        settings.add(new Setting(getString(R.string.setting_action_click), setting4, getString(R.string.setting_action_click_info), false, items45[setting4]));
        settings.add(new Setting(getString(R.string.setting_action_long), setting5, getString(R.string.setting_action_long_info), false, items45[setting5]));
        settings.add(new Setting(getString(R.string.setting_theme), setting6, getString(R.string.setting_theme_info), false, items6[setting6]));
        adapter = new SettingItemAdapter(this, settings);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
                Setting setting = adapter.getItem(position);
                switch (view.getId()) {
                    case R.id.checkBox_setting:
                        if (view instanceof Checkable) {
                            CompoundButton compoundButton = (CompoundButton) view;
                            setting.setCheckBoxNow(compoundButton.isChecked());
                            editor.putBoolean("setting" + position, compoundButton.isChecked()).apply();
                        }
                        break;
                    default:
                        String[] items;
                        switch (position) {
                            case 2:
                                items = items2;
                                break;
                            case 3:
                                items = items3;
                                break;
                            case 4:
                            case 5:
                                items = items45;
                                break;
                            case 6:
                                items = items6;
                                break;
                            default:
                                items = defo;
                        }
                        int def = setting.getIndex();
                        FragmentSettingAlertDialog alertDialogFragment = new FragmentSettingAlertDialog();
                        Bundle bundle = new Bundle();
                        bundle.putInt("defaultItem", def);
                        bundle.putStringArray("items", items);
                        bundle.putInt("position", position);
                        alertDialogFragment.setArguments(bundle);
                        alertDialogFragment.show(getSupportFragmentManager(), "da");
                }
            }
        });
    }

    @Override
    public void onItemClick(final int which, final String tag) {

    }

    @Override
    public void onOKClick(final int dialogId, final int position, @Nullable final String returnMemo, final String tag, final String[] items) {
        Setting setting = adapter.getItem(position);
        setting.setIndex(dialogId);
        setting.setNow(items[dialogId]);
        adapter.notifyDataSetChanged();
        editor.putInt("setting" + position, dialogId).commit();
        if (position == 6) {
            ColorfulKt.Colorful().edit()
                    .setDarkTheme(dialogId != 0)
                    .apply(this, new Function0<Unit>() {
                        @Override
                        public Unit invoke() {
                            recreate();
                            return null;
                        }
                    });
        }
        //Log.d("checkedItem:", "" + checkedItems.get(0));
    }
}
