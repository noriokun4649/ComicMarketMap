/*
 * Copyright (c) 2018 noriokun4649.
 */

package jp.noriokun4649.comicmarketmap.dialogfragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import jp.noriokun4649.comicmarketmap.R;

/**
 * フラグメントダイアログです.
 * このフラグメントダイアログでは、アイテムリストを使用したダイアログを表示する際に使用します.
 * <p>
 * パラメータ未定
 */
public class FragmentItemsAlertDialog extends DialogFragment {
    /**
     * ダイアログのタイトルリストです.
     */
    private String[] a;
    /**
     * ダイアログからの操作を受け取るインターフェースです.
     */
    private DialogsListener mListener;
    /**
     * Adapterのポジションです、このポジションを元にAdapterのデータを編集したり削除したりを行います.
     */
    private int position;

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        if (context instanceof DialogsListener) {
            mListener = (DialogsListener) context;
            Bundle bundle = getArguments();
            a = bundle.getStringArray("a");
            position = bundle.getInt("position");
        } else {
            throw new RuntimeException("Don't cast to DialogsListener. Activity implements DialogsListener??");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.please_action);
        builder.setItems(a, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, final int which) {
                mListener.onOKClick(which, position, null, getTag(), null);
            }
        });
        return builder.create();
    }
}
