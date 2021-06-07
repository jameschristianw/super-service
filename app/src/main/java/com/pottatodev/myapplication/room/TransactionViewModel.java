package com.pottatodev.myapplication.room;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.pottatodev.myapplication.model.ProductModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TransactionViewModel extends AndroidViewModel {
    private TransactionRepository repository;
    private LiveData<List<ProductModel>> transactions;

    public TransactionViewModel(@NonNull @NotNull Application application) {
        super(application);

        repository = new TransactionRepository(application);
        transactions = repository.getTransactionList();
    }

    public LiveData<List<ProductModel>> getTransactionList(){
        return this.transactions;
    }

    public void insert(ProductModel product){
        repository.insert(product);
    }

    public void delete(ProductModel product){
        repository.delete(product);
    }

    public void update(ProductModel product){
        repository.update(product);
    }

    public void deleteAll(){
        repository.deleteAll();
    }
}
