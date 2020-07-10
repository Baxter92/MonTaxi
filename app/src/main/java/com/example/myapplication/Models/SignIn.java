package com.example.myapplication.Models;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignIn {
    private String phone;
    private String password;

    public SignIn(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String JsonUser(){
        Map<String, String> params = new HashMap<>();
        params.put("phone", phone);
        params.put("password", password);

        JSONObject jsonUser = new JSONObject(params);
        return jsonUser.toString();
    }
}
