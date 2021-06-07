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
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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

    @GET("product/promo/")
    Call<ProductModel> getProductsPromo();

    @FormUrlEncoded
    @POST("product/")
    Call<ConsultationModel> createProduct(@Field("name") String title, @Field("price") int price, @Field("description") String description, @Field("image") String imageUrl, @Field("stock") int stock);

    @FormUrlEncoded
    @POST("product/buy/")
    Call<ConsultationModel> buyProduct(@Field("user") int id, @Field("product") int productId, @Field("quantity") int qty);

    @FormUrlEncoded
    @POST("product/promo/")
    Call<ConsultationModel> createProductPromo(@Field("product") int productId, @Field("title") String title, @Field("banner") String banner);

    @PUT("product/{id}")
    Call<ProductModel> putProductsById(@Path("id") int id);

//    @FormUrlEncoded
//    @PATCH("product/{id}")
//    Call<ProductModel> updateProductsById(@Path("id") int id, @Field("name") String title, @Field("price") int price, @Field("description") String description, @Field("image") String imageUrl, @Field("stock") int stock);
    @PATCH("product/{id}")
    Call<ProductModel> updateProductsById(@Path("id") int id, @Body ProductModel product);

    @DELETE("product/{id}")
    Call<ProductModel> deleteProductsById(@Path("id") int id);

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
