package com.example.myapplication.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Interface.networksJO;
import com.example.myapplication.Models.Config;
import com.example.myapplication.Models.Country;
import com.example.myapplication.R;
import com.example.myapplication.Service.Networks;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYouListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener, networksJO {

    private EditText countryEdt;
    private EditText numberEdt;
    private EditText passwordEdt;
    private TextView forgotTxt;
    private Button gotIt;
    private ImageView imageView;
    Drawable drawable;

    private boolean flag;
    Networks networks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        init();
        getCountryFlag();
    }

    private void getCountryFlag() {
        flag = true;
        Map<String, Map<String, String>> whereParam = new HashMap<>();
        Map<String, String> flagParam = new HashMap<>();
        flagParam.put("country_code", Config.cameroonFlag);
        whereParam.put("where",flagParam);

        JSONObject flagObject = new JSONObject(whereParam);
        String filterJson = flagObject.toString();

        Map<String, String> filter = new HashMap<>();
        filter.put("filter",filterJson);
        networks.getvolley(networks.EncodeUrl("countries",filter));

    }

    private void init() {
        countryEdt = findViewById(R.id.country);
        numberEdt = findViewById(R.id.phone);
        passwordEdt = findViewById(R.id.password);
        imageView = findViewById(R.id.iv);
        gotIt = findViewById(R.id.email_sign_in_button); gotIt.setOnClickListener(this);
        forgotTxt = findViewById(R.id.forgot); forgotTxt.setOnClickListener(this);
        numberEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() > 8){
                    numberEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_green, 0);
                }else {
                    numberEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_grey, 0);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        passwordEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() > 7){
                    passwordEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_green, 0);
                }else {
                    passwordEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_grey, 0);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        networks = new Networks(SignInActivity.this,this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.forgot){
           // startActivity(new Intent(SignInActivity.this, RecorvedActivity.class));
            Toast.makeText(view.getContext(),getString(R.string.forgot_label),Toast.LENGTH_SHORT).show();
        }else if (view.getId()==R.id.email_sign_in_button){
            startActivity(new Intent(SignInActivity.this, SignInAddPasswordActivity.class));
            finish();
        }
    }

    @Override
    public void getVolleyJson(Context context, JSONObject jsonObject, JSONArray jsonArray) {
        if (flag){
            try {
                JSONObject FlagJson = jsonArray.getJSONObject(0);
                Country country = new Country(FlagJson.getString("flag"),FlagJson.getString("country_code"),
                        FlagJson.getString("alpha3code"));
                setCountryFlag(country.getFlag(),country.getCountry_code());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void setCountryFlag(String flagpath, String code) {
        GlideToVectorYou.init()
                .with(this)
                .withListener(new GlideToVectorYouListener() {
                    @Override
                    public void onLoadFailed() {

                    }

                    @Override
                    public void onResourceReady() {

                    }
                }).load(Uri.parse(flagpath),imageView);
        countryEdt.setText(code);
        countryEdt.setEnabled(false);
    }

    @Override
    public void getVolleyFromPostJson(Context context, JSONObject jsonObject, JSONArray jsonArray) {

    }

    @Override
    public void geterrorVolley(Context context, String error) {

    }
}
