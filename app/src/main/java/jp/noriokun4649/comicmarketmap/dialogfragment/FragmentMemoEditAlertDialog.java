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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import jp.noriokun4649.comicmarketmap.R;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * フラグメントダイアログです.
 * このフラグメントダイアログはメモ内容を編集するEditAk
 */
public class FragmentMemoEditAlertDialog extends DialogFragment {

    /**
     * メモの内容.
     */
    private String memo;
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
            memo = bundle.getString("memo");
            position = bundle.getInt("position");
        } else {
            throw new RuntimeException("Don't cast to DialogsListener. Activity implements DialogsListener??");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        // カスタムビューを設定
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.memo_input_dialog,
                (ViewGroup) getActivity().findViewById(R.id.layout_root));
        final EditText editText = layout.findViewById(R.id.memo_edit);
        editText.setText(memo);
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setIcon(new IconicsDrawable(getContext(),
                GoogleMaterial.Icon.gmd_edit).sizeDp(12))
                .setTitle(R.string.memo_edit)
                .setView(layout)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int whichButton) {
                        mListener.onOKClick(0, position, editText.getText().toString(), getTag());
                    }
                })
                .setNegativeButton(R.string.cancel, null);
        return alert.create();
    }
}
