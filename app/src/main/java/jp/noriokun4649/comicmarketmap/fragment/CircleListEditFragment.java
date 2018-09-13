/*
 * Copyright (c) 2018 noriokun4649.
 */

package jp.noriokun4649.comicmarketmap.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jp.noriokun4649.comicmarketmap.R;

/**
 * サークルリスト編集のフラグメントです.
 */
public class CircleListEditFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.circle_list_edit_layout, container, false);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.CircleListEdit));

        return view;

    }
}
