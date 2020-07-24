package com.example.myapplication.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Interface.networksJO;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Models.Config;
import com.example.myapplication.R;
import com.example.myapplication.Service.Networks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class RecorvedTwoActivity extends AppCompatActivity implements View.OnClickListener, networksJO {
    private static final long START_TIME_IN_MILLIS = 60000;

    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private TextView mTextViewCountDown;
    private TextView mTextViewResendCode;
    private TextView mTextViewReceivesms;
    private String phone, pincode;
    private EditText Edt1, Edt2, Edt3, Edt4;
    private boolean sent = false;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery2);
        init();
        setCountDown();
    }

    private void init() {
        phone = getIntent().getStringExtra("phone");

        Edt1 = (EditText)findViewById(R.id.edt1);
        Edt2 = (EditText)findViewById(R.id.edt2);
        Edt3 = (EditText)findViewById(R.id.edt3);
        Edt4 = (EditText)findViewById(R.id.edt4);
        mTextViewCountDown = (TextView)findViewById(R.id.countdown);
        mTextViewReceivesms = (TextView)findViewById(R.id.receivesms);
        mTextViewResendCode = (TextView)findViewById(R.id.resend_code);mTextViewResendCode.setOnClickListener(this);
        //mTextViewReceivesms.setText(getString(R.string.recorver_two,phone.substring(phone.length()-2)));
       mTextViewReceivesms.setText(getString(R.string.recorver_two,"43"));
        ((Button)findViewById(R.id.email_sign_in_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(RecorvedTwoActivity.this, RecorvedTreeActivity.class));
                if (!Edt1.getText().toString().isEmpty()&&!Edt2.getText().toString().isEmpty()&&!Edt3.getText().toString().isEmpty()
                &&!Edt4.getText().toString().isEmpty()) {
                    pincode = Edt1.getText().toString()+Edt2.getText().toString()+Edt3.getText().toString()+Edt4.getText().toString();
                    //sendCode(phone,pincode);
                    switch2Page();
                }
            }
        });
        intent = new Intent(RecorvedTwoActivity.this, RecorvedTreeActivity.class);
        intent.putExtra("phone",phone);
        setEditOneTimes();
    }

    private void switch2Page(){
        sent = true;
        Intent intent = new Intent(RecorvedTwoActivity.this, RecorvedNewPassword.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("phone",phone);
        intent.putExtra("code",pincode);
        startActivity(intent);
        finish();
    }
    private void sendCode(String phone, String pincode){
        Config.ProgressDialog(RecorvedTwoActivity.this);
        Config.showDialog("");
        List<String> paths = new ArrayList<>();
        paths.add("phone_validation");
        paths.add("verifycode");
        Map<String, String> phoneParam = new HashMap<>();
        phoneParam.put("phone",phone);
        phoneParam.put("pincode",pincode);
        Networks networks = new Networks(RecorvedTwoActivity.this,this);
        networks.getvolley(networks.EncodeUrl(paths,phoneParam));
    }
    private void setCountDown(){
        new CountDownTimer(mTimeLeftInMillis, 1000) {

            public void onTick(long millisUntilFinished) {
                //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }
            private void updateCountDownText() {
                int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
                int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
                String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
                mTextViewCountDown.setText(timeLeftFormatted);
            }
            public void onFinish() {
                if (!sent) {
                    mTextViewCountDown.setText("--:--");
                    intent.putExtra("error", false);
                    startActivity(intent);
                    finish();
                }
            }
        }.start();

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void getVolleyJson(Context context, JSONObject jsonObject, JSONArray jsonArray, int code) {
        Config.hideDialog();
        try {
            if (jsonObject.getString("result").equals("success")){
                Intent intent = new Intent(RecorvedTwoActivity.this, RecorvedNewPassword.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("phone",phone);
                intent.putExtra("code",pincode);
                startActivity(intent);
                finish();
            }else {
                intent.putExtra("error",true);
                intent.putExtra("phone",phone);
                startActivity(intent);
                finish();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getVolleyFromPostJson(Context context, JSONObject jsonObject, JSONArray jsonArray, int code) {

    }

    @Override
    public void geterrorVolley(Context context, String error) {
        if (error == null) {
            Config.hideDialog();
            intent.putExtra("error", true);
            intent.putExtra("phone", phone);
            startActivity(intent);
            finish();
        }
    }

    private void setEditOneTimes(){
        final StringBuilder sb=new StringBuilder();

        Edt1.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if(sb.length()==0&Edt1.length()==1)
                {
                    sb.append(s);
                    Edt1.clearFocus();
                    Edt2.requestFocus();
                    Edt2.setCursorVisible(true);

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                if(sb.length()==1)
                {
                    sb.deleteCharAt(0);
                }
            }

            public void afterTextChanged(Editable s) {
                if(sb.length()==0)
                {
                    Edt1.requestFocus();
                }
            }
        });

        Edt2.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if(sb.length()==0&Edt2.length()==1)
                {
                    sb.append(s);
                    Edt2.clearFocus();
                    Edt3.requestFocus();
                    Edt3.setCursorVisible(true);

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                if(sb.length()==1)
                {
                    sb.deleteCharAt(0);
                }
            }

            public void afterTextChanged(Editable s) {
                if(sb.length()==0)
                {
                    Edt2.requestFocus();
                }
            }
        });

        Edt3.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if(sb.length()==0&Edt3.length()==1)
                {
                    sb.append(s);
                    Edt3.clearFocus();
                    Edt4.requestFocus();
                    Edt4.setCursorVisible(true);

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                if(sb.length()==1)
                {
                    sb.deleteCharAt(0);
                }
            }

            public void afterTextChanged(Editable s) {
                if(sb.length()==0)
                {
                    Edt3.requestFocus();
                }
            }
        });

        Edt4.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if(sb.length()==0&Edt4.length()==1)
                {
                    sb.append(s);
                    Edt4.clearFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                if(sb.length()==1)
                {
                    sb.deleteCharAt(0);
                }
            }

            public void afterTextChanged(Editable s) {
                if(sb.length()==0)
                {
                    switch2Page();
                }
            }
        });
    }
}
