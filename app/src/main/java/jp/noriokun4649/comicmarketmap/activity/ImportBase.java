/*
 * Copyright (c) 2018 noriokun4649.
 */

package jp.noriokun4649.comicmarketmap.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.danielnilsson9.colorpickerview.dialog.ColorPickerDialogFragment;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.context.IconicsLayoutInflater2;

import java.util.ArrayList;

import jp.noriokun4649.comicmarketmap.R;
import jp.noriokun4649.comicmarketmap.dialogfragment.DialogsListener;
import jp.noriokun4649.comicmarketmap.dialogfragment.FragmentEditAlertDialog;
import jp.noriokun4649.comicmarketmap.dialogfragment.FragmentItemsAlertDialog;
import jp.noriokun4649.comicmarketmap.dialogfragment.FragmentMemoEditAlertDialog;
import jp.noriokun4649.comicmarketmap.list.Circle;
import jp.noriokun4649.comicmarketmap.list.CircleListItemAdapter;
import jp.noriokun4649.comicmarketmap.twitter.TwitterConnect;
import twitter4j.AsyncTwitter;

/**
 * インポートアクティビティのベースです.
 * 様々なインポート方法ののちに表示されるユーザー結果を表示するさいに継承します。
 */
abstract class ImportBase extends AppCompatActivity
        implements ColorPickerDialogFragment.ColorPickerDialogListener, DialogsListener {

    /**
     * ダイアログのタイトルリストです.
     */
    private String[] a;

    /**
     * サークルリストのリストViewのアダプタでしゅ.
     */
    private CircleListItemAdapter adapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory2(getLayoutInflater(), new IconicsLayoutInflater2(getDelegate()));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.import_layout);
        getSupportActionBar().hide();
        Intent intent = getIntent();
        int counts = intent.getIntExtra("count", 0);
        long id = intent.getLongExtra("listId", 0);
        a = new String[]{getString(R.string.open_twitter), getString(R.string.day_edit),
                getString(R.string.space_edit),
                getString(R.string.name_edit), getString(R.string.delete)};
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getTitles());
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_arrow_back).sizeDp(16).color(Color.WHITE));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                finish();
            }
        });
        TextView textView = findViewById(R.id.textView4);
        textView.setText(getLoadingText());
        final ListView listView = findViewById(R.id.follow_import_list);
        ArrayList<Circle> circles = new ArrayList<>();
        adapter = new CircleListItemAdapter(this, circles);
        TwitterConnect twitterConnect = new TwitterConnect(this);
        twitterConnect.login();
        final AsyncTwitter asyncTwitter = twitterConnect.getmTwitter();
        getListData(asyncTwitter, adapter, counts, id);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
                final Circle circle = adapter.getItem(position);
                switch (view.getId()) {
                    case R.id.color_button:
                        final ColorPickerDialogFragment dialogFragment = new ColorPickerDialogFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", position);
                        bundle.putString("title", getString(R.string.plese_color));
                        bundle.putString("ok_button", "OK");
                        bundle.putBoolean("alpha", false);
                        bundle.putInt("init_color", circle.getColor());
                        dialogFragment.setArguments(bundle);
                        dialogFragment.show(getFragmentManager(), "color");
                        //ColorPickerDialogFragment;
                        break;
                    case R.id.memo_button:
                        // カスタムビューを設定
                        FragmentMemoEditAlertDialog fragmentMemoEditAlertDialog = new FragmentMemoEditAlertDialog();
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("memo", circle.getMemo());
                        bundle1.putInt("position", position);
                        fragmentMemoEditAlertDialog.setArguments(bundle1);
                        fragmentMemoEditAlertDialog.show(getSupportFragmentManager(), "edit_memo");
                        break;
                    case R.id.card:
                        FragmentItemsAlertDialog fragmentItemsAlertDialog = new FragmentItemsAlertDialog();
                        Bundle bundle2 = new Bundle();
                        bundle2.putStringArray("a", a);
                        bundle2.putInt("position", position);
                        fragmentItemsAlertDialog.setArguments(bundle2);
                        fragmentItemsAlertDialog.show(getSupportFragmentManager(), "item_list");
                    default:
                }
            }
        });
        FloatingActionButton fav = findViewById(R.id.fav_save);
        fav.setImageDrawable(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_save));
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
            }
        });
    }

    /**
     * EditTextを使用したダイアログの処理.
     *
     * @param position ポジション、Adapterの情報の何番目か
     * @param ad       String配列、ダイアログに表示されてたタイトルが入ってる
     * @param type     タイプ、一つ前のダイアログで選択されたポジションが入ってる
     */
    private void showEditTextDialog(final int position, final String[] ad, final int type) {
        final FragmentEditAlertDialog editAlertDialog = new FragmentEditAlertDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putInt("type", type);
        bundle.putStringArray("a", ad);
        editAlertDialog.setArguments(bundle);
        editAlertDialog.show(getSupportFragmentManager(), "edit");
    }

    @Override
    public void onColorSelected(final int dialogId, final int color) {
        Circle circle = adapter.getItem(dialogId);
        circle.setColor(color);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDialogDismissed(final int dialogId) {

    }

    /**
     * リストViewのアダプタにサークル情報を入れる処理をするメソッド.
     *
     * @param asyncTwitter Twitterの非同期処理Twitterから情報を得る際にのみ使用する
     * @param adapters     リストViewのアダプタ
     * @param counts       TwitterのListから取得時のみ使用するカウント
     * @param id           TwitterのListから取得時のみ使用するListのID
     */
    abstract void getListData(AsyncTwitter asyncTwitter, CircleListItemAdapter adapters, int counts, long id);

    /**
     * ToolBarのタイトルを設定する(例：R.string.follow_import).
     *
     * @return R.string.xxxxx
     */
    abstract int getTitles();

    /**
     * ロード中のメッセージ内容を指定する(例：R.string.follow_import).
     *
     * @return R.string.xxxxxx
     */
    abstract int getLoadingText();

    @Override
    public void onOKClick(final int dialogId, final int position, @Nullable final String returnMemo, final String tag) {
        switch (tag) {
            case "edit":
                Circle circle = adapter.getItem(position);
                switch (dialogId) {
                    case 1:
                        circle.setDay(returnMemo);
                        break;
                    case 2:
                        circle.setBlock(returnMemo);
                        break;
                    case 3:
                        circle.setUserName(returnMemo);
                        break;
                    default:
                }
                adapter.notifyDataSetChanged();
                break;
            case "edit_memo":
                Circle circle1 = adapter.getItem(dialogId);
                circle1.setMemo(returnMemo);
                break;
            case "item_list":
                final Circle circle2 = adapter.getItem(position);
                switch (dialogId) {
                    case 0:
                        Uri uri2 = Uri.parse("https://twitter.com/" + circle2.getScreenName().replace("@", "") + "/");
                        Intent i2 = new Intent(Intent.ACTION_VIEW, uri2);
                        startActivity(i2);
                        break;
                    case 4:
                        adapter.remove(circle2);
                        final CoordinatorLayout layout = findViewById(R.id.coord);
                        Snackbar.make(layout, R.string.item_delete, Snackbar.LENGTH_LONG).setAction(R.string.undo, new View.OnClickListener() {
                            @Override
                            public void onClick(final View v) {
                                adapter.insert(circle2, position);
                            }
                        }).show();
                        break;
                    default:
                        showEditTextDialog(position, a, dialogId);
                }
                break;
            default:
        }
    }
}
