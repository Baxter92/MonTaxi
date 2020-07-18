package com.example.myapplication.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

public class RecorvedNewPassword extends AppCompatActivity implements networksJO {

    private EditText passwEdt;
    private EditText confpasswEdt;
    private Button updateBtn;
    private TextView mTextViewReceivesms;
    int screensize;
    private String phone, pincode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        init();
    }

    private void init() {
        phone = getIntent().getStringExtra("phone");
        pincode = getIntent().getStringExtra("pincode");

        passwEdt = findViewById(R.id.pwd1);
        confpasswEdt = findViewById(R.id.pwd2);
        mTextViewReceivesms = (TextView)findViewById(R.id.receivesms);
        mTextViewReceivesms.setText(getString(R.string.recorver_two,phone.substring(phone.length()-2)));
        updateBtn = findViewById(R.id.update);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!passwEdt.getText().toString().isEmpty() && !confpasswEdt.getText().toString().isEmpty()){
                    if (passwEdt.getText().toString().equals(confpasswEdt.getText().toString())){
                        recorver(phone,pincode,passwEdt.getText().toString());
                    }
                }
            }
        });
        setListenner();
    }

    private void recorver(String phone, String pincode, String password) {
        Config.ProgressDialog(RecorvedNewPassword.this);
        Config.showDialog("");
        Networks networks = new Networks(RecorvedNewPassword.this,this);

        Map<String,String> params = new HashMap<>();
        params.put("phone",phone);
        params.put("password",password);
        params.put("pincode",pincode);

        JSONObject jsonParams = new JSONObject(params);
        networks.postData(jsonParams.toString(),Config.recoveraccount,new HashMap<String, String>());
    }

    private void setListenner() {
        passwEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() > 4) {
                    if (screensize == Configuration.SCREENLAYOUT_SIZE_LARGE)
                        passwEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_green_2, 0);
                    else
                        passwEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_green, 0);

                } else if (charSequence.toString().length() < 4 && charSequence.toString().length() != 0) {
                    if (screensize == Configuration.SCREENLAYOUT_SIZE_LARGE)
                        passwEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_red_2, 0);
                    else
                        passwEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_red, 0);
                } else {
                    if (screensize == Configuration.SCREENLAYOUT_SIZE_LARGE)
                        passwEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_grey_2, 0);
                    else
                        passwEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_grey, 0);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        passwEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() > 4) {
                    if (screensize == Configuration.SCREENLAYOUT_SIZE_LARGE)
                        confpasswEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_green_2, 0);
                    else
                        confpasswEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_green, 0);

                } else if (charSequence.toString().length() < 4 && charSequence.toString().length() != 0) {
                    if (screensize == Configuration.SCREENLAYOUT_SIZE_LARGE)
                        confpasswEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_red_2, 0);
                    else
                        confpasswEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_red, 0);
                } else {
                    if (screensize == Configuration.SCREENLAYOUT_SIZE_LARGE)
                        confpasswEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_grey_2, 0);
                    else
                        confpasswEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_grey, 0);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    @Override
    public void getVolleyJson(Context context, JSONObject jsonObject, JSONArray jsonArray, int code) {

    }

    @Override
    public void getVolleyFromPostJson(Context context, JSONObject jsonObject, JSONArray jsonArray, int code) {
        Config.hideDialog();
        try {
            if (jsonObject.getString("result").equals("success")){
                startActivity(new Intent(RecorvedNewPassword.this, MainActivity.class));
                finish();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void geterrorVolley(Context context, String error) {
        Config.hideDialog();
    }
}
