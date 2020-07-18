package com.example.myapplication.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Interface.networksJO;
import com.example.myapplication.Models.Config;
import com.example.myapplication.R;
import com.example.myapplication.Service.Networks;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class RecorvedTreeActivity extends AppCompatActivity implements View.OnClickListener , networksJO {

    private static final long START_TIME_IN_MILLIS = 60000 *3;

    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private TextView mTextViewCountDown;

    private TextView mTextViewReceivesms;
    private TextView mTextViewerrormsg;

    private EditText Edt1, Edt2, Edt3, Edt4;
    private Button confirmBtn, chatBtn;

    String phone;
    boolean error;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery3);

        init();
    }

    private void init() {
        phone = getIntent().getStringExtra("phone");
        error = getIntent().getBooleanExtra("error",false);
        mTextViewReceivesms = findViewById(R.id.receivesms);
        mTextViewCountDown = (TextView)findViewById(R.id.countdown);
        mTextViewReceivesms.setText(getString(R.string.recorver_two,phone.substring(phone.length()-2)));
        mTextViewerrormsg = findViewById(R.id.msg);
        if (error){
            mTextViewerrormsg.setText(getString(R.string.invalid_code_label));
            mTextViewerrormsg.setTextColor(Color.RED);
        }else {
            mTextViewerrormsg.setText(getString(R.string.code_sent));
            mTextViewerrormsg.setTextColor(Color.GREEN);
        }
        Edt1 = findViewById(R.id.edt1);
        Edt2 = findViewById(R.id.edt2);
        Edt3 = findViewById(R.id.edt3);
        Edt4 = findViewById(R.id.edt4);

        confirmBtn = (Button)findViewById(R.id.confirm);
        chatBtn = (Button)findViewById(R.id.chat_btn);
        confirmBtn.setOnClickListener(this);
        chatBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.confirm){

        }else if (view.getId() == R.id.chat_btn){
            Intent intent = new Intent(RecorvedTreeActivity.this, RecorvedChatActivity.class);
            intent.putExtra("phone",phone);
            startActivity(intent);
        }
    }

    private void sendCode(String phone, String pincode){
        Config.ProgressDialog(RecorvedTreeActivity.this);
        Config.showDialog("");
        List<String> paths = new ArrayList<>();
        paths.add("phone_validation");
        paths.add("verifycode");
        Map<String, String> phoneParam = new HashMap<>();
        phoneParam.put("phone",phone);
        phoneParam.put("pincode",pincode);
        Networks networks = new Networks(RecorvedTreeActivity.this,this);
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
                mTextViewCountDown.setText("--:--");
                mTextViewerrormsg.setText(getString(R.string.invalid_code_label));
                mTextViewerrormsg.setTextColor(Color.RED);
            }
        }.start();

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
