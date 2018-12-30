/*
 * Copyright (c) 2018 noriokun4649.
 */

package jp.noriokun4649.comicmarketmap.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import jp.noriokun4649.comicmarketmap.R;
import jp.noriokun4649.comicmarketmap.asycktasks.AsyncTaskClass;
import jp.noriokun4649.comicmarketmap.list.CircleListItemAdapter;
import twitter4j.AsyncTwitter;

/**
 * CSVファイルからインポートする際のアクティビティです.
 */
public class ImportCSVActivity extends ImportBase {

    /**
     * インテントコールバック識別用のコード.
     */
    private static final int REQUEST_CODE = 1002;

    /**
     * サークルリストのリストViewのアダプタでしゅ.
     */
    private CircleListItemAdapter adapter;

    /**
     * 進捗状況を示すレイアウトのテキスト部分です.
     */
    private TextView textView;

    /**
     * 進捗状況を示すレイアウトで、すべての処理が完了後に非表示にします.
     */
    private LinearLayout linearLayout;

    /**
     * 進捗状況を示すレイアウトのプログレスバー部分です.
     */
    private ProgressBar progressBar;

    /**
     * CSVファイルを読み込む処理を行う非同期処理クラスです.
     */
    private AsyncTaskClass asyncTaskClass;

    @Override
    void getListData(final AsyncTwitter asyncTwitter, final CircleListItemAdapter adapters, final int counts, final long id) {
        this.adapter = adapters;
        linearLayout = findViewById(R.id.progress);
        textView = findViewById(R.id.textView4);
        progressBar = findViewById(R.id.progressBar3);
        openFile();
    }

    @Override
    int getTitles() {
        return R.string.csv_import;
    }

    @Override
    int getLoadingText() {
        return R.string.define_int_android_maps_utils;
    }

    @Override
    public void onOKClick(final int dialogId, final int position, @Nullable final String returnMemo, final String tag, final String[] items) {
        super.onOKClick(dialogId, position, returnMemo, tag, items);
    }

    @Override
    public void onItemClick(final int which, final String tag) {
    }

    @SuppressLint("StringFormatMatches")
    @Override
    public void onActivityResult(final int requestCode, final int resultCode,
                                 final Intent resultData) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK && resultData != null && resultData.getData() != null) {
            Uri uri = resultData.getData();
            try (Cursor cursor = getContentResolver().query(uri, null,
                    null, null, null, null)) {
                if (cursor != null) {
                    InputStream inputStream = getContentResolver().openInputStream(uri);
                    cursor.moveToFirst();
                    final String displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    if (isCsv(displayName)) {
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                        InputStream inputStream2 = getContentResolver().openInputStream(uri);
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream2));
                        String[] strings = reader.readLine().split(",");
                        reader.close();
                        textView.setText(R.string.loadding_now);
                        asyncTaskClass = new AsyncTaskClass(linearLayout, adapter, textView, progressBar, strings[3], sharedPreferences);
                        asyncTaskClass.execute(inputStream);
                    } else {
                        createSnackBar(R.string.csv_file_please);
                    }
                } else {
                    createSnackBar(R.string.plase_tekisetu_file);
                }
            } catch (Exception er) {
                er.printStackTrace();
                createSnackBar(R.string.file_import_error, er.getMessage());
            }
        } else {
            createSnackBar(R.string.file_get_error);
        }
    }

    /**
     * スナックバーを作成するメソッドです.
     *
     * @param massage      スナックバーに表示するメッセージを指定します。
     * @param errorMessage エラーメッセージです
     */
    private void createSnackBar(final String massage, final String errorMessage) {
        progressBar.setVisibility(View.GONE);
        String text = massage + "\n\n" + errorMessage;
        textView.setText(text);
        final CoordinatorLayout layout = findViewById(R.id.coord);
        Snackbar.make(layout, massage, Snackbar.LENGTH_INDEFINITE).setAction(R.string.select, new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                openFile();
            }
        }).show();
    }

    /**
     * スナックバーを作成するメソッドです.
     *
     * @param massage スナックバーに表示するメッセージを指定します。
     */
    private void createSnackBar(final String massage) {
        createSnackBar(massage, "");
    }

    /**
     * スナックバーを作成するメソッドです.
     *
     * @param massage スナックバーに表示するメッセージのリソースIDを指定します。
     */
    private void createSnackBar(final int massage) {
        createSnackBar(getString(massage), "");
    }

    /**
     * スナックバーを作成するメソッドです.
     *
     * @param massage スナックバーに表示するメッセージのリソースIDを指定します。
     * @param err     エラーメッセージです。
     */
    private void createSnackBar(final int massage, final String err) {
        createSnackBar(getString(massage), err);
    }

    /**
     * Android標準フレームワーク、SAF(ストレージアクセスフレームワーク)を使用してCSVファイルを取得するめソーメン.
     */
    private void openFile() {
        String[] mimeType = new String[]{"text/comma-separated-values", "text/csv"};
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeType);
        startActivityForResult(Intent.createChooser(intent, null), REQUEST_CODE);
    }

    /**
     * パラメータのファイル名の拡張子からCSVファイルかどうかを判定します.
     * SAFのMIMEにてCSVファイルを指定していますが、外部アプリを経由した場合のためにCheckをシています.
     *
     * @param filename ファイル名
     * @return CSVファイルかどうか、trueの場合はCSVファイル。
     */
    private boolean isCsv(final String filename) {
        return filename.matches(".*\\.csv$");
    }

    /**
     * ファイルサイズから単位をつけるやつ.
     *
     * @param size さいず
     * @return 文字列であり、単位を付け足したサイズ
     */
    private String getSizeStr(final float size) {
        if (1024 > size) {
            return size + " B";
        } else if (1024 * 1024 > size) {
            double detaSize = size;
            detaSize = detaSize / 1024;
            BigDecimal bi = new BigDecimal(String.valueOf(detaSize));
            double value = bi.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
            return value + " KB";
        } else {
            double detaSize = size;
            detaSize = detaSize / 1024 / 1024;
            BigDecimal bi = new BigDecimal(String.valueOf(detaSize));
            double value = bi.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
            return value + " MB";
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (asyncTaskClass != null) {
            asyncTaskClass.cancel(true);
        }
    }
}
