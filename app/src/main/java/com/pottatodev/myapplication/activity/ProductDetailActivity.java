package com.pottatodev.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.pottatodev.myapplication.R;
import com.pottatodev.myapplication.helper.Config;
import com.pottatodev.myapplication.helper.H;
import com.pottatodev.myapplication.model.ProductModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {

    int productId;
    int buyQty = 1;

    H apiInterface;
    ProductModel product;

    ImageView imgProductDetailImage;
    TextView tvProductDetailName, tvProductDetailPrice, tvProductDetailDescription;
    LinearLayout llBuyQty;
    Button btnBuyProduct;
    EditText edtItemQty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        llBuyQty = findViewById(R.id.llBuyQty);

        Intent intent = getIntent();
        productId = intent.getIntExtra(ProductModel.PRODUCT_ID, 0);
        this.setTitle("Loading");

        initData();
    }

    void initData(){
        apiInterface = Config.getClient().create(H.class);

        Call<ProductModel> getProduct = apiInterface.getProductsById(productId);
        getProduct.enqueue(new Callback<ProductModel>() {
            @Override
            public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                product = response.body();

                Log.d("ProductDetail", String.valueOf(response.code()));
                Log.d("ProductDetail", product.getName());

                if(product != null){
                    ProductDetailActivity.this.setTitle(product.getName());
                    initViews();
                }
            }

            @Override
            public void onFailure(Call<ProductModel> call, Throwable t) {
                showToast("Please check your internet connection");
            }
        });
    }

    void initViews(){
        imgProductDetailImage = findViewById(R.id.imgProductDetailImage);
        tvProductDetailName = findViewById(R.id.tvProductDetailName);
        tvProductDetailPrice = findViewById(R.id.tvProductDetailPrice);
        tvProductDetailDescription = findViewById(R.id.tvProductDetailDescription);
        btnBuyProduct = findViewById(R.id.btnBuyProduct);
        edtItemQty = findViewById(R.id.edtItemQty);

        RequestOptions options = new RequestOptions().error(R.mipmap.ic_launcher_round);
        Glide.with(this).load(product.getImageUrl()).apply(options).into(imgProductDetailImage);

        tvProductDetailName.setText(product.getName());
        tvProductDetailPrice.setText(product.getPrice());
        tvProductDetailDescription.setText(product.getDescription());

        btnBuyProduct.setOnClickListener((v) -> {
            if(llBuyQty.getVisibility() == View.VISIBLE ) {
                if (edtItemQty.getText().toString().equals("")){
                    showToast("Please enter the quantity");
                } else if (Integer.parseInt(edtItemQty.getText().toString()) == 0){
                    showToast("Quantity cannot be 0");
                }
                else {
                    showToast("Success buying " + buyQty + " " + product.getName());
                    finish();
                }
            } else {
                llBuyQty.setVisibility(View.VISIBLE);
                edtItemQty.setText(String.valueOf(buyQty));
                btnBuyProduct.setText("Buy " + String.valueOf(buyQty));
            }
        });

        edtItemQty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                buyQty = s.toString().equals("") ? 0 : Integer.parseInt(s.toString());
                btnBuyProduct.setText("Buy " + s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}