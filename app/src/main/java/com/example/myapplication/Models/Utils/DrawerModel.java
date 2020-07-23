package com.example.myapplication.Models.Utils;

public class DrawerModel {

    private boolean onlyIcon;
    private int icon;
    private String name;

    public DrawerModel(boolean onlyIcon, int icon, String name) {
        this.onlyIcon = onlyIcon;
        this.icon = icon;
        this.name = name;
    }

    public boolean isOnlyIcon() {
        return onlyIcon;
    }

    public int getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public void setOnlyIcon(boolean onlyIcon) {
        this.onlyIcon = onlyIcon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setName(String name) {
        this.name = name;
    }
}
