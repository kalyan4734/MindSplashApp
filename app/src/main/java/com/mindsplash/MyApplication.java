package com.mindsplash;

import android.app.Application;
import android.content.Context;

import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;

public class MyApplication extends Application {
    private static MyApplication myApplication;
    private static DataStore<Preferences> dataStore;

    public static Context getAppContext() {
        if (myApplication == null)
            myApplication = new MyApplication();
        return myApplication;
    }

    public static DataStore<Preferences> getDataStoreInstance() {
        if (dataStore == null)
            dataStore = new RxPreferenceDataStoreBuilder(getAppContext(),"settings").build();
        return dataStore;
    }


}
