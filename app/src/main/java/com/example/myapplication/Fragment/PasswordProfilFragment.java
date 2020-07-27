package com.example.myapplication.Fragment;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.Interface.networksJO;
import com.example.myapplication.Models.Config;
import com.example.myapplication.Models.Utils.SessionDriver;
import com.example.myapplication.R;
import com.example.myapplication.Service.Networks;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PasswordProfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PasswordProfilFragment extends Fragment implements networksJO {

    private EditText old_passwordEdt;
    private EditText new_passwordEdt;
    private EditText conf_passwordEdt;
    private TextView resetText;
    private ImageView iv_upload;
    private Button updateBtn, cancelBtn;
    ProgressBar progressBar;
    SessionDriver sessionDriver;

    int screensize;

    public PasswordProfilFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PasswordProfilFragment newInstance() {
        PasswordProfilFragment fragment = new PasswordProfilFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionDriver = new SessionDriver(getActivity());
        screensize = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.password_profil_dialog, container, false);

        init(view);
        return view;
    }

    private void init(View view) {
        old_passwordEdt = (EditText)view.findViewById(R.id.oldpassword);
        new_passwordEdt = (EditText)view.findViewById(R.id.newpassword);
        conf_passwordEdt = (EditText)view.findViewById(R.id.confpassword);
        resetText = (TextView)view.findViewById(R.id.resetpwd);
        progressBar = (ProgressBar)view.findViewById(R.id.progressbar);
        iv_upload = (ImageView)view.findViewById(R.id.upload);
        updateBtn = (Button)view.findViewById(R.id.update) ;
        cancelBtn = (Button)view.findViewById(R.id.cancel);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!old_passwordEdt.getText().toString().isEmpty()&&!new_passwordEdt.getText().toString().isEmpty()
                &&!conf_passwordEdt.getText().toString().isEmpty()){
                    if (new_passwordEdt.getText().toString().equals(conf_passwordEdt.getText().toString())){
                        resetText.setVisibility(View.GONE);
                       // Config.showProgressDialog(progressBar);
                        resetPassword(old_passwordEdt.getText().toString(),new_passwordEdt.getText().toString());
                    }else {
                        resetText.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Config.hideProgressDialog(progressBar);
            }
        });
        iv_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchFragment(UploadProfilFragment.newInstance());
            }
        });

        setListener();
    }

    private void SwitchFragment(Fragment fragment){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void setListener() {
        old_passwordEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() > 4){
                    if (screensize == Configuration.SCREENLAYOUT_SIZE_LARGE)
                        old_passwordEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_green_2, 0);
                    else
                        old_passwordEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_green, 0);
                }else if ((charSequence.toString().length() > 0)&& (charSequence.toString().length() < 5))
                {
                    if (screensize == Configuration.SCREENLAYOUT_SIZE_LARGE)
                        old_passwordEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_red_2, 0);
                    else
                        old_passwordEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_red, 0);
                }else {
                    if (screensize == Configuration.SCREENLAYOUT_SIZE_LARGE)
                        old_passwordEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_grey_2, 0);
                    else
                        old_passwordEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_grey, 0);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        new_passwordEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() > 4){
                    if (screensize == Configuration.SCREENLAYOUT_SIZE_LARGE)
                        new_passwordEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_green_2, 0);
                    else
                        new_passwordEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_green, 0);
                }else if ((charSequence.toString().length() > 0)&& (charSequence.toString().length() < 5))
                {
                    if (screensize == Configuration.SCREENLAYOUT_SIZE_LARGE)
                        new_passwordEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_red_2, 0);
                    else
                        new_passwordEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_red, 0);
                }else {
                    if (screensize == Configuration.SCREENLAYOUT_SIZE_LARGE)
                        new_passwordEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_grey_2, 0);
                    else
                        new_passwordEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_grey, 0);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        conf_passwordEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() > 4){
                    if (screensize == Configuration.SCREENLAYOUT_SIZE_LARGE)
                        conf_passwordEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_green_2, 0);
                    else
                        conf_passwordEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_green, 0);
                }else if ((charSequence.toString().length() > 0)&& (charSequence.toString().length() < 5))
                {
                    if (screensize == Configuration.SCREENLAYOUT_SIZE_LARGE)
                        conf_passwordEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_red_2, 0);
                    else
                        conf_passwordEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_red, 0);
                }else {
                    if (screensize == Configuration.SCREENLAYOUT_SIZE_LARGE)
                        conf_passwordEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_grey_2, 0);
                    else
                        conf_passwordEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_grey, 0);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void resetPassword(String oldpassword, String newpassword){
        Config.showProgressDialog(progressBar);
        Map<String,String> params = new HashMap<>();
        params.put("oldPassword",oldpassword);
        params.put("newPassword",newpassword);

        String token = sessionDriver.getDriver().getId();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization",token);

        JSONObject dataJson = new JSONObject(params);
        Networks networks = new Networks(getActivity(),this);
        networks.postData(dataJson.toString(),Config.resetPassword,headers);
    }

    @Override
    public void getVolleyJson(Context context, JSONObject jsonObject, JSONArray jsonArray, int code) {

    }

    @Override
    public void getVolleyFromPostJson(Context context, JSONObject jsonObject, JSONArray jsonArray, int code) {
            Config.hideProgressDialog(progressBar);
            resetText.setVisibility(View.VISIBLE);
            resetText.setText(getString(R.string.update_done));
            resetText.setTextColor(getResources().getColor(R.color.commentsent));
    }

    @Override
    public void geterrorVolley(Context context, String error) {
        if (error == null){
            Config.hideProgressDialog(progressBar);
        }
    }
}