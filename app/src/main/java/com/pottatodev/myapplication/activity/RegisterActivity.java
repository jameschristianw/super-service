package com.pottatodev.myapplication.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pottatodev.myapplication.R;
import com.pottatodev.myapplication.helper.Config;
import com.pottatodev.myapplication.helper.H;
import com.pottatodev.myapplication.model.RegisterModel;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText edtRegisterUsername, edtRegisterEmail, edtRegisterPassword, edtRegisterConfirmPassword;
    Button btnRegister;
    H apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        apiInterface = Config.getClient().create(H.class);

        initializeViews();
    }

    void initializeViews(){
        ActionBar actionBar = this.getSupportActionBar();
//        actionBar.hide();

        edtRegisterUsername = findViewById(R.id.edtRegisterUsername);
        edtRegisterEmail = findViewById(R.id.edtRegisterEmail);
        edtRegisterPassword = findViewById(R.id.edtRegisterPassword);
        edtRegisterConfirmPassword = findViewById(R.id.edtRegisterConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener((v) -> {
            String email = edtRegisterEmail.getText().toString();
            String password = edtRegisterPassword.getText().toString();
            String confirmPassword = edtRegisterConfirmPassword.getText().toString();
            String username = edtRegisterUsername.getText().toString();

            if(isEmailValid(email)){
                if(password.equals(confirmPassword)){
                    register(username, email, password);
                }
                else {
                    edtRegisterPassword.setError("Password is not the same");
                    edtRegisterConfirmPassword.setError("Password is not the same");
//                    showToast("Please confirm your password", Toast.LENGTH_LONG);
                }
            } else {
                edtRegisterEmail.setError("Email is invalid");
            }
        });

        edtRegisterPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                edtRegisterConfirmPassword.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        edtRegisterConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                edtRegisterPassword.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    void register(String username, String email, String password){
        Call<ResponseBody> registerResponse = apiInterface.registerUser(new RegisterModel(email, username, password));
        registerResponse.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("RegisterActivity", String.valueOf(call.request().url()));
                Log.d("RegisterActivity", String.valueOf(response.code()));
                Log.d("RegisterActivity", String.valueOf(response.body()));
                if(response.isSuccessful()){
                    showToast("Register success! You can now login.", Toast.LENGTH_LONG);
                    finish();
                } else {
                    // TODO benerin error handling register
                    Log.d("RegisterActivity", String.valueOf(response));
                    Log.d("RegisterActivity", response.message());
                    Log.d("RegisterActivity", "Response is not successful");
                    // Temporary
                    showToast("Username or email already exist", Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                showToast("Please check your internet connection", Toast.LENGTH_LONG);
                t.printStackTrace();
                t.getCause();
                t.getLocalizedMessage();
                t.getMessage();
            }
        });
    }

    void showToast(String message, int length){
        Toast.makeText(RegisterActivity.this, message, length).show();
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}