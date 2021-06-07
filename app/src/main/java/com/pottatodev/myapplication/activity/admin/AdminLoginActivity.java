package com.pottatodev.myapplication.activity.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pottatodev.myapplication.R;
import com.pottatodev.myapplication.model.UserModel;

public class AdminLoginActivity extends AppCompatActivity {

    EditText edtEmailAdmin, edtPasswordAdmin;
    Button btnLoginAdmin, btnCancel;
    TextView tvLoginError;

    String email, password;

    SharedPreferences preferences;
    String prefName;
    SharedPreferences.Editor editor;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        prefName = AdminLoginActivity.this.getPackageName();
        preferences = getSharedPreferences(prefName, MODE_PRIVATE);

        initViews();
    }

    void initViews(){
        btnLoginAdmin = findViewById(R.id.btnLoginAdmin);
        btnCancel = findViewById(R.id.btnCancel);
        edtEmailAdmin = findViewById(R.id.edtEmailAdmin);
        edtPasswordAdmin = findViewById(R.id.edtPasswordAdmin);
        tvLoginError = findViewById(R.id.tvLoginError);

        btnLoginAdmin.setOnClickListener((v -> {
            email = edtEmailAdmin.getText().toString();
            password = edtPasswordAdmin.getText().toString();

            if (email.isEmpty() || password.isEmpty()){
                if(email.isEmpty()) showEdtError(edtEmailAdmin);
                if(password.isEmpty()) showEdtError(edtPasswordAdmin);
                return;
            }

            if (email.equals("admin") && password.equals("admin")){
//                saveDataToSharedPreference();
                Intent intent = new Intent(AdminLoginActivity.this, AdminAllProductsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finishAffinity();
            } else {
                tvLoginError.setVisibility(View.VISIBLE);
            }
        }));

        btnCancel.setOnClickListener((v -> finish()));


    }

    void saveDataToSharedPreference(){
        editor = preferences.edit();
        editor.putBoolean(UserModel.USER_LOGGED_IN, true);
        editor.apply();
        editor.commit();
    }

    void showEdtError(EditText target){
        target.setError("This field cannot be empty");
    }
}