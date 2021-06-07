package com.pottatodev.myapplication.activity.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.kusu.loadingbutton.LoadingButton;
import com.pottatodev.myapplication.R;
import com.pottatodev.myapplication.helper.Config;
import com.pottatodev.myapplication.helper.H;
import com.pottatodev.myapplication.model.ConsultationModel;
import com.pottatodev.myapplication.model.ProductModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminAddEditProductActivity extends AppCompatActivity {

    int productId;
    ProductModel product;

    H apiInterface;

    EditText edtProductName, edtProductDescription, edtProductPrice, edtProductImage, edtProductStock;
    ImageView imgProductImage;
    LoadingButton btnSubmit;
    Button btnLoadImage;
    LinearLayout llProductDetail;
    ProgressBar pbLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_edit_product);
        this.setTitle("Edit Product");

        apiInterface = Config.getClient().create(H.class);

        initViews();

        initData();
    }

    void initViews(){
        pbLoading = findViewById(R.id.pbLoading);
        llProductDetail = findViewById(R.id.llProductDetail);

        edtProductDescription = findViewById(R.id.edtProductDescription);
        edtProductName = findViewById(R.id.edtProductName);
        edtProductPrice = findViewById(R.id.edtProductPrice);
        edtProductStock = findViewById(R.id.edtProductStock);
        edtProductImage = findViewById(R.id.edtProductImage);
        imgProductImage = findViewById(R.id.imgProductImage);
        btnLoadImage = findViewById(R.id.btnLoadImage);
        btnLoadImage.setOnClickListener((v -> {
            loadImage(edtProductImage.getText().toString());
        }));

        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener((v) -> {
            btnSubmit.showLoading();

            if (!validateInput(edtProductName, edtProductDescription, edtProductPrice, edtProductStock, edtProductImage)) return;

            String name, desc, url;
            int price, stock;

            name = edtProductName.getText().toString();
            desc = edtProductDescription.getText().toString();
            url = edtProductImage.getText().toString();
            price = Integer.parseInt( edtProductPrice.getText().toString());
            stock = Integer.parseInt( edtProductStock.getText().toString());

            if (productId == 0) {
                apiInterface.createProduct(name, price, desc, url, stock).enqueue(new Callback<ConsultationModel>() {
                    @Override
                    public void onResponse(Call<ConsultationModel> call, Response<ConsultationModel> response) {
                        showToast("Product added successfully");
                        setResult(RESULT_OK);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<ConsultationModel> call, Throwable t) {
                        btnSubmit.hideLoading();
                        showToast("Please check your internet connection");
                    }
                });
            } else {
                ProductModel product = new ProductModel(name, price, desc, url, stock);
                apiInterface.updateProductsById(productId, product).enqueue(new Callback<ProductModel>() {
                    @Override
                    public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                        Log.d("AddEditProductActivity", String.valueOf(response.body()));

                        showToast("Product updated successfully");
                        setResult(RESULT_OK);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<ProductModel> call, Throwable t) {
                        btnSubmit.hideLoading();
                        showToast("Please check your internet connection");
                    }
                });
//                apiInterface.updateProductsById(productId, name, price, desc, url, stock).enqueue(new Callback<ProductModel>() {
//                    @Override
//                    public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
//                        Log.d("AddEditProductActivity", String.valueOf(response.body()));
//
//                        showToast("Product updated successfully");
//                        setResult(RESULT_OK);
//                        finish();
//                    }
//
//                    @Override
//                    public void onFailure(Call<ProductModel> call, Throwable t) {
//                        btnSubmit.hideLoading();
//                        showToast("Please check your internet connection");
//                    }
//                });
            }
        });
    }

    void initData() {
        Intent intent = getIntent();
        productId = intent.getIntExtra(ProductModel.PRODUCT_ID, 0);

        if (productId != 0) {
            apiInterface.getProductsById(productId).enqueue(new Callback<ProductModel>() {
                @Override
                public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                    product = response.body();

                    pbLoading.setVisibility(View.GONE);
                    llProductDetail.setVisibility(View.VISIBLE);

                    setTitle(product.getName());
                    edtProductName.setText(product.getName());
                    edtProductDescription.setText(product.getDescription());
                    edtProductPrice.setText(String.valueOf(product.getPrice()));
                    edtProductImage.setText(product.getImageUrl());
                    edtProductStock.setText(String.valueOf(product.getStock()));

                    loadImage(product.getImageUrl());
                }

                @Override
                public void onFailure(Call<ProductModel> call, Throwable t) {
                    showToast("Please check your internet connection");
                }
            });

        } else {
            this.setTitle("Add Product");
            pbLoading.setVisibility(View.GONE);
            llProductDetail.setVisibility(View.VISIBLE);
        }
    }

    void loadImage(String url){
        RequestOptions options = new RequestOptions().centerCrop().placeholder(R.drawable.ic_baseline_error_outline_48).error(R.mipmap.ic_launcher_round);
        Glide.with(AdminAddEditProductActivity.this).load(url).apply(options).into(imgProductImage);
    }

    void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    boolean validateInput(EditText ...target){
        for (EditText editText : target) {
            if (editText.getText().toString().isEmpty()) {
                editText.setError("This field cannot be empty");
                return false;
            }
        }

        return true;
    }
}