package com.example.myapplication.Service;


import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.Interface.VolleyCallback;
import com.example.myapplication.Interface.networksJO;
import com.example.myapplication.Models.Config;
import com.example.myapplication.TaxiDriverApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Networks {

    private networksJO mVolleynetwork;
    private VolleyCallback volleyCallback;
    private Context context;
    private static String TAG = "volley";
    private int statusCode;


    public Networks(Context context, networksJO mVolleynetwork){
        this.context = context;
        this.mVolleynetwork = mVolleynetwork;
    }
    public Networks(Context context, VolleyCallback volleyCallback){
        this.context = context;
        this.volleyCallback = volleyCallback;
    }
    public void getvolley(String Url){

        Log.e(TAG, "getvolley: Url="+Url);
        String tag_string_req = "req_register";
        StringRequest strReq = new StringRequest(Request.Method.GET,
                Url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(Config.TAG1, "get Response: " + response);
                try {
                    //<< get json string from server
                    Object json = new JSONTokener(response).nextValue();
                    if (json instanceof JSONObject) {
                        JSONObject jObj = new JSONObject(response);
                        mVolleynetwork.getVolleyJson(context,jObj,null,statusCode);
                    } else if (json instanceof JSONArray){
                        JSONArray jObj = new JSONArray(response);
                        mVolleynetwork.getVolleyJson(context,null,jObj,statusCode);
                    }
                   /* JSONArray jObj = new JSONArray(response);
                    mVolleynetwork.getVolleyJson(context,jObj);*/
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("error", "Registration Error: " + error.getMessage());
            }
        }) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                statusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };
        // Adding request to request queue
        TaxiDriverApplication.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public void StoreVolleyNetwork(final Map<String, String> params, String Url){

        // Tag used to cancel the request
        String tag_string_req = "req_register";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                Url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(Config.TAG1, "Register Response: " + response);
                try {
                    //<< get json string from server
                    Object json = new JSONTokener(response).nextValue();
                    if (json instanceof JSONObject) {
                        JSONObject jObj = new JSONObject(response);
                        mVolleynetwork.getVolleyFromPostJson(context,jObj,null, statusCode);
                    } else if (json instanceof JSONArray){
                        JSONArray jObj = new JSONArray(response);
                        mVolleynetwork.getVolleyFromPostJson(context,null,jObj, statusCode);
                    }


                   /* JSONArray jObj = new JSONArray(response);
                   mVolleynetwork.getVolleyFromPostJson(context,jObj);*/
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("error", "Registration Error: " + error.getMessage());
                mVolleynetwork.geterrorVolley(context,error.getMessage());
            }

        }) {

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            protected Map<String, String> getParams()  {

                // Posting params to register url
                return params;
            }
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                statusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }

        };
        strReq.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Adding request to request queue
        TaxiDriverApplication.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public void postData(final String mRequestBody, String url, final Map<String, String> postheaders){
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.startsWith("{"))
                    response = response.substring(1);

                Log.d(Config.TAG2, response);
                try {
                    //<< get json string from server
                    Object json = new JSONTokener(response).nextValue();
                    if (json instanceof JSONObject) {
                        JSONObject jObj = new JSONObject(response);
                        mVolleynetwork.getVolleyFromPostJson(context,jObj,null,statusCode);
                    } else if (json instanceof JSONArray){
                        JSONArray jObj = new JSONArray(response);
                        mVolleynetwork.getVolleyFromPostJson(context,null,jObj, statusCode);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", "Registration Error: " + error.getMessage());
                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null && networkResponse.statusCode == 401) {
                    // HTTP Status Code: 401 Unauthorized
                    mVolleynetwork.geterrorVolley(context,"401");
                }
                    mVolleynetwork.geterrorVolley(context, error.getMessage());
            }
        })
        {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                return postheaders;
            }
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                statusCode = response.statusCode;
                String parsed;
                try {
                    parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                } catch (UnsupportedEncodingException e) {
                    parsed = new String(response.data);
                }
                return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,//DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48 (2500 ms par defaut)
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);

    }

    public String EncodeUrl(List<String> paths, Map<String,String> params){
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("api-dev.montaxii.com")
                .appendPath("api")
                .appendPath("v1");

        for(String path: paths) {
            builder.appendPath(path);
        }
        for(Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            builder.appendQueryParameter(key,value);
        }
        return builder.build().toString();
    }

}
