package com.pottatodev.myapplication.helper;

import com.pottatodev.myapplication.model.ConsultationModel;
import com.pottatodev.myapplication.model.ProductModel;
import com.pottatodev.myapplication.model.RegisterModel;
import com.pottatodev.myapplication.model.ServiceModel;
import com.pottatodev.myapplication.model.UserModel;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface H {
//    @FormUrlEncoded
    @POST("authentication/register/")
    Call<ResponseBody> registerUser(@Body RegisterModel registerModel);

    @POST("authentication/login/")
    Call<UserModel> loginUser(@Body UserModel userModel);

    @FormUrlEncoded
    @POST("authentication/login/")
    Call<UserModel> loginUser(@Field("email") String email, @Field("password") String password);

    // Consultation
    @GET("consultation/")
    Call<List<ConsultationModel>> getConsultations();

    @FormUrlEncoded
    @POST("consultation/")
    Call<ConsultationModel> createConsultation(@Field("title") String title, @Field("description") String description, @Field("vehicle") String vehicle);

    @FormUrlEncoded
    @POST("consultation/consult/")
    Call<ConsultationModel> createConsultationConsult(@Field("title") int user, @Field("consultation") int consultation);

    // Product
    @GET("product/")
    Call<List<ProductModel>> getProducts();

    @GET("product/{id}")
    Call<ProductModel> getProductsById(@Path("id") int id);

    @FormUrlEncoded
    @POST("product/")
    Call<ConsultationModel> createProduct(@Field("name") String title, @Field("price") int price, @Field("description") String description);

    @FormUrlEncoded
    @POST("product/buy/")
    Call<ConsultationModel> createProductBuy(@Field("user") int user, @Field("product") int consultation);


    // Services
    @GET("service/")
    Call<List<ServiceModel>> getServices();

    @FormUrlEncoded
    @POST("service/")
    Call<ConsultationModel> createService(@Field("name") String title, @Field("price") int price, @Field("description") String description);

    @FormUrlEncoded
    @POST("service/buy/")
    Call<ConsultationModel> createServiceBuy(@Field("user") int user, @Field("service") int consultation);
}
