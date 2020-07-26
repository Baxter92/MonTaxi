package com.example.myapplication.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.example.myapplication.Interface.DrawerItemClick;
import com.example.myapplication.Models.Utils.DrawerModel;
import com.example.myapplication.R;

import java.util.List;

public class DrawerItemCustomAdapter extends BaseAdapter {

    private Context context;
    private List<DrawerModel> dataList;
    DrawerItemClick drawerItemClick;
    private int mSelectedItem;
    private boolean isHomeIcon;

    public DrawerItemCustomAdapter(@NonNull Context context, List<DrawerModel> dataList, DrawerItemClick drawerItemClick) {
        this.dataList = dataList;
        this.context = context;
        this.drawerItemClick = drawerItemClick;
        this.isHomeIcon = false;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int i) {
        return dataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.list_view_item_row, viewGroup, false);
        }

        // get current item to be displayed
        DrawerModel currentItem = (DrawerModel) getItem(i);

        // get the TextView for item name and item description

        RelativeLayout container = (RelativeLayout)convertView.findViewById(R.id.container);
        TextView title = (TextView)
                convertView.findViewById(R.id.textViewName);
        ImageView iconImg = (ImageView)
                convertView.findViewById(R.id.imageViewIcon);

        if (i==0 || i==getCount()-1) {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.taxiapp));
            iconImg.setColorFilter(context.getResources().getColor(R.color.black));
            title.setTextColor(context.getResources().getColor(R.color.black));
        }
        //sets the text for item name and item description from the current item object
        if (!currentItem.isOnlyIcon()) {
            title.setText(currentItem.getName());
           // iconImg.setColorFilter(context.getResources().getColor(R.color.white));
            //container.setBackgroundColor(context.getResources().getColor(R.color.drawer_color));
        }else {
            title.setText("");
        }

        Glide.with(context).load(currentItem.getIcon()).into(iconImg);
        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelectedItem = i;
                isHomeIcon = false;
                drawerItemClick.clicked(i);
            }
        });
        return convertView;
    }

    public void setHomeIcon(){
        isHomeIcon = true ;
    }
}
