package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.myapplication.MainActivity;
import com.example.myapplication.Models.Utils.SessionDriver;
import com.example.myapplication.R;

public class SplashActivity extends AppCompatActivity {

    SessionDriver sessionDriver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sessionDriver = new SessionDriver(this);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run()
            {
                //start  activity after 3 sec
                if (sessionDriver.getDriver()!=null){
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
                startActivity(new Intent(SplashActivity.this, SteppersActivity.class));
                finish();
            }
        }, 3000);

    }
}