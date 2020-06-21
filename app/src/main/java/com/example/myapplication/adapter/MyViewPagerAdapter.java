package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;

import com.example.myapplication.R;


public class MyViewPagerAdapter extends PagerAdapter {

    private LayoutInflater layoutInflater;
    private String[] title;
    private String[] description;
    private int[] image;
    private Context context;

    public MyViewPagerAdapter(Context context,String[] title, String[] description, int[] image) {
        this.context = context;
        this.title = title;
        this.description = description;
        this.image = image;
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.item_stepper_wizard, container, false);
        ((TextView) view.findViewById(R.id.title)).setText(title[position]);
        ((TextView) view.findViewById(R.id.description)).setText(description[position]);
        //((ImageView) view.findViewById(R.id.image)).setImageResource(image[position]);
        //((RelativeLayout) view.findViewById(R.id.lyt_parent)).setBackgroundResource(image[position]);

        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            ((RelativeLayout) view.findViewById(R.id.lyt_parent)).setBackgroundDrawable(ContextCompat.getDrawable(context, image[position]) );
        } else {
            ((RelativeLayout) view.findViewById(R.id.lyt_parent)).setBackground(ContextCompat.getDrawable(context, image[position]));
        }

        container.addView(view);

        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}
