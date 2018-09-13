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
import android.text.InputType;
import android.widget.EditText;

import jp.noriokun4649.comicmarketmap.R;

/**
 * フラグメントダイアログです.
 * このフラグメントダイアログは編集するEditAk
 */
public class FragmentEditAlertDialog extends DialogFragment {
    /**
     * リストアイテムのタイトルが格納されている配列です.
     */
    private String[] items;
    /**
     * 　一つ前のダイアログで選択したポジションが入っている.
     */
    private int defaultItem;
    /**
     * Adapterのポジションです、このポジションを元にAdapterのデータを編集したり削除したりを行います.
     */
    private int position;
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
            position = bundle.getInt("position", 0);
            items = bundle.getStringArray("a");
            defaultItem = bundle.getInt("type", 0);
        } else {
            throw new RuntimeException("Don't cast to DialogsListener. Activity implements DialogsListener??");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        final EditText editText = new EditText(getContext());
        editText.setMaxLines(1);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext()).setTitle(items[defaultItem]).setView(editText).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialog, final int whichButton) {
                mListener.onOKClick(defaultItem, position, editText.getText().toString(), getTag());
            }
        })
                .setNegativeButton(R.string.cancel, null);
        return alert.create();
    }
}
