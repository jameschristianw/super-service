package com.pottatodev.myapplication.helper;

import com.pottatodev.myapplication.model.RegisterModel;
import com.pottatodev.myapplication.model.UserModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface H {
//    @FormUrlEncoded
    @POST("authentication/register/")
    Call<ResponseBody> registerUser(@Body RegisterModel registerModel);

    @POST("authentication/login/")
    Call<UserModel> loginUser(@Body UserModel userModel);

    @FormUrlEncoded
    @POST("authentication/login/")
    Call<UserModel> loginUser(@Field("email") String email, @Field("password") String password);
}
