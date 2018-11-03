/*
 * Copyright (c) 2018 noriokun4649.
 */

package jp.noriokun4649.comicmarketmap.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.snackbar.Snackbar;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialize.MaterializeBuilder;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import jp.noriokun4649.comicmarketmap.R;
import jp.noriokun4649.comicmarketmap.activity.ImportBackupActivity;
import jp.noriokun4649.comicmarketmap.activity.ImportCSVActivity;
import jp.noriokun4649.comicmarketmap.activity.ImportFollowActivity;
import jp.noriokun4649.comicmarketmap.activity.ImportListListActivity;
import jp.noriokun4649.comicmarketmap.activity.MainActivity;
import jp.noriokun4649.comicmarketmap.list.Import;
import jp.noriokun4649.comicmarketmap.list.ListItemAdapter;

/**
 * サークルリストインポートのフラグメントです.
 * このフラグメントでは各インポート方式を追加します
 */
public class CircleListImportFragment extends Fragment {
    /**
     * スナックバーをクリックしたさいのリスナーです.
     */
    private View.OnClickListener connect = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            Activity activity = getActivity();
            if (activity instanceof MainActivity) {
                MainActivity maActivity = (MainActivity) activity;
                maActivity.signIn();
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.circle_list_import_layout, container, false);
        new MaterializeBuilder().withActivity(getActivity()).build();
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.CircleListImport));
        toolbar.getMenu().clear();
        ListView listView = view.findViewById(R.id.list);
        ArrayList<Import> imports = new ArrayList<>();
        Import twitter = new Import(CommunityMaterial.Icon.cmd_twitter, getString(R.string.twitter_conect));
        Import follow = new Import(CommunityMaterial.Icon.cmd_account_check, getString(R.string.follow_import));
        Import list = new Import(GoogleMaterial.Icon.gmd_playlist_add_check, getString(R.string.list_import));
        Import csv = new Import(CommunityMaterial.Icon.cmd_file_delimited, getString(R.string.csv_import));
        Import backup = new Import(GoogleMaterial.Icon.gmd_settings_backup_restore, getString(R.string.backup_import));
        imports.add(twitter);
        imports.add(follow);
        imports.add(list);
        imports.add(csv);
        imports.add(backup);
        ListItemAdapter adapter = new ListItemAdapter(getContext(), imports);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
                Activity activity = getActivity();
                MainActivity mainActivity;
                if (activity instanceof MainActivity) {
                    mainActivity = (MainActivity) activity;
                } else {
                    mainActivity = new MainActivity();
                }
                switch (position) {
                    case 0:
                        mainActivity.signIn();
                        return;
                    case 1:
                        if (mainActivity.getSignIn()) {
                            startActivity(new Intent(getActivity(), ImportFollowActivity.class));
                        } else {
                            Snackbar.make(view, getString(R.string.twitter_connect_please),
                                    Snackbar.LENGTH_LONG).setAction(R.string.connect, connect).show();
                        }
                        return;
                    case 2:
                        if (mainActivity.getSignIn()) {
                            startActivity(new Intent(getActivity(), ImportListListActivity.class));
                        } else {
                            Snackbar.make(view, getString(R.string.twitter_connect_please),
                                    Snackbar.LENGTH_LONG).setAction(R.string.connect, connect).show();
                        }
                        return;
                    case 3:
                        startActivity(new Intent(getActivity(), ImportCSVActivity.class));
                        return;
                    case 4:
                        startActivity(new Intent(getActivity(), ImportBackupActivity.class));
                        return;
                    default:
                }
            }
        });

        return view;
    }

}
