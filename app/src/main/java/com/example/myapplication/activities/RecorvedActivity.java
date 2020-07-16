package com.example.myapplication.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class RecorvedActivity extends AppCompatActivity implements networksJO {

    private ImageView ivCountry;
    private String flagCountry;
    private EditText countryEdt;
    private EditText numberEdt;
    boolean flag = false;
    int screensize;
    String lang;
    Networks networks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery);
        init();
        getCountryFlag();
    }

    private void init() {
        ivCountry = (ImageView)findViewById(R.id.iv);
        countryEdt = (EditText)findViewById(R.id.country);
        numberEdt = (EditText)findViewById(R.id.phone);
        networks = new Networks(RecorvedActivity.this,this);
        screensize = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        lang = Locale.getDefault().getLanguage();
        Config.ProgressDialog(this);
        setListenner();
        ((Button)findViewById(R.id.email_sign_in_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //verifyPhone();
                startActivity(new Intent(RecorvedActivity.this, RecorvedTwoActivity.class));
            }
        });
    }

    private void setListenner() {
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
    }

    @Override
    public void getVolleyJson(Context context, JSONObject jsonObject, JSONArray jsonArray, int code) {
        Config.hideDialog();
        if (flag) {
            try {
                JSONObject FlagJson = jsonArray.getJSONObject(0);
                Country country = new Country(FlagJson.getString("flag"), FlagJson.getString("country_code"),
                        FlagJson.getString("alpha3code"), FlagJson.getJSONObject("name").getString("en"),
                        FlagJson.getJSONObject("name").getString("fr"));
                if (lang.equals("en"))
                    setCountryFlag(country.getFlag(), country.getLabel());
                else
                    setCountryFlag(country.getFlag(), country.getLabel_fr());

                flag = false;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            try {
                boolean exist = jsonObject.getBoolean("exist");
                if (exist){
                    Intent intent = new Intent(RecorvedActivity.this, RecorvedTwoActivity.class);
                    intent.putExtra("phone",numberEdt.getText().toString());
                    startActivity(intent);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void getVolleyFromPostJson(Context context, JSONObject jsonObject, JSONArray jsonArray, int code) {

    }

    @Override
    public void geterrorVolley(Context context, String error) {

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
                }).load(Uri.parse(flagpath),ivCountry);
        countryEdt.setText(code);
        countryEdt.setEnabled(false);
    }

    private void getCountryFlag() {
        flag = true;
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

    private void verifyPhone() {
        Config.showDialog(getString(R.string.verify));
        List<String> paths = new ArrayList<>();
        paths.add("drivers");
        paths.add("verifyphone");
        Map<String, String> phoneParam = new HashMap<>();
        phoneParam.put("phone",numberEdt.getText().toString());
        networks.getvolley(networks.EncodeUrl(paths,phoneParam));

    }
}
