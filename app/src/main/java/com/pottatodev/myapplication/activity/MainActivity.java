package com.pottatodev.myapplication.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.pottatodev.myapplication.R;
import com.pottatodev.myapplication.fragment.ConsultationFragment;
import com.pottatodev.myapplication.fragment.ProductFragment;
import com.pottatodev.myapplication.fragment.ServiceFragment;
import com.pottatodev.myapplication.helper.Config;
import com.pottatodev.myapplication.helper.H;
import com.pottatodev.myapplication.model.ConsultationModel;
import com.pottatodev.myapplication.model.ProductModel;
import com.pottatodev.myapplication.model.ServiceModel;
import com.pottatodev.myapplication.model.UserModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    FragmentManager homeFragmentManager;
    FragmentTransaction transaction;

    ImageView btnAddConsultation;

    H apiInterface;

    List<ProductModel> products = new ArrayList<>();
    List<ServiceModel> services = new ArrayList<>();
    List<ConsultationModel> consultations = new ArrayList<>();

    boolean initializingData = true;

    SharedPreferences preferences;
    String prefName;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefName = this.getPackageName();

        homeFragmentManager = getSupportFragmentManager();
        transaction = homeFragmentManager.beginTransaction();

        initView();

        initData();
    }

    void initView(){
        btnAddConsultation = findViewById(R.id.btnAddConsultation);

        btnAddConsultation.setOnClickListener((v) ->{
            Intent intent = new Intent(MainActivity.this, ConsultationActivity.class);
            startActivityForResult(intent, 200);
        });
    }

    void initData(){
        apiInterface = Config.getClient().create(H.class);

        requestProducts();
        requestServices();
        requestConsultation();

        initializingData = true;
    }

    void requestProducts(){
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
                showToast("Products cannot be fetched");
            }
        });
    }

    void requestServices(){
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
                showToast("Services cannot be fetched");
            }
        });

    }

    void requestConsultation(){
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
                showToast("Consultations cannot be fetched");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 200 && resultCode == 200){
            requestConsultation();
        }
        if(resultCode == 201) {
            requestProducts();
        }
    }

    void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    void removeLoginFromSP(){
        preferences = getSharedPreferences(prefName, MODE_PRIVATE);
        editor = preferences.edit();
        editor.putBoolean(UserModel.USER_LOGGED_IN, false);
        editor.apply();
        editor.commit();
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
            case R.id.action_logout:
                removeLoginFromSP();
                Intent logoutIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(logoutIntent);
                finish();
                return true;

            case R.id.action_transaction:
                removeLoginFromSP();
                Intent trxIntent = new Intent(MainActivity.this, TransactionActivity.class);
                startActivity(trxIntent);
                return true;

            case R.id.action_about_app:
                Intent aboutAppIntent = new Intent(MainActivity.this, AboutAppActivity.class);
                startActivity(aboutAppIntent);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}