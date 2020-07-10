package com.example.myapplication.Models.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.myapplication.Models.Driver;
import com.google.gson.Gson;

public class SessionDriver {

    // LogCat tag
    private static String TAG = SessionDriver.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    SharedPreferences.Editor editor;
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "driver";

    private static final String KEY_IS_DRIVER = "isdriver";

    public SessionDriver(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setDriver(Driver driverModel) {

        // editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
        Gson gson = new Gson();
        String kwatt1 = gson.toJson(driverModel);
        editor.putString(KEY_IS_DRIVER, kwatt1);

        // commit changes
        editor.commit();

        Log.d(TAG, "Driver login session modified!");
    }
    public boolean updateDriver(Driver driverModel) {

        // editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
        Gson gson = new Gson();
        String kwatt1 = gson.toJson(driverModel);
        editor.putString(KEY_IS_DRIVER, kwatt1);

        // apply changes
        return  editor.commit();

        //Log.d(TAG, "User login session modified!");
    }

    public Driver getDriver(){
        //return pref.getString(KEY_IS_KWATT, "");
        Gson gson = new Gson();
        String json = pref.getString(KEY_IS_DRIVER, "");
        Driver userModel = gson.fromJson(json, Driver.class);
        return userModel;
    }

    public void removeDriver(){
        editor.clear();
        editor.commit();
    }
}
