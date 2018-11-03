/*
 * Copyright (c) 2018 noriokun4649.
 */

package jp.noriokun4649.comicmarketmap;

import com.beardedhen.androidbootstrap.TypefaceProvider;

import androidx.multidex.MultiDexApplication;
import io.multimoon.colorful.ColorfulKt;
import io.multimoon.colorful.Defaults;
import io.multimoon.colorful.ThemeColor;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * アプリが実行された際に1回のみ処理される初期化処理.
 */
public class MyApp extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        TypefaceProvider.registerDefaultIconSets();
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().name("noriokunrealm.realm").build();
        Defaults defaults = new Defaults(ThemeColor.BLUE, ThemeColor.GREEN, false, false, 0);
        ColorfulKt.initColorful(this, defaults);
    }
}
