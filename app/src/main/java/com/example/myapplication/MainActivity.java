package com.example.myapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.Fragment.EditProfilFragment;
import com.example.myapplication.Fragment.HomeFragment;
import com.example.myapplication.Interface.DrawerItemClick;
import com.example.myapplication.Interface.networksJO;
import com.example.myapplication.Models.Config;
import com.example.myapplication.Models.Town;
import com.example.myapplication.Models.Utils.DrawerModel;
import com.example.myapplication.Models.Utils.EventTown;
import com.example.myapplication.Models.Utils.SessionDriver;
import com.example.myapplication.Service.Networks;
import com.example.myapplication.activities.RecorvedChatActivity;
import com.example.myapplication.activities.SignInActivity;
import com.example.myapplication.adapter.DrawerItemCustomAdapter;
import com.google.android.material.navigation.NavigationView;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements DrawerItemClick, networksJO {

    private EditText countryEdt;
    private EditText numberEdt;
    private EditText passwordEdt;
    private Button gotIt;
    private ImageView imageIcon;
    private RelativeLayout menuIcon;

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

    public void setIconToolbar(Uri uri, int drawable){
        if (drawable ==-1) {
            Glide.with(this)
                    .load(uri)
                    .into(imageIcon);
        }else {
            Glide.with(this)
                    .load(drawable)
                    .into(imageIcon);
        }
    }

    public void initToolbar() {
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
        menuIcon = (RelativeLayout)findViewById(R.id.menu);
        imageIcon = (ImageView)findViewById(R.id.icon);

        adapter = new DrawerItemCustomAdapter(this,drawerModelList, this);
        mDrawerList.setAdapter(adapter);

        mDrawerList.setItemChecked(0, true);
        mDrawerList.setSelection(0);
           menuIcon();

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerModelList.get(0).isOnlyIcon()) {
                    menu();
                }else {
                    menuIcon2();
                }
            }
        });
    }

    private void menuIcon() {
        mDrawerList.getLayoutParams().width = 90;
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

    private void menuIcon2() {
        mDrawerList.getLayoutParams().width = 90;
        drawerModelList.set(0,new DrawerModel(true,R.drawable.ic_home_2,getString(R.string.nav_home)));
        drawerModelList.set(1,new DrawerModel(true,R.drawable.ic_taxi_2,getString(R.string.nav_taxi)));
        drawerModelList.set(2,new DrawerModel(true,R.drawable.ic_profile_2,getString(R.string.nav_profil)));
        drawerModelList.set(3,new DrawerModel(true,R.drawable.ic_request_2,getString(R.string.nav_request)));
        drawerModelList.set(4,new DrawerModel(true,R.drawable.ic_request_accepted_2,getString(R.string.nav_request_true)));
        drawerModelList.set(5,new DrawerModel(true,R.drawable.ic_wallet_2,getString(R.string.nav_wallet)));
        drawerModelList.set(6,new DrawerModel(true,R.drawable.ic_history_2,getString(R.string.nav_history)));
        drawerModelList.set(7,new DrawerModel(true,R.drawable.ic_contact_2,getString(R.string.nav_contact)));
        drawerModelList.set(8,new DrawerModel(true,R.drawable.ic_log_out_2,getString(R.string.nav_logout)));

        adapter.notifyDataSetChanged();
    }
    private void menu() {
        mDrawerList.getLayoutParams().width = 300;
        drawerModelList.set(0,new DrawerModel(false,R.drawable.ic_home_2,getString(R.string.nav_home)));
        drawerModelList.set(1,new DrawerModel(false,R.drawable.ic_taxi_2,getString(R.string.nav_taxi)));
        drawerModelList.set(2,new DrawerModel(false,R.drawable.ic_profile_2,getString(R.string.nav_profil)));
        drawerModelList.set(3,new DrawerModel(false,R.drawable.ic_request_2,getString(R.string.nav_request)));
        drawerModelList.set(4,new DrawerModel(false,R.drawable.ic_request_accepted_2,getString(R.string.nav_request_true)));
        drawerModelList.set(5,new DrawerModel(false,R.drawable.ic_wallet_2,getString(R.string.nav_wallet)));
        drawerModelList.set(6,new DrawerModel(false,R.drawable.ic_history_2,getString(R.string.nav_history)));
        drawerModelList.set(7,new DrawerModel(false,R.drawable.ic_contact_2,getString(R.string.nav_contact)));
        drawerModelList.set(8,new DrawerModel(false,R.drawable.ic_log_out_2,getString(R.string.nav_logout)));

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
        lp.width =310;
        lp.height = 310;
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
        mDrawerList.setItemChecked(position, true);
        mDrawerList.setSelection(position);
        //mDrawerList.getChildAt(position).setBackgroundColor(getResources().getColor(R.color.taxiapp));
        if (position == drawerModelList.size()-1){
            Alert();
        }else if (position == 2){
            replaceFragment(EditProfilFragment.newInstance(),Config.EditProfil);
        }else {
            replaceFragment(HomeFragment.newInstance(),Config.Home);
        }
    }

    private void replaceFragment(Fragment newFragment, String tag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, newFragment, tag);
        ft.addToBackStack(null);
        ft.commit();

    }

    private void getTownFromCountry() {
        Map<String, Map<String, String>> whereParam = new HashMap<>();
        Map<String, String> flagParam = new HashMap<>();
        flagParam.put("country_code", Config.cameroonFlag);
        whereParam.put("where",flagParam);

        JSONObject flagObject = new JSONObject(whereParam);
        String filterJson = flagObject.toString();

        List<String> countrieList = new ArrayList<>();
        countrieList.add("towns");
        Map<String, String> filter = new HashMap<>();
        filter.put("filter",filterJson);
        Networks networks = new Networks(MainActivity.this,this);
        networks.getvolley(networks.EncodeUrl(countrieList,filter));

    }

    @Override
    public void getVolleyJson(Context context, JSONObject jsonObject, JSONArray jsonArray, int code) {
        try {
            List<Town> townList = new ArrayList<>();
            int country_id = jsonObject.getInt("country_id");
            if (country_id == 1) {
                Town town = new Town(jsonObject.getInt("id"), jsonObject.getJSONObject("name").getString("fr"),
                        jsonObject.getJSONObject("location").getLong("lng"), jsonObject.getJSONObject("location").getLong("lat"));
                townList.add(town);
            }
            EventBus.getDefault().postSticky(new EventTown(country_id,townList));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().removeAllStickyEvents();
        super.onStop();
    }

    @Override
    public void getVolleyFromPostJson(Context context, JSONObject jsonObject, JSONArray jsonArray, int code) {

    }

    @Override
    public void geterrorVolley(Context context, String error) {

    }
}