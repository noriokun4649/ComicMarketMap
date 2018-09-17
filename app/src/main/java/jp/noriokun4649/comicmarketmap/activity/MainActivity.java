/*
 * Copyright (c) 2018 noriokun4649.
 */

package jp.noriokun4649.comicmarketmap.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.Iconics;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.context.IconicsLayoutInflater2;

import io.multimoon.colorful.CAppCompatActivity;
import io.multimoon.colorful.ColorfulKt;
import jp.noriokun4649.comicmarketmap.R;
import jp.noriokun4649.comicmarketmap.dialogfragment.DialogsListener;
import jp.noriokun4649.comicmarketmap.dialogfragment.FragmentCancelOKAlertDialog;
import jp.noriokun4649.comicmarketmap.fragment.CircleInfoFragment;
import jp.noriokun4649.comicmarketmap.fragment.CircleListEditFragment;
import jp.noriokun4649.comicmarketmap.fragment.CircleListFragment;
import jp.noriokun4649.comicmarketmap.fragment.CircleListImportFragment;
import jp.noriokun4649.comicmarketmap.fragment.MapFragment;
import jp.noriokun4649.comicmarketmap.twitter.TwitterConnect;

import static android.content.DialogInterface.BUTTON_POSITIVE;

/**
 * メインのアクティビティ.
 */
public class MainActivity extends CAppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, DialogsListener {
    /**
     * Twitterのインスタンス.
     */
    private TwitterConnect twitterConnect = new TwitterConnect(this);

