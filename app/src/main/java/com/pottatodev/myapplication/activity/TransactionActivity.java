package com.pottatodev.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.pottatodev.myapplication.R;
import com.pottatodev.myapplication.adapter.TransactionAdapter;
import com.pottatodev.myapplication.model.ProductModel;
import com.pottatodev.myapplication.room.TransactionViewModel;

import java.util.List;

public class TransactionActivity extends AppCompatActivity {

    RecyclerView rvTransactionList;
    TransactionAdapter adapter;

    TransactionViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        rvTransactionList = findViewById(R.id.rvTransactionList);
        rvTransactionList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TransactionAdapter(TransactionActivity.this);
        rvTransactionList.setAdapter(adapter);

//        viewModel = ViewModelProviders.of(this).get(TransactionViewModel.class);
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(TransactionViewModel.class);
        viewModel.getTransactionList().observe(this, new Observer<List<ProductModel>>() {
            @Override
            public void onChanged(List<ProductModel> productModels) {
                adapter.setTransactions(productModels);
                adapter.notifyDataSetChanged();
            }
        });
    }
}