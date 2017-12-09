package com.example.sontbv.wallpaper.App;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by sontbv on 12/9/17.
 */

public class AppController extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name("wallpaper.realm")
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }
}
