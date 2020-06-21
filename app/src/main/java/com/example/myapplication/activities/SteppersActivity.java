package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.adapter.MyViewPagerAdapter;

public class SteppersActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int MAX_STEP = 3;

    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private ImageButton imageLeft, imageRight;
    private Button skip;

    private int images_array[] = {
            R.drawable.stepperone,
            R.drawable.steppertwo,
            R.drawable.splashscreentree
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steppers);

        init();

        imageLeft.setOnClickListener(this);
        imageRight.setOnClickListener(this);
        skip.setOnClickListener(this);
        String[] titles  = getResources().getStringArray(R.array.titles);
        String[] descriptions  = getResources().getStringArray(R.array.descriptions);

        myViewPagerAdapter = new MyViewPagerAdapter(this,titles,descriptions,images_array);
        viewPager.setAdapter(myViewPagerAdapter);
    }

    private void init() {
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        imageLeft = (ImageButton)findViewById(R.id.imageleft);
        imageRight = (ImageButton)findViewById(R.id.imageright);
        skip = (Button)findViewById(R.id.skip);

        ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
              if (position == 0){
                    skip.setVisibility(View.VISIBLE);
                    imageLeft.setEnabled(false);
                }else if (position<MAX_STEP){
                    skip.setVisibility(View.VISIBLE);
                    imageRight.setEnabled(true);
                    imageLeft.setEnabled(true);
                }
                else {
                    skip.setVisibility(View.INVISIBLE);
                    imageLeft.setEnabled(true);
                    imageRight.setEnabled(false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        };
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageleft:
                //left click
                int current_left = viewPager.getCurrentItem() - 1;
                if (current_left>=0){
                    //move to previous step
                    viewPager.setCurrentItem(current_left);
                    imageRight.setEnabled(true);
                }else {
                    imageLeft.setEnabled(false);
                    imageRight.setEnabled(true);
                }
                skip.setVisibility(View.VISIBLE);
                break;
            case R.id.imageright:
                //right click
                int current_right = viewPager.getCurrentItem() + 1;
                if (current_right<MAX_STEP){
                    //move to next step
                    viewPager.setCurrentItem(current_right);
                    imageLeft.setEnabled(true);
                }else{
                    imageRight.setEnabled(false);
                    imageLeft.setEnabled(true);
                    skip.setVisibility(View.INVISIBLE);
                    startActivity(new Intent(SteppersActivity.this, MainActivity.class));
                    finish();
                }


                break;
            case R.id.skip:
                //skip
                startActivity(new Intent(SteppersActivity.this, MainActivity.class));
                finish();
                break;
        }
    }
}