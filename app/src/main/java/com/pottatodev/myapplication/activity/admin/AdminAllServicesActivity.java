package com.pottatodev.myapplication.activity.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.pottatodev.myapplication.R;
import com.pottatodev.myapplication.adapter.AllProductAdapter;
import com.pottatodev.myapplication.adapter.AllServiceAdapter;
import com.pottatodev.myapplication.helper.Config;
import com.pottatodev.myapplication.helper.H;
import com.pottatodev.myapplication.model.ProductModel;
import com.pottatodev.myapplication.model.ServiceModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminAllServicesActivity extends AppCompatActivity {

    RecyclerView rvAdminAllServices;
    AllServiceAdapter adapter;
    ProgressBar pbLoading;

    boolean initializingData = true;

    H apiInterface;

    List<ServiceModel> services = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_all_services);
        this.setTitle("All Services");

        initViews();

        initData();
    }

    void initViews(){
        rvAdminAllServices = findViewById(R.id.rvAdminAllProducts);
        rvAdminAllServices.setLayoutManager(new LinearLayoutManager(this));

        pbLoading = findViewById(R.id.pbLoading);
    }

    void initData(){
        apiInterface = Config.getClient().create(H.class);

        requestServices();
    }

    void requestServices(){
        pbLoading.setVisibility(View.VISIBLE);
        rvAdminAllServices.setVisibility(View.GONE);
        Call<List<ServiceModel>> getProducts = apiInterface.getServices();
        getProducts.enqueue(new Callback<List<ServiceModel>>() {
            @Override
            public void onResponse(Call<List<ServiceModel>> call, Response<List<ServiceModel>> response) {
                Log.d("MainActivity", "getProduct response code " + String.valueOf(response.code()));

                rvAdminAllServices.setVisibility(View.VISIBLE);
                services.clear();
                services = response.body();

                adapter = new AllServiceAdapter(services, AdminAllServicesActivity.this, 1);
                rvAdminAllServices.setAdapter(adapter);

                pbLoading.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<ServiceModel>> call, Throwable t) {
                showToast("Products cannot be fetched");
            }
        });
    }

    void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}