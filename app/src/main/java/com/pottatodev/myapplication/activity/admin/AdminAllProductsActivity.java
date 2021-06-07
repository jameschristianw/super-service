package com.pottatodev.myapplication.activity.admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.pottatodev.myapplication.R;
import com.pottatodev.myapplication.activity.AboutAppActivity;
import com.pottatodev.myapplication.activity.LoginActivity;
import com.pottatodev.myapplication.activity.MainActivity;
import com.pottatodev.myapplication.adapter.AllProductAdapter;
import com.pottatodev.myapplication.adapter.ProductAdapter;
import com.pottatodev.myapplication.fragment.ProductFragment;
import com.pottatodev.myapplication.fragment.ServiceFragment;
import com.pottatodev.myapplication.helper.Config;
import com.pottatodev.myapplication.helper.H;
import com.pottatodev.myapplication.model.ProductModel;
import com.pottatodev.myapplication.model.ServiceModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminAllProductsActivity extends AppCompatActivity {

    RecyclerView rvAdminAllProducts;
    AllProductAdapter adapter;
    ProgressBar pbLoading;

    boolean initializingData = true;

    H apiInterface;

    List<ProductModel> products = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_all_products);
        this.setTitle("All Products");

        initViews();

        initData();
    }

    void initViews(){
        rvAdminAllProducts = findViewById(R.id.rvAdminAllProducts);
        rvAdminAllProducts.setLayoutManager(new LinearLayoutManager(this));

        pbLoading = findViewById(R.id.pbLoading);
    }

    void initData(){
        apiInterface = Config.getClient().create(H.class);

        requestProducts();
    }
    void requestProducts(){
        pbLoading.setVisibility(View.VISIBLE);
        rvAdminAllProducts.setVisibility(View.GONE);
        Call<List<ProductModel>> getProducts = apiInterface.getProducts();
        getProducts.enqueue(new Callback<List<ProductModel>>() {
            @Override
            public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {
                Log.d("MainActivity", "getProduct response code " + String.valueOf(response.code()));

                rvAdminAllProducts.setVisibility(View.VISIBLE);
                products.clear();
                products = response.body();

                adapter = new AllProductAdapter(products, AdminAllProductsActivity.this, 1);
                rvAdminAllProducts.setAdapter(adapter);

                pbLoading.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<ProductModel>> call, Throwable t) {
                showToast("Products cannot be fetched");
            }
        });
    }

    void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.admin_product_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                Intent logoutIntent = new Intent(AdminAllProductsActivity.this, LoginActivity.class);
                startActivity(logoutIntent);
                finish();
                return true;

            case R.id.action_add:
                Intent aboutAppIntent = new Intent(AdminAllProductsActivity.this, AdminAddEditProductActivity.class);
                startActivityForResult(aboutAppIntent, 200);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            requestProducts();
        }
    }
}