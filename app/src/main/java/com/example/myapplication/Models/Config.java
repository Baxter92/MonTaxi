package com.example.myapplication.Models;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.R;

public class Config {
    public static final String Home = "home";
    public static final String EditProfil = "editprofil";
    public static final String TAG1 = "volley_response";
    public static final String TAG2 = "volley_response_json";
    public static final String TAG3 = "firestore_response";
    public static final String cameroonFlag = "+237";
    private static AlertDialog show;
    private static ProgressDialog progressDialog;

    public static final String signIn = "http://api-dev.montaxii.com/api/v1/drivers/login";
    public static final String changePassword = "http://api-dev.montaxii.com/api/v1/drivers/change-password";
    public static final String recoveraccount = "http://api-dev.montaxii.com/api/v1/drivers/recover-account";
    public static final String chatcomment = "http://api-dev.montaxii.com/api/v1/comments";
    public static final String resetPassword = "http://api-dev.montaxii.com/api/v1/drivers/reset-password";
    public static final String resetProfil = "http://api-dev.montaxii.com/api/v1/drivers/drivers/";

    public static void Alert(Context context, String message, boolean success){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View layoutView =  inflater.inflate(R.layout.alertnotification, null);
        builder.setView(layoutView);
        TextView textView = (TextView)layoutView.findViewById(R.id.message);
        textView.setText(message);
        if (success)
            textView.setTextColor(context.getResources().getColor(R.color.commentsent));
        else
            textView.setTextColor(context.getResources().getColor(R.color.red));

         show = builder.show();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run()
            {
                show.dismiss();
            }
        }, 3000);
    }

    public static void dismiss(){
        show.dismiss();
    }

    public static void ProgressDialog(Context context){
        progressDialog = new ProgressDialog(context, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(true);
    }

    public static void showDialog(String message){
        progressDialog.setMessage(message);
        progressDialog.show();
    }
    public static void hideDialog(){
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public static void showProgressDialog(View view){
        view.setVisibility(View.VISIBLE);
    }
    public static void hideProgressDialog(View view){
        view.setVisibility(View.GONE);
    }
}