    /**
     * DrawerLayout横から出てくるメニューみたいなやつ.
     */
    private DrawerLayout drawer;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory2(getLayoutInflater(), new IconicsLayoutInflater2(getDelegate()));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        twitterConnect.login();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        Iconics.init(getApplicationContext());
        MapFragment fragment = new MapFragment();
        fragmentTransaction.add(R.id.fragment1, fragment);
        fragmentTransaction.commit();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_map);
        setNaviIcon(navigationView);
    }

    /**
     * 左側から出てくるナビゲーションの設定をするメソッド.
     *
     * @param navi ナビゲーション
     */
    private void setNaviIcon(final NavigationView navi) {
        IconicsDrawable mapIcon = new IconicsDrawable(this, GoogleMaterial.Icon.gmd_map);
        IconicsDrawable circleListIcon = new IconicsDrawable(this, GoogleMaterial.Icon.gmd_view_list);
        IconicsDrawable circleInfoIcon = new IconicsDrawable(this, GoogleMaterial.Icon.gmd_info);
        IconicsDrawable circleListEditIcon = new IconicsDrawable(this, GoogleMaterial.Icon.gmd_edit);
        IconicsDrawable circleListImportIcon = new IconicsDrawable(this, GoogleMaterial.Icon.gmd_input);
        IconicsDrawable codeIcon = new IconicsDrawable(this, GoogleMaterial.Icon.gmd_code);
        IconicsDrawable settingsIcon = new IconicsDrawable(this, GoogleMaterial.Icon.gmd_settings);
        Menu menu = navi.getMenu();
        MenuItem map = menu.findItem(R.id.nav_map);
        MenuItem circleList = menu.findItem(R.id.nav_circlelist);
        MenuItem circleInfo = menu.findItem(R.id.nav_circleinfo);
        MenuItem circleListEdit = menu.findItem(R.id.nav_circlelistedit);
        MenuItem circleListImport = menu.findItem(R.id.nav_circlelistimport);
        MenuItem code = menu.findItem(R.id.nav_oss);
        MenuItem setting = menu.findItem(R.id.nav_setting);
        map.setIcon(mapIcon);
        circleList.setIcon(circleListIcon);
        circleInfo.setIcon(circleInfoIcon);
        circleListEdit.setIcon(circleListEditIcon);
        circleListImport.setIcon(circleListImportIcon);
        code.setIcon(codeIcon);
        setting.setIcon(settingsIcon);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawerd = findViewById(R.id.drawer_layout);
        if (drawerd.isDrawerOpen(GravityCompat.START)) {
            drawerd.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*
        if (id == R.id.action_settings) {
            return true;
        }
        */

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_map:
                MapFragment fragment = new MapFragment();
                fragmentTransaction.replace(R.id.fragment1, fragment, "map");
                break;
            case R.id.nav_circlelist:
                CircleListFragment circleListFragment = new CircleListFragment();
                fragmentTransaction.replace(R.id.fragment1, circleListFragment, "circlelist");
                break;
            case R.id.nav_circleinfo:
                CircleInfoFragment circleInfoFragment = new CircleInfoFragment();
                fragmentTransaction.replace(R.id.fragment1, circleInfoFragment, "circleinfo");
                break;
            case R.id.nav_circlelistedit:
                CircleListEditFragment circleListEditFragment = new CircleListEditFragment();
                fragmentTransaction.replace(R.id.fragment1, circleListEditFragment, "circlelistedit");
                break;
            case R.id.nav_circlelistimport:
                CircleListImportFragment circleListImportFragment = new CircleListImportFragment();
                fragmentTransaction.replace(R.id.fragment1, circleListImportFragment, "circlelistimport");
                break;
            case R.id.nav_setting:
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
                break;
            case R.id.nav_oss:
                Libs.ActivityStyle activityStyle = Libs.ActivityStyle.LIGHT_DARK_TOOLBAR;
                if (ColorfulKt.Colorful().getDarkTheme()) {
                    activityStyle = Libs.ActivityStyle.DARK;
                }
                startActivity(new LibsBuilder()
                        .withFields(R.string.class.getFields())
                        .withActivityTitle(getString(R.string.Oss))
                        .withActivityStyle(activityStyle)
                        .withAboutDescription(getString(R.string.thisoss))
                        .withLibraries("imagelayout", "leaflet", "colorpickerview", "twitter4j", "realm", "realm_java", "android_bootstrap", "cryptore")
                        .withLicenseShown(true)
                        .withAboutIconShown(true)
                        .withVersionShown(true)
                        .withAboutVersionShown(true)
                        .intent(MainActivity.this));
                break;
            default:
        }
        DrawerLayout drawerd = findViewById(R.id.drawer_layout);
        drawerd.closeDrawer(GravityCompat.START);
        fragmentTransaction.commit();
        return true;
    }

    /**
     * TwitterとOAuth認証を行うメソッドfragmentから呼ばれることを想定してる.
     */
    public void signIn() {
        twitterConnect.signIn();
    }

    /**
     * Twitterと連携されているかどうかを返す.
     *
     * @return true:連携されている、false:連携されてない
     */
    public boolean getSignIn() {
        return twitterConnect.getFlag();
    }

    @Override
    protected void onNewIntent(final Intent intent) {
        final Uri uri = intent.getData();
        if (uri != null) {
            final String verifier = uri.getQueryParameter("oauth_verifier");
            if (verifier != null) {
                twitterConnect.getOAuthAsync(verifier);
                twitterConnect.setFlag(true);
                Toast.makeText(getApplicationContext(), getString(R.string.twitter_connect_ok), Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.twitter_connect_error), Toast.LENGTH_SHORT).show();
                twitterConnect.setFlag(false);
            }
        } else {
            Toast.makeText(getApplicationContext(), R.string.twitter_connect_error, Toast.LENGTH_SHORT).show();
            twitterConnect.setFlag(false);
        }
    }

    @Override
    public boolean onKeyDown(final int keyCode, final KeyEvent event) {
        if (keyCode != KeyEvent.KEYCODE_BACK) {
            return super.onKeyDown(keyCode, event);
        } else {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                FragmentCancelOKAlertDialog fragmentCancelOKAlertDialog = new FragmentCancelOKAlertDialog();
                Bundle bundle = new Bundle();
                bundle.putInt("title", 0);
                bundle.putString("massage", getString(R.string.wat_close));
                bundle.putInt("buttonName", R.string.close);
                fragmentCancelOKAlertDialog.setArguments(bundle);
                fragmentCancelOKAlertDialog.show(getSupportFragmentManager(), "app_close");
                return false;
            } else {
                drawer.openDrawer(Gravity.START);
                return false;
            }
        }
    }

    @Override
    public void onItemClick(final int which, final String tag) {
        if (which == BUTTON_POSITIVE) {
            switch (tag) {
                case "disconnect":
                    twitterConnect.logout();
                    break;
                case "app_close":
                    finish();
                    break;
                default:
            }
        }
    }

    @Override
    public void onOKClick(final int dialogId, final int position,
                          @Nullable final String returnMemo, final String tag, final String[] items) {
    }
}
