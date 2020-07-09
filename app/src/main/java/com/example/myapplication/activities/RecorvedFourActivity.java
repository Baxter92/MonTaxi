package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class RecorvedFourActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery4);

        ((Button)findViewById(R.id.email_sign_in_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Alert();
            }
        });
    }

    private void Alert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View contentView = getLayoutInflater().inflate(R.layout.comment_dialog,null);
        TextView exitText = (TextView)contentView.findViewById(R.id.exit);
        exitText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RecorvedFourActivity.this, SignInActivity.class));
                finish();
            }
        });

        builder.setView(contentView)
                .show();
    }
}
