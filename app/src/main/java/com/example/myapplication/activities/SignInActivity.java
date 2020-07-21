package com.example.myapplication.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
    RelativeLayout ll_error;
    private TextView errortvt;
    private ImageView imageView;
    SessionDriver sessionDriver;
    Drawable drawable;
    ProgressDialog progressDialog;
    ProgressBar progressBar;
    Country country;

    private boolean flag;
    private boolean countryExist;
    Networks networks;
    int screensize;

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

        screensize = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        
        countryEdt = findViewById(R.id.country);
        numberEdt = findViewById(R.id.phone);
        passwordEdt = findViewById(R.id.password);
        imageView = findViewById(R.id.iv);
        ll_error = findViewById(R.id.flashmessage);
        errortvt = findViewById(R.id.flashmessage_txt);
        progressBar = findViewById(R.id.progressbar);
        gotIt = findViewById(R.id.email_sign_in_button); gotIt.setOnClickListener(this);
        forgotTxt = findViewById(R.id.forgot); forgotTxt.setOnClickListener(this);
        numberEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() == 9){
                    if (screensize == Configuration.SCREENLAYOUT_SIZE_LARGE)
                        numberEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_green_2, 0);
                    else
                        numberEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_green, 0);

                }else if (charSequence.toString().length() != 9 && charSequence.toString().length() != 0) {
                    if (screensize == Configuration.SCREENLAYOUT_SIZE_LARGE)
                        numberEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_red_2, 0);
                    else
                        numberEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_red, 0);
                }else {
                    if (screensize == Configuration.SCREENLAYOUT_SIZE_LARGE)
                        numberEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_grey_2, 0);
                    else
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
                if (charSequence.toString().length() > 4){
                    if (screensize == Configuration.SCREENLAYOUT_SIZE_LARGE)
                        passwordEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_green_2, 0);
                    else
                        passwordEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_green, 0);
                }else if ((charSequence.toString().length() > 0)&& (charSequence.toString().length() < 5))
                {
                    if (screensize == Configuration.SCREENLAYOUT_SIZE_LARGE)
                         passwordEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_red_2, 0);
                    else
                        passwordEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_red, 0);
                }else {
                    if (screensize == Configuration.SCREENLAYOUT_SIZE_LARGE)
                        passwordEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_grey_2, 0);
                    else
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
            Intent intent = new Intent(SignInActivity.this, RecorvedActivity.class);
            startActivity(intent);
           // Toast.makeText(view.getContext(),getString(R.string.forgot_label),Toast.LENGTH_SHORT).show();
        }else if (view.getId()==R.id.email_sign_in_button){
            if (!numberEdt.getText().toString().isEmpty() &&
                    !passwordEdt.getText().toString().isEmpty()) {
                SignIn(numberEdt.getText().toString(), passwordEdt.getText().toString());
            }/*else {
               // Toast.makeText(view.getContext(),getString(R.string.sign_required),Toast.LENGTH_LONG).show();
                Config.Alert(SignInActivity.this,getString(R.string.sign_required),false);
            }*/
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
    public void getVolleyJson(Context context, JSONObject jsonObject, JSONArray jsonArray, int statusCode) {
        Log.e("jsondata","status code "+statusCode);
            try {
                flag = false;
                JSONObject FlagJson = jsonArray.getJSONObject(0);
                country = new Country(FlagJson.getString("flag"),FlagJson.getString("country_code"),
                        FlagJson.getString("alpha3code"), FlagJson.getJSONObject("name").getString("en"),
                        FlagJson.getJSONObject("name").getString("fr"));
                setCountryFlag(country.getFlag(),country.getCountry_code());

            } catch (JSONException e) {
                e.printStackTrace();
            }

    }

    private void SignIn(String number, String password) {
        showProgressDialog();
        //showDialog("login");
       // String numberCC = Config.cameroonFlag.substring(1)+number;
        String numberCC = country.getCountry_code()+number;
        SignIn signIn = new SignIn(numberCC,password);
        Log.e("jsondata",signIn.JsonUser());
        networks.postData(signIn.JsonUser(),Config.signIn,new HashMap<String, String>());
       // Config.Alert(SignInActivity.this,"success text screen",true);
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
    public void getVolleyFromPostJson(Context context, JSONObject jsonObject, JSONArray jsonArray, int statusCode) {
        //hideDialog();
        hideProgressDialog();
        Log.e("jsondata","status code "+statusCode);
        try {
            if (jsonObject.has("error")){
                JSONObject errorJson = jsonObject.getJSONObject("error");
                //Toast.makeText(context,errorJson.getString("message"),Toast.LENGTH_LONG).show();
                Config.Alert(SignInActivity.this,errorJson.getString("message"),false);
            }else {
                Driver driver = new Driver(jsonObject.getString("id"), jsonObject.getLong("ttl"), jsonObject.getString("created"),
                        jsonObject.getInt("userId"), jsonObject.getBoolean("signedup"),country);

                if (driver.isSignedup()) {
                    //Store Driver In SharedPreference
                    sessionDriver.setDriver(driver);
                    startActivity(new Intent(SignInActivity.this, MainActivity.class));
                    finish();
                } else {
                    Intent i = new Intent(SignInActivity.this, SignInAddPasswordActivity.class);
                    i.putExtra("token",driver.getId());
                    i.putExtra("password",passwordEdt.getText().toString());
                    startActivity(i);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void geterrorVolley(Context context, String error) {
       // hideDialog();
            hideProgressDialog();
            if (error == null){
               // Config.Alert(SignInActivity.this,getString(R.string.un_authorize),false);
                showErrorMessage(getString(R.string.un_authorize));
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
    private void showProgressDialog(){
        progressBar.setVisibility(View.VISIBLE);
        gotIt.setVisibility(View.GONE);
    }
    private void hideProgressDialog(){
        progressBar.setVisibility(View.GONE);
        gotIt.setVisibility(View.VISIBLE);
    }
    private void showErrorMessage(String texterror){
        forgotTxt.setVisibility(View.GONE);
        ll_error.setVisibility(View.VISIBLE);
        errortvt.setText(texterror);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run()
            {
                ll_error.setVisibility(View.GONE);
                forgotTxt.setVisibility(View.VISIBLE);
            }
        }, 3000);
    }

}
