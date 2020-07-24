package com.example.myapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.Interface.DrawerItemClick;
import com.example.myapplication.Models.Config;
import com.example.myapplication.Models.Utils.DrawerModel;
import com.example.myapplication.Models.Utils.SessionDriver;
import com.example.myapplication.activities.RecorvedChatActivity;
import com.example.myapplication.activities.SignInActivity;
import com.example.myapplication.adapter.DrawerItemCustomAdapter;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DrawerItemClick {

    private EditText countryEdt;
    private EditText numberEdt;
    private EditText passwordEdt;
    private Button gotIt;
    private ImageView menuIcon;

    private ActionBar actionBar;
    private Toolbar toolbar;
    SessionDriver sessionDriver;

    private ListView mDrawerList;
    private List<DrawerModel> drawerModelList;
    private DrawerItemCustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // initToolbar();
       // initNavigationMenu();
        initMenu();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("");
    }

    private void initMenu(){
        sessionDriver = new SessionDriver(this);
        drawerModelList = new ArrayList<>();
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        menuIcon = (ImageView)findViewById(R.id.menu);

        adapter = new DrawerItemCustomAdapter(this,drawerModelList, this);
        mDrawerList.setAdapter(adapter);

           menuIcon();
        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerModelList.get(0).isOnlyIcon()) {
                    menu();
                }else {
                    menuIcon();
                }
            }
        });
    }

    private void menuIcon() {
        mDrawerList.getLayoutParams().width = 70;
        drawerModelList.clear();
        drawerModelList.add(new DrawerModel(true,R.drawable.ic_home_2,getString(R.string.nav_home)));
        drawerModelList.add(new DrawerModel(true,R.drawable.ic_taxi_2,getString(R.string.nav_taxi)));
        drawerModelList.add(new DrawerModel(true,R.drawable.ic_profile_2,getString(R.string.nav_profil)));
        drawerModelList.add(new DrawerModel(true,R.drawable.ic_request_2,getString(R.string.nav_request)));
        drawerModelList.add(new DrawerModel(true,R.drawable.ic_request_accepted_2,getString(R.string.nav_request_true)));
        drawerModelList.add(new DrawerModel(true,R.drawable.ic_wallet_2,getString(R.string.nav_wallet)));
        drawerModelList.add(new DrawerModel(true,R.drawable.ic_history_2,getString(R.string.nav_history)));
        drawerModelList.add(new DrawerModel(true,R.drawable.ic_contact_2,getString(R.string.nav_contact)));
        drawerModelList.add(new DrawerModel(true,R.drawable.ic_log_out_2,getString(R.string.nav_logout)));

        adapter.notifyDataSetChanged();
    }

    private void menu() {
        mDrawerList.getLayoutParams().width = 200;
        drawerModelList.clear();
        drawerModelList.add(new DrawerModel(false,R.drawable.ic_home_2,getString(R.string.nav_home)));
        drawerModelList.add(new DrawerModel(false,R.drawable.ic_taxi_2,getString(R.string.nav_taxi)));
        drawerModelList.add(new DrawerModel(false,R.drawable.ic_profile_2,getString(R.string.nav_profil)));
        drawerModelList.add(new DrawerModel(false,R.drawable.ic_request_2,getString(R.string.nav_request)));
        drawerModelList.add(new DrawerModel(false,R.drawable.ic_request_accepted_2,getString(R.string.nav_request_true)));
        drawerModelList.add(new DrawerModel(false,R.drawable.ic_wallet_2,getString(R.string.nav_wallet)));
        drawerModelList.add(new DrawerModel(false,R.drawable.ic_history_2,getString(R.string.nav_history)));
        drawerModelList.add(new DrawerModel(false,R.drawable.ic_contact_2,getString(R.string.nav_contact)));
        drawerModelList.add(new DrawerModel(false,R.drawable.ic_log_out_2,getString(R.string.nav_logout)));

        adapter.notifyDataSetChanged();
    }


    void setupToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }


    private void initNavigationMenu() {
        sessionDriver = new SessionDriver(this);

        NavigationView nav_view = (NavigationView) findViewById(R.id.nav_view);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(final MenuItem item) {
               /* Toast.makeText(getApplicationContext(), item.getTitle() + " Selected", Toast.LENGTH_SHORT).show();
                actionBar.setTitle(item.getTitle());*/
               if (item.getItemId()==R.id.nav_logout){
                   Alert();
               }
                drawer.closeDrawers();
                return true;
            }
        });

        // open drawer at start
        drawer.openDrawer(GravityCompat.START);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_notification, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void Alert() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        View contentView = getLayoutInflater().inflate(R.layout.logout_dialog,null);
        dialog.setContentView(contentView);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width =210;
        lp.height = 210;
       // lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        //lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        Button exitText = (Button) contentView.findViewById(R.id.exit);
        Button cancel = (Button) contentView.findViewById(R.id.cancel);

        exitText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionDriver.removeDriver();
                startActivity(new Intent(MainActivity.this, SignInActivity.class));
                finish();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void clicked(int position) {
        if (position == drawerModelList.size()-1){
            Alert();
        }
    }
}