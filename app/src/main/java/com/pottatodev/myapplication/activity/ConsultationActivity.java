package com.pottatodev.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.pottatodev.myapplication.R;
import com.pottatodev.myapplication.helper.Config;
import com.pottatodev.myapplication.helper.H;
import com.pottatodev.myapplication.model.ConsultationModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConsultationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinnerConsultVehicle;
    Button btnSendConsult;
    EditText edtConsultTitle, edtConsultDesc;

    H apiInterface;

    String title, description, vehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation);

        this.setTitle("Consultation");

        apiInterface = Config.getClient().create(H.class);

        initViews();
    }

    void initViews(){
        edtConsultTitle = findViewById(R.id.edtConsultTitle);
        edtConsultDesc = findViewById(R.id.edtConsultDesc);

        spinnerConsultVehicle = findViewById(R.id.spinnerConsultVehicle);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.vehicle_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerConsultVehicle.setAdapter(adapter);

        btnSendConsult = findViewById(R.id.btnSendConsult);
        btnSendConsult.setOnClickListener((v) -> {
            title = edtConsultTitle.getText().toString();
            description = edtConsultDesc.getText().toString();
            vehicle = spinnerConsultVehicle.getSelectedItem().toString();

            Log.d("ConsultationActivity", vehicle + " " + description  + " " + title);
            sendConsult();
        });
    }

    void sendConsult(){
        Call<ConsultationModel> sendConsult = apiInterface.createConsultation(title, description, vehicle);
        sendConsult.enqueue(new Callback<ConsultationModel>() {
            @Override
            public void onResponse(Call<ConsultationModel> call, Response<ConsultationModel> response) {
                Log.d("ConsultationActivity", String.valueOf(response.code()));
                Log.d("ConsultationActivity", String.valueOf(response.body()));
                switch (response.code()){
                    case 201:
                        showToast("Consultation has been sent");
                        setResult(200);
                        finish();
                        break;
                    case 400:
                        break;
                    default:
                        showToast("Something went wrong");
                }
            }

            @Override
            public void onFailure(Call<ConsultationModel> call, Throwable t) {
                showToast("Check your internet connection");
            }
        });
    }

    void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        vehicle = parent.getSelectedItem().toString();
        Log.d("ConsultationActivity", "onItemSelected " + vehicle);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Log.d("ConsultationActivity", "onNothingSelected " + vehicle);
        vehicle = "";
    }
}