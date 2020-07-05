package com.example.myapplication.Interface;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface VolleyCallback {

    void notifySuccess(JSONObject response);
    void notifyError(VolleyError response);
}
