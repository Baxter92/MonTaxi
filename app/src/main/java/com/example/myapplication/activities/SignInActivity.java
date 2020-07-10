package com.example.myapplication.activities;

import android.app.ProgressDialog;
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
import com.example.myapplication.MainActivity;
import com.example.myapplication.Models.Config;
import com.example.myapplication.Models.Country;
import com.example.myapplication.Models.Driver;
import com.example.myapplication.Models.SignIn;
import com.example.myapplication.Models.Utils.SessionDriver;
import com.example.myapplication.R;
import com.example.myapplication.Service.Networks;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYouListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener, networksJO {

    private EditText countryEdt;
    private EditText numberEdt;
    private EditText passwordEdt;
    private TextView forgotTxt;
    private Button gotIt;
    private ImageView imageView;
    SessionDriver sessionDriver;
    Drawable drawable;
    ProgressDialog progressDialog;

    private boolean flag;
    private boolean countryExist;
    Networks networks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        init();
        getCountryFlag();
    }

    private void getCountryFlag() {
        Map<String, Map<String, String>> whereParam = new HashMap<>();
        Map<String, String> flagParam = new HashMap<>();
        flagParam.put("country_code", Config.cameroonFlag);
        whereParam.put("where",flagParam);

        JSONObject flagObject = new JSONObject(whereParam);
        String filterJson = flagObject.toString();

        List<String> countrieList = new ArrayList<>();
        countrieList.add("countries");
        Map<String, String> filter = new HashMap<>();
        filter.put("filter",filterJson);
        networks.getvolley(networks.EncodeUrl(countrieList,filter));

    }

    private void init() {
        sessionDriver = new SessionDriver(this);
        progressDialog = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(true);
        
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
                if (charSequence.toString().length() > 3){
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
            if (!numberEdt.getText().toString().isEmpty() &&
                    !passwordEdt.getText().toString().isEmpty()) {
                SignIn(numberEdt.getText().toString(), passwordEdt.getText().toString());
            }else {
                Toast.makeText(view.getContext(),getString(R.string.sign_required),Toast.LENGTH_LONG).show();
            }
        }
    }

    private void checkNumber(String phoneNumber) {
        countryExist = true;
        List<String> paths = new ArrayList<>();
        paths.add("drivers");
        paths.add("verifyphone");

        Map<String,String> phoneParams = new HashMap<>();
        phoneParams.put("phone",phoneNumber);

        networks.getvolley(networks.EncodeUrl(paths,phoneParams));
    }

    @Override
    public void getVolleyJson(Context context, JSONObject jsonObject, JSONArray jsonArray) {

            try {
                flag = false;
                JSONObject FlagJson = jsonArray.getJSONObject(0);
                Country country = new Country(FlagJson.getString("flag"),FlagJson.getString("country_code"),
                        FlagJson.getString("alpha3code"));
                setCountryFlag(country.getFlag(),country.getCountry_code());

            } catch (JSONException e) {
                e.printStackTrace();
            }

    }

    private void SignIn(String number, String password) {
        showDialog("login");
        String numberCC = Config.cameroonFlag.substring(1)+number;
        SignIn signIn = new SignIn(numberCC,password);
        Log.e("jsondata",signIn.JsonUser());
        networks.postData(signIn.JsonUser(),Config.signIn);
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
        hideDialog();
        try {
        if (jsonObject.has("error")){
            JSONObject errorJson = jsonObject.getJSONObject("error");
            Toast.makeText(context,errorJson.getString("message"),Toast.LENGTH_LONG).show();
        }else {
            Driver driver = new Driver(jsonObject.getString("id"), jsonObject.getLong("ttl"), jsonObject.getString("created"),
                    jsonObject.getInt("userId"), jsonObject.getBoolean("signedup"));

            if (driver.isSignedup()) {
                //Store Driver In SharedPreference
                sessionDriver.setDriver(driver);
                startActivity(new Intent(SignInActivity.this, MainActivity.class));
                finish();
            } else {
                startActivity(new Intent(SignInActivity.this, SignInAddPasswordActivity.class));
            }
        }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void geterrorVolley(Context context, String error) {

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
