package com.etuloser.padma.rohit.inclass07;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Rohit on 4/24/2017.
 */

public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration  config=new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(config);

    }
}
