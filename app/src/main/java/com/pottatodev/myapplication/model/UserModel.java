package com.pottatodev.myapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserModel implements Serializable {

    public static String USER_DATA = "USER_DATA";
    public static String USER_EMAIL = "USER_EMAIL";
    public static String USER_USERNAME = "USER_USERNAME";
    public static String USER_REFRESH_TOKEN = "USER_REFRESH_TOKEN";
    public static String USER_ACCESS_TOKEN = "USER_ACCESS_TOKEN";


    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("tokens")
    @Expose
    private TokenModel tokens;

    public UserModel(String email, String username) {
        this.email = email;
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public TokenModel getTokens() {
        return tokens;
    }

    public void setTokens(TokenModel tokens) {
        this.tokens = tokens;
    }

}