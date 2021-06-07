package com.pottatodev.myapplication.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Entity(tableName = "tableTransaction")
public class ProductModel implements Serializable {

    public static final String PRODUCT_ID = "PRODUCT_ID";

    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    private int id;

    @ColumnInfo(name = "name")
    @SerializedName("name")
    @Expose
    private String name;

    @ColumnInfo(name = "price")
    @SerializedName("price")
    @Expose
    private int price;

    @ColumnInfo(name = "description")
    @SerializedName("description")
    @Expose
    private String description;

    @ColumnInfo(name = "image")
    @SerializedName("image")
    @Expose
    private String imageUrl;

    @ColumnInfo(name = "qty")
    @SerializedName("stock")
    @Expose
    private int stock;

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "created_at")
    private String created_at;

    @Ignore
    public ProductModel(int id, String name, int price, String description, String imageUrl, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.stock = stock;
        created_at = Calendar.getInstance().getTime().toString();
    }

    public ProductModel(String name, int price, String description, String imageUrl, int stock) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public static String getProductId() {
        return PRODUCT_ID;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @NonNull
    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(@NonNull String created_at) {
        this.created_at = created_at;
    }
}
