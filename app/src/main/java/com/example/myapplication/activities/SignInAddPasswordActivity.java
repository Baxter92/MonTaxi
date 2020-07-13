package com.example.myapplication.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Interface.networksJO;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Models.Config;
import com.example.myapplication.R;
import com.example.myapplication.Service.Networks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignInAddPasswordActivity extends AppCompatActivity implements networksJO {

    private EditText passwdEdt;
    private EditText confpasswdEdt;
    private Button validBtn;
    ProgressDialog progressDialog;
    String oldpassword;
    String token;
    int screensize;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_pwd);
        oldpassword = getIntent().getStringExtra("password");
        token = getIntent().getStringExtra("token");
        init();
    }

    private void init() {
        progressDialog = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(true);

        screensize = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;

        passwdEdt = findViewById(R.id.pwd);
        confpasswdEdt = findViewById(R.id.pwd2);
        validBtn = findViewById(R.id.email_sign_in_button);
        validBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!passwdEdt.getText().toString().isEmpty()&& ! confpasswdEdt.getText().toString().isEmpty()){
                    if (validPassword(passwdEdt.getText().toString(), confpasswdEdt.getText().toString())){
                        SignUp(oldpassword,passwdEdt.getText().toString());
                    }/*else {
                        //Toast.makeText(view.getContext(), getString(R.string.password_similar),Toast.LENGTH_LONG).show();
                        Config.Alert(SignInAddPasswordActivity.this,getString(R.string.password_similar),false);
                    }*/
                }/*else {
                    //Toast.makeText(view.getContext(), getString(R.string.sign_required),Toast.LENGTH_LONG).show();
                    Config.Alert(SignInAddPasswordActivity.this,getString(R.string.sign_required),false);
                }*/
            }
        });
        passwdEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.toString().length() > 4){
                    if (screensize == Configuration.SCREENLAYOUT_SIZE_LARGE)
                        passwdEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_green_2, 0);
                    else
                        passwdEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_green, 0);
                }else if ((charSequence.toString().length() > 0)&& (charSequence.toString().length() < 5))
                {
                    if (screensize == Configuration.SCREENLAYOUT_SIZE_LARGE)
                        passwdEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_red_2, 0);
                    else
                        passwdEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_red, 0);
                }else {
                    if (screensize == Configuration.SCREENLAYOUT_SIZE_LARGE)
                        passwdEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_grey_2, 0);
                    else
                        passwdEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_grey, 0);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        confpasswdEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.toString().length() > 4){
                    if (screensize == Configuration.SCREENLAYOUT_SIZE_LARGE)
                        confpasswdEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_green_2, 0);
                    else
                        confpasswdEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_green, 0);
                }else if ((charSequence.toString().length() > 0)&& (charSequence.toString().length() < 5))
                {
                    if (screensize == Configuration.SCREENLAYOUT_SIZE_LARGE)
                        confpasswdEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_red_2, 0);
                    else
                        confpasswdEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_red, 0);
                }else {
                    if (screensize == Configuration.SCREENLAYOUT_SIZE_LARGE)
                        confpasswdEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_grey_2, 0);
                    else
                        confpasswdEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_grey, 0);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void SignUp(String oldpassword, String password) {
        showDialog(getString(R.string.update_password));
        Map<String, String> params = new HashMap<>();
        params.put("oldPassword",oldpassword);
        params.put("newPassword",password);

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization",token);

        Log.e("jsondata","oldpassword "+oldpassword+" newpassword "+password+" token "+token);

        Networks networks = new Networks(SignInAddPasswordActivity.this,this);
        JSONObject jsondata = new JSONObject(params);
        networks.postData(jsondata.toString(), Config.changePassword,headers);
    }

    private boolean validPassword(String password, String password2){
        return password.equals(password2);
    }

    @Override
    public void getVolleyJson(Context context, JSONObject jsonObject, JSONArray jsonArray, int StatusCode) {

    }

    @Override
    public void getVolleyFromPostJson(Context context, JSONObject jsonObject, JSONArray jsonArray, int StatusCode) {
        hideDialog();
        Log.e("jsondata","status code "+StatusCode);
      //  if (StatusCode == 200){
            try {
                if (jsonObject.has("result") && jsonObject.getString("result").equals("success") ){
                    startActivity(new Intent(SignInAddPasswordActivity.this, SignInActivity.class));
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        //}
    }

    @Override
    public void geterrorVolley(Context context, String error) {
            hideDialog();
        if (error == null){
            Config.Alert(SignInAddPasswordActivity.this,getString(R.string.un_authorize),false);
        }
    }

    private void showDialog(String message){
        progressDialog.setMessage(message);
        progressDialog.show();
    }
    private void hideDialog(){
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
