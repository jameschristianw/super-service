package com.pottatodev.myapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserConsultationModel {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("user")
    @Expose
    private int user;
    @SerializedName("consultation")
    @Expose
    private int consultation;

    public UserConsultationModel(int id, int user, int consultation) {
        this.id = id;
        this.user = user;
        this.consultation = consultation;
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

    public int getConsultation() {
        return consultation;
    }

    public void setConsultation(int consultation) {
        this.consultation = consultation;
    }
}
