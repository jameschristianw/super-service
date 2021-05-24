package com.pottatodev.myapplication.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.pottatodev.myapplication.R;
import com.pottatodev.myapplication.helper.Config;
import com.pottatodev.myapplication.helper.H;
import com.pottatodev.myapplication.model.UserModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText edtLoginEmail, edtLoginPassword;
    Button btnLogin, btnLoginRegister;

    H apiInterface;

    UserModel currentUser;

    LinearLayout llLoginForm;
    ProgressBar pbLoading;

    SharedPreferences preferences;
    String prefName;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        prefName = LoginActivity.this.getPackageName();
        preferences = getSharedPreferences(prefName, MODE_PRIVATE);

        apiInterface = Config.getClient().create(H.class);

        initializeViews();
    }

    void initializeViews(){
        llLoginForm = findViewById(R.id.llLoginForm);
        pbLoading = findViewById(R.id.pbLoading);

        llLoginForm.setVisibility(View.GONE);

        // Cek klo udh login
        boolean isLoggedIn = preferences.getBoolean(UserModel.USER_LOGGED_IN, false);
        if (isLoggedIn){
            moveToMainPage();
        }

        llLoginForm.setVisibility(View.VISIBLE);
        pbLoading.setVisibility(View.GONE);

        edtLoginEmail = findViewById(R.id.edtLoginEmail);
        edtLoginPassword = findViewById(R.id.edtLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLoginRegister = findViewById(R.id.btnLoginRegister);

        btnLogin.setOnClickListener((v) ->  {
            String email, password;
            email = edtLoginEmail.getText().toString();
            password = edtLoginPassword.getText().toString();

            if(email.isEmpty()){
                edtLoginEmail.setError("Email cannot be empty");
            }
            if(password.isEmpty()){
                edtLoginPassword.setError("Password cannot be empty");
            }

            if(isEmailValid(email)){
                Toast.makeText(LoginActivity.this, "Logging In", Toast.LENGTH_LONG).show();
                login(email, password);
            } else{
                edtLoginEmail.setError("Email is not valid");
            }
        });

        btnLoginRegister.setOnClickListener((v) -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    void moveToMainPage(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    void login(String email, String password){
        Call<UserModel> userResponse = apiInterface.loginUser(email, password);
        userResponse.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                Log.d("LoginActivity", String.valueOf(response.code()));
                Log.d("LoginActivity", String.valueOf(response.message()));
                Log.d("LoginActivity", String.valueOf(response.errorBody()));
                Log.d("LoginActivity", String.valueOf(call.request().url()));

                Log.d("LoginActivity", String.valueOf(response.code()));
                switch (response.code()) {
                    case 200:
                        currentUser = response.body();
                        saveDataToSharedPreference();
                        moveToMainPage();
                        break;
                    case 400:
                        showToast("Ensure this field has at least 6 characters.");
                    case 401:
                        showToast("Invalid credentials, please try again");
                        break;
                    default:
                        showToast("Something went wrong, please try again later");

                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                t.printStackTrace();
                t.getCause();
                showToast("Please check your internet connection");
            }
        });
    }

    void saveDataToSharedPreference(){
        editor = preferences.edit();
        editor.putBoolean(UserModel.USER_LOGGED_IN, true);
        editor.putString(UserModel.USER_EMAIL, currentUser.getEmail());
        editor.putString(UserModel.USER_USERNAME, currentUser.getUsername());
        editor.putString(UserModel.USER_ACCESS_TOKEN, currentUser.getTokens().getAccess());
        editor.putString(UserModel.USER_REFRESH_TOKEN, currentUser.getTokens().getRefresh());
        editor.apply();
        editor.commit();
    }

    void showToast(String message){
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}