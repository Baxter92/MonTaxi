package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText countryEdt;
    private EditText numberEdt;
    private EditText passwordEdt;
    private TextView forgotTxt;
    private Button gotIt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        init();
    }

    private void init() {
        countryEdt = findViewById(R.id.country);
        numberEdt = findViewById(R.id.phone);
        passwordEdt = findViewById(R.id.password);
        gotIt = findViewById(R.id.email_sign_in_button);
        forgotTxt = findViewById(R.id.forgot); forgotTxt.setOnClickListener(this);
        numberEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (i2 >= 6){
                    numberEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_green, 0);
                }else {
                    numberEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_grey, 0);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.forgot){
            startActivity(new Intent(SignInActivity.this, RecorvedActivity.class));
        }else if (view.getId()==R.id.email_sign_in_button){
            startActivity(new Intent(SignInActivity.this, MainActivity.class));
            finish();
        }
    }
}
