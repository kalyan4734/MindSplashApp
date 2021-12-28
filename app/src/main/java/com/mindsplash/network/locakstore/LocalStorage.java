package com.mindsplash.network.locakstore;

import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import androidx.datastore.rxjava3.RxDataStore;

import com.mindsplash.MyApplication;
import com.mindsplash.helper.Constants;
import com.mindsplash.network.model.Student;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class LocalStorage {
    private static LocalStorage localStorage;

    public static LocalStorage getInstance() {
        if (localStorage == null)
            localStorage = new LocalStorage();
        return localStorage;
    }

    public void addMobileNumber(String mobileNumber) {
        RxDataStore.updateDataAsync(MyApplication.getDataStoreInstance(),
                prefsIn -> {
                    MutablePreferences mutablePreferences = prefsIn.toMutablePreferences();
                    mutablePreferences.set(PreferencesKeys.stringKey(Constants.MOB_NO), mobileNumber);
                    return Single.just(mutablePreferences);
                });
    }

    public void addStudentDetails(Student student) {
        RxDataStore.updateDataAsync(MyApplication.getDataStoreInstance(),
                prefsIn -> {
                    MutablePreferences mutablePreferences = prefsIn.toMutablePreferences();
                    mutablePreferences.set(PreferencesKeys.stringKey(Constants.MOB_NO), "1");
                    return Single.just(mutablePreferences);
                });
    }

    public String getMobileNumber() {
        Preferences.Key<String> idKey = PreferencesKeys.stringKey(Constants.MOB_NO);
        Flowable<String> mobileNumber =
                RxDataStore.data(MyApplication.getDataStoreInstance()).map(prefs -> prefs.get(idKey));
        return mobileNumber.toString();
    }
}
