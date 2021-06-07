package com.pottatodev.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.pottatodev.myapplication.R;
import com.pottatodev.myapplication.model.ProductModel;

public class PaymentActivity extends AppCompatActivity {

    ProductModel product;
    ImageView imgProduct;
    TextView tvProductName, tvProductQty, tvProductPrice;
    Button btnReturn;

    String name, imageUrl;
    int total, qty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Intent intent = getIntent();
//        product = intent.getParcelableExtra("PRODUCT");
        name = intent.getStringExtra("PRODUCT_NAME");
        imageUrl = intent.getStringExtra("PRODUCT_IMAGE");
        total = intent.getIntExtra("PRODUCT_PRICE", 0);
        qty = intent.getIntExtra("PRODUCT_QTY", 0);

        initViews();
    }

    void initViews(){
        imgProduct = findViewById(R.id.imgProduct);
        tvProductName = findViewById(R.id.tvProductName);
        tvProductQty = findViewById(R.id.tvProductQty);
        tvProductPrice = findViewById(R.id.tvProductPrice);
        btnReturn = findViewById(R.id.btnReturn);

        RequestOptions options = new RequestOptions().centerCrop().placeholder(R.drawable.ic_baseline_error_outline_48).error(R.mipmap.ic_launcher_round);
        Glide.with(this).load(imageUrl).apply(options).into(imgProduct);

        String productQty = qty + "x";

        tvProductName.setText(name);
        tvProductQty.setText(productQty);
        tvProductPrice.setText(String.valueOf(total));

        btnReturn.setOnClickListener((v -> {
            setResult(RESULT_OK);
            finish();
        }));
    }
}