package com.example.myapplication.Interface;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONObject;

public interface networksJO {
    void getVolleyJson(Context context, JSONObject jsonObject, JSONArray jsonArray);
    void getVolleyFromPostJson(Context context, JSONObject jsonObject, JSONArray jsonArray);
    void  geterrorVolley(Context context, String error);
}
