package com.pottatodev.myapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserProductModel {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("user")
    @Expose
    private int user;
    @SerializedName("product")
    @Expose
    private int product;

    public UserProductModel(int id, int user, int product) {
        this.id = id;
        this.user = user;
        this.product = product;
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

    public int getProduct() {
        return product;
    }

    public void setProduct(int product) {
        this.product = product;
    }
}
