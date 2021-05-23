package com.pottatodev.myapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserServiceModel {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("user")
    @Expose
    private int user;
    @SerializedName("service")
    @Expose
    private int service;

    public UserServiceModel(int id, int user, int service) {
        this.id = id;
        this.user = user;
        this.service = service;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public int getService() {
        return service;
    }

    public void setService(int service) {
        this.service = service;
    }
}
