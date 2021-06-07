package com.pottatodev.myapplication.activity.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pottatodev.myapplication.R;
import com.pottatodev.myapplication.activity.AboutAppActivity;
import com.pottatodev.myapplication.activity.LoginActivity;
import com.pottatodev.myapplication.helper.H;
import com.pottatodev.myapplication.model.UserModel;

public class AdminMainActivity extends AppCompatActivity {

    LinearLayout llAllProducts, llAllServices;

    SharedPreferences preferences;
    String prefName;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        this.setTitle("Admin Super Service");

        prefName = this.getPackageName();

        initViews();
    }

    void initViews(){
        llAllProducts = findViewById(R.id.llAllProducts);
        llAllServices = findViewById(R.id.llAllServices);

        llAllServices.setOnClickListener((v -> {
            Intent servicesIntent = new Intent(AdminMainActivity.this, AdminAllServicesActivity.class);
            startActivity(servicesIntent);
        }));

        llAllProducts.setOnClickListener((v -> {
            Intent productsIntent = new Intent(AdminMainActivity.this, AdminAllProductsActivity.class);
            startActivity(productsIntent);
        }));
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
                Intent logoutIntent = new Intent(AdminMainActivity.this, LoginActivity.class);
                startActivity(logoutIntent);
                finish();
                return true;

            case R.id.action_about_app:
                Intent aboutAppIntent = new Intent(AdminMainActivity.this, AboutAppActivity.class);
                startActivity(aboutAppIntent);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    void removeLoginFromSP() {
        preferences = getSharedPreferences(prefName, MODE_PRIVATE);
        editor = preferences.edit();
        editor.putBoolean(UserModel.USER_LOGGED_IN, false);
        editor.apply();
        editor.commit();
    }
}