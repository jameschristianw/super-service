package com.pottatodev.myapplication.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pottatodev.myapplication.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    EditText edtLoginEmail, edtLoginPassword;
    Button btnLogin, btnLoginRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = this.getSupportActionBar();
//        actionBar.hide();

        edtLoginEmail = findViewById(R.id.edtLoginEmail);
        edtLoginPassword = findViewById(R.id.edtLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLoginRegister = findViewById(R.id.btnLoginRegister);

        btnLogin.setOnClickListener((v) ->  {
            String email, password;
            email = edtLoginEmail.getText().toString();
            password = edtLoginPassword.getText().toString();

//            if(email.isEmpty() && password.isEmpty()){
//                Log.d("LoginActivity", "Both email and password are empty");
//                Toast.makeText(LoginActivity.this, "Email and password are invalid", Toast.LENGTH_LONG).show();
//                return;
//            }
//            if(email.isEmpty()){
//                Log.d("LoginActivity", "Email is invalid");
//                Toast.makeText(LoginActivity.this, "Email is invalid", Toast.LENGTH_LONG).show();
//                return;
//            }
//            if(password.isEmpty()){
//                Log.d("LoginActivity", "Password is invalid");
//                Toast.makeText(LoginActivity.this, "Password is invalid", Toast.LENGTH_LONG).show();
//                return;
//            }
//
//            if(isEmailValid(email)){
//                Toast.makeText(LoginActivity.this, "Loggin In", Toast.LENGTH_LONG).show();
//
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
//            } else{
//                edtLoginEmail.setError("Please enter a correct email");
//            }

            // Biar cepet
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        btnLoginRegister.setOnClickListener((v) -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}