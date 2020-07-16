package com.example.myapplication.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Interface.networksJO;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Models.Config;
import com.example.myapplication.R;
import com.example.myapplication.Service.Networks;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class RecorvedTwoActivity extends AppCompatActivity implements View.OnClickListener, networksJO {
    private static final long START_TIME_IN_MILLIS = 60000 *3;

    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private TextView mTextViewCountDown;
    private TextView mTextViewResendCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery2);
        init();
        setCountDown();
    }

    private void init() {
        mTextViewCountDown = (TextView)findViewById(R.id.countdown);
        mTextViewResendCode = (TextView)findViewById(R.id.resend_code);mTextViewResendCode.setOnClickListener(this);
        ((Button)findViewById(R.id.email_sign_in_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RecorvedTwoActivity.this, RecorvedTreeActivity.class));
            }
        });
    }

    private void sendCode(){
        Config.ProgressDialog(RecorvedTwoActivity.this);
        Config.showDialog("");
        Map<String, String> params = new HashMap<>();
        params.put("phone","");
        params.put("password","");
        params.put("pincode","");
        JSONObject jsonObject = new JSONObject(params);
        Networks networks = new Networks(
                RecorvedTwoActivity.this, this);
        networks.postData(jsonObject.toString(),Config.recoveraccount,new HashMap<String, String>());
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
                mTextViewCountDown.setText("--:--");
            }
        }.start();

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void getVolleyJson(Context context, JSONObject jsonObject, JSONArray jsonArray, int code) {

    }

    @Override
    public void getVolleyFromPostJson(Context context, JSONObject jsonObject, JSONArray jsonArray, int code) {

    }

    @Override
    public void geterrorVolley(Context context, String error) {

    }
}
