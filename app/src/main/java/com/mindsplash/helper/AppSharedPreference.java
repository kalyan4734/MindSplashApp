package com.mindsplash.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.mindsplash.network.model.Student;

public class AppSharedPreference {
    private static SharedPreferences sharedPref;
    private static AppSharedPreference appSharedPreference;
    private SharedPreferences.Editor editor;
    private String STUDRNT_DETAILS = "STUDRNT_DETAILS";
    private String STUDRNT_MOBILE_NUMBER = "STUDRNT_MOBILE_NUMBER";
    private String MOBILE_VErIFIED = "MOBILE_VErIFIED";
    private String STUDRNT_REGISTERED = "STUDRNT_REGISTERED";

    public static AppSharedPreference getMInstance(){
        if (appSharedPreference == null)
            appSharedPreference = new AppSharedPreference();
        return appSharedPreference;
    }

    public static SharedPreferences getInstance(Context context) {
        if (sharedPref == null)
            sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPref;
    }

    public void clearAll(SharedPreferences sharedPref) {
        editor = sharedPref.edit();
        editor.clear();
        editor.apply();
    }

    public void addStudentDetails(String studentJson) {
        editor = sharedPref.edit();
        editor.putString(STUDRNT_DETAILS, studentJson);
        editor.apply();
    }

    public Student getStudent() {
        Student student = new Gson().fromJson(appSharedPreference.getStudentDetails(), Student.class);
        return student;
    }

    public void addStudentMobileNo(String mobileNumber) {
        editor = sharedPref.edit();
        editor.putString(STUDRNT_MOBILE_NUMBER, mobileNumber);
        editor.apply();
    }

    public void setMobileVerified(boolean verified) {
        editor = sharedPref.edit();
        editor.putBoolean(MOBILE_VErIFIED, verified);
        editor.apply();
    }

    public void setRegistered(boolean registered) {
        editor = sharedPref.edit();
        editor.putBoolean(STUDRNT_REGISTERED, registered);
        editor.apply();
    }

    public boolean isMobileVerified(){
        return sharedPref.getBoolean(MOBILE_VErIFIED,false);
    }

    public boolean isRegistered(){
        return sharedPref.getBoolean(STUDRNT_REGISTERED,false);
    }

    public String getStudentDetails(){
        return sharedPref.getString(STUDRNT_DETAILS,"");
    }

    public String getStudentMobileNo(){
        return sharedPref.getString(STUDRNT_MOBILE_NUMBER,"");
    }
}
