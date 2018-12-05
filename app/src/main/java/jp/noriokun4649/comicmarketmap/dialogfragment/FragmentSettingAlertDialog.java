/*
 * Copyright (c) 2018 noriokun4649.
 */

package jp.noriokun4649.comicmarketmap.dialogfragment;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AlertDialog;

import jp.noriokun4649.comicmarketmap.R;

/**
 * 設定で使用するダイアログです.
 */
public class FragmentSettingAlertDialog extends DialogFragment {
    /**
     * List形式のダイアログに表示するアイテムの配列です.
     */
    private String[] items;
    /**
     * デフォルトでチェックボックスがONになっているアイテムです.
     */
    private int defaultItem;
    /**
     * ダイアログからの操作を受け取るインターフェースです.
     */
    private DialogsListener mListener;

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        if (context instanceof DialogsListener) {
            mListener = (DialogsListener) context;
            Bundle bundle = getArguments();
            items = bundle.getStringArray("items");
            defaultItem = bundle.getInt("defaultItem", 0);
        } else {
            throw new RuntimeException("Don't cast to DialogsListener. Activity implements DialogsListener??");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity())
                .setSingleChoiceItems(items, defaultItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        defaultItem = which;
                        getArguments().putInt("defaultItem", which);
                        mListener.onItemClick(which, getTag());
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        mListener.onOKClick(defaultItem, getArguments().getInt("position"), null, getTag(), items);
                    }
                })
                .setNegativeButton(R.string.cancel, null);
        return alert.create();
    }


}