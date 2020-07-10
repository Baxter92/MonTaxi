package com.example.myapplication.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Interface.networksJO;
import com.example.myapplication.R;
import com.example.myapplication.Service.Networks;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignInAddPasswordActivity extends AppCompatActivity implements networksJO {

    private EditText passwdEdt;
    private EditText confpasswdEdt;
    private Button validBtn;
    String number;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_pwd);
        number = getIntent().getStringExtra("number");
        init();
    }

    private void init() {
        passwdEdt = findViewById(R.id.pwd);
        confpasswdEdt = findViewById(R.id.pwd2);
        validBtn = findViewById(R.id.email_sign_in_button);
        validBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!passwdEdt.getText().toString().isEmpty()&& ! confpasswdEdt.getText().toString().isEmpty()){
                    if (validPassword(passwdEdt.getText().toString(), confpasswdEdt.getText().toString())){
                        SignUp(number,passwdEdt.getText().toString());
                    }else {
                        Toast.makeText(view.getContext(), getString(R.string.password_similar),Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(view.getContext(), getString(R.string.sign_required),Toast.LENGTH_LONG).show();
                }
            }
        });
        passwdEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() > 8){
                    passwdEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_green, 0);
                }else {
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
                if (charSequence.toString().length() > 8){
                    confpasswdEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_green, 0);
                }else {
                    confpasswdEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_grey, 0);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void SignUp(String number, String password) {
        Map<String, String> params = new HashMap<>();
        params.put("number",number);
        params.put("password",password);

        Networks networks = new Networks(SignInAddPasswordActivity.this,this);
        JSONObject jsondata = new JSONObject(params);
        networks.postData(jsondata.toString(),"");

    }

    private boolean validPassword(String password, String password2){
        return  password == password2;
    }

    @Override
    public void getVolleyJson(Context context, JSONObject jsonObject, JSONArray jsonArray) {

    }

    @Override
    public void getVolleyFromPostJson(Context context, JSONObject jsonObject, JSONArray jsonArray) {

    }

    @Override
    public void geterrorVolley(Context context, String error) {

    }
}
