package com.example.myapplication.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.myapplication.Interface.networksJO;
import com.example.myapplication.Models.ChatComment;
import com.example.myapplication.Models.Config;
import com.example.myapplication.R;
import com.example.myapplication.Service.Networks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RecorvedChatActivity extends AppCompatActivity implements networksJO {

    private EditText phoneEdt, commentEdt;
    private Button validBtn;
    ProgressBar progressBar;

    private String phone;
    int screensize;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recoverychat);

        phone = getIntent().getStringExtra("phone");
        phone = phone !=null?phone:"695797443";

        init();
        setListenner();
    }

    private void init() {
        phoneEdt = (EditText)findViewById(R.id.phone);
        commentEdt = (EditText)findViewById(R.id.comment);
        progressBar = (ProgressBar)findViewById(R.id.progressbar);
        screensize = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        ((Button)findViewById(R.id.send)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Alert(getString(R.string.comment_sent),false);
                //phone = "237695797443";
                if (phone.substring(3).equals(phoneEdt.getText().toString()))
                    sendChatComment(phoneEdt.getText().toString(),commentEdt.getText().toString());
                else {
                    Config.showProgressDialog(progressBar);
                    Alert(getString(R.string.comment_notsent), true);
                }
            }
        });
    }

    private void Alert(String message, boolean error) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        View contentView = getLayoutInflater().inflate(R.layout.comment_dialog,null);
        dialog.setContentView(contentView);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width =310;
        lp.height = 310;
        dialog.setCancelable(true);
        Button exitText = (Button) contentView.findViewById(R.id.exit);
        TextView comment = (TextView)contentView.findViewById(R.id.comment);
        TextView cancel = (TextView)contentView.findViewById(R.id.cancel);
        ImageView ivEmoji = (ImageView)contentView.findViewById(R.id.emoji);
        if (error) {
            comment.setTextColor(getResources().getColor(R.color.commentnotsent));
            comment.setText(getString(R.string.comment_notsent));
            Glide.with(RecorvedChatActivity.this).load(R.drawable.sad_emoji).into(ivEmoji);
            cancel.setVisibility(View.VISIBLE);
            exitText.setText(getString(R.string.try_again));
            exitText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Config.hideProgressDialog(progressBar);
                    dialog.dismiss();
                }
            });
        }
        else {
            comment.setTextColor(getResources().getColor(R.color.commentsent));
            comment.setText(getString(R.string.comment_sent));
            Glide.with(RecorvedChatActivity.this).load(R.drawable.laugh_icon).into(ivEmoji);
            cancel.setVisibility(View.GONE);
            exitText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(RecorvedChatActivity.this, SignInActivity.class));
                    finish();
                }
            });
        }

        comment.setText(message);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RecorvedChatActivity.this, SignInActivity.class));
                finish();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(RecorvedChatActivity.this, RecorvedTreeActivity.class);
        i.putExtra("phone",phone);
        startActivity(i);
    }

    private void sendChatComment(String phone, String comment){
        showProgressDialog();
        Map<String, String> params = new HashMap<>();
        params.put("phone",phone);
        params.put("message",comment);

        JSONObject jsonParams = new JSONObject(params);

        Networks networks = new Networks(RecorvedChatActivity.this, this);
        networks.postData(jsonParams.toString(),Config.chatcomment,new HashMap<String, String>());
    }

    @Override
    public void getVolleyJson(Context context, JSONObject jsonObject, JSONArray jsonArray, int code) {

    }

    @Override
    public void getVolleyFromPostJson(Context context, JSONObject jsonObject, JSONArray jsonArray, int code) {
       hideProgressDialog();
        if (jsonObject.has("id")){
            try {
                ChatComment chatComment = new ChatComment(jsonObject.getInt("id"),jsonObject.getString("phone"),
                        jsonObject.getString("message"),jsonObject.getBoolean("read"),jsonObject.getBoolean("favoris"),
                        jsonObject.getString("received_at"));

                Alert(getString(R.string.comment_sent),false);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            Alert(getString(R.string.comment_notsent),true);
        }
    }

    @Override
    public void geterrorVolley(Context context, String error) {
            if (error == null){
                hideProgressDialog();
                Alert(getString(R.string.comment_notsent),true);
            }else {
                hideProgressDialog();
                Alert(getString(R.string.comment_notsent),true);
            }
    }

    private void setListenner() {
        phoneEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() == 9){
                    if (screensize == Configuration.SCREENLAYOUT_SIZE_LARGE)
                        phoneEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_green_2, 0);
                    else
                        phoneEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_green, 0);

                }else if (charSequence.toString().length() != 9 && charSequence.toString().length() != 0) {
                    if (screensize == Configuration.SCREENLAYOUT_SIZE_LARGE)
                        phoneEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_red_2, 0);
                    else
                        phoneEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_red, 0);
                }else {
                    if (screensize == Configuration.SCREENLAYOUT_SIZE_LARGE)
                        phoneEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_grey_2, 0);
                    else
                        phoneEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_grey, 0);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void showProgressDialog(){
        progressBar.setVisibility(View.VISIBLE);
        validBtn.setVisibility(View.GONE);
    }
    private void hideProgressDialog(){
        progressBar.setVisibility(View.GONE);
        validBtn.setVisibility(View.VISIBLE);
    }
}
