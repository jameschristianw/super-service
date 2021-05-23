package com.pottatodev.myapplication.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.pottatodev.myapplication.R;
import com.pottatodev.myapplication.fragment.ConsultationFragment;
import com.pottatodev.myapplication.fragment.ProductFragment;
import com.pottatodev.myapplication.fragment.ServiceFragment;
import com.pottatodev.myapplication.helper.Config;
import com.pottatodev.myapplication.helper.H;
import com.pottatodev.myapplication.model.ConsultationModel;
import com.pottatodev.myapplication.model.ProductModel;
import com.pottatodev.myapplication.model.ServiceModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    FragmentManager homeFragmentManager;
    FragmentTransaction transaction;
    H apiInterface;

    List<ProductModel> products = new ArrayList<>();
    List<ServiceModel> services = new ArrayList<>();
    List<ConsultationModel> consultations = new ArrayList<>();

    boolean initializingData = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeFragmentManager = getSupportFragmentManager();
        transaction = homeFragmentManager.beginTransaction();

        initData();
    }

    void initData(){
        apiInterface = Config.getClient().create(H.class);

        Call<List<ProductModel>> getProducts = apiInterface.getProducts();
        getProducts.enqueue(new Callback<List<ProductModel>>() {
            @Override
            public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {
                Log.d("MainActivity", "getProduct response code " + String.valueOf(response.code()));

                products.clear();
                products = response.body();

                transaction = homeFragmentManager.beginTransaction();
                Fragment productFragment = new ProductFragment(products, MainActivity.this);
                transaction.replace(R.id.productFrameLayout, productFragment);
                transaction.commit();
            }

            @Override
            public void onFailure(Call<List<ProductModel>> call, Throwable t) {

            }
        });

        Call<List<ServiceModel>> getServices = apiInterface.getServices();
        getServices.enqueue(new Callback<List<ServiceModel>>() {
            @Override
            public void onResponse(Call<List<ServiceModel>> call, Response<List<ServiceModel>> response) {
                Log.d("MainActivity", "getServices response code " + String.valueOf(response.code()));

                services.clear();
                services = response.body();

                transaction = homeFragmentManager.beginTransaction();
                Fragment serviceFragment = new ServiceFragment(services);
                transaction.replace(R.id.serviceFrameLayout, serviceFragment);
                transaction.commit();
            }

            @Override
            public void onFailure(Call<List<ServiceModel>> call, Throwable t) {

            }
        });

        Call<List<ConsultationModel>> getConsultations = apiInterface.getConsultations();
        getConsultations.enqueue(new Callback<List<ConsultationModel>>() {
            @Override
            public void onResponse(Call<List<ConsultationModel>> call, Response<List<ConsultationModel>> response) {
                Log.d("MainActivity", "getConsultations response code " + String.valueOf(response.code()));

                consultations.clear();
                consultations = response.body();

                transaction = homeFragmentManager.beginTransaction();
                Fragment consultationFragment = new ConsultationFragment(consultations);
                transaction.replace(R.id.consultationFrameLayout, consultationFragment);
                transaction.commit();
            }

            @Override
            public void onFailure(Call<List<ConsultationModel>> call, Throwable t) {

            }
        });

        initializingData = true;
    }

    void initFragment(){
        homeFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = homeFragmentManager.beginTransaction();

        Fragment productFragment = new ProductFragment(products, MainActivity.this);
        transaction.replace(R.id.productFrameLayout, productFragment);

        Fragment serviceFragment = new ServiceFragment(services);
        transaction.replace(R.id.serviceFrameLayout, serviceFragment);

        Fragment consultationFragment = new ConsultationFragment(consultations);
        transaction.replace(R.id.consultationFrameLayout, consultationFragment);

        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.action_favorite:
//                // User chose the "Settings" item, show the app settings UI...
//                return true;

            case R.id.action_logout:
                Intent logoutIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(logoutIntent);
                finish();
                return true;

            case R.id.action_about_app:
                Intent aboutAppIntent = new Intent(MainActivity.this, AboutAppActivity.class);
                startActivity(aboutAppIntent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}