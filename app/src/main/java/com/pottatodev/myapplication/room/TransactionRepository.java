package com.pottatodev.myapplication.room;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.pottatodev.myapplication.model.ProductModel;

import java.util.List;

public class TransactionRepository {
    private TransactionDAO daoTransaction;

    private LiveData<List<ProductModel>> transactionList;

    public TransactionRepository(Application app) {
        TransactionRoomDB db = TransactionRoomDB.getDatabase(app);
        daoTransaction = db.daoTransaction();
        transactionList = daoTransaction.getAllTransaction();
    }

    LiveData<List<ProductModel>> getTransactionList() {
        return this.transactionList;
    }

    public void insert(ProductModel product){
        new insertAsyncTask(daoTransaction).execute(product);
    }

    public void deleteAll(){
        new deleteAllAsyncTask(daoTransaction).execute();
    }

    public void delete(ProductModel product){
        new deleteAsyncTask(daoTransaction).execute(product);
    }

    public void update(ProductModel product){
        new updateAsyncTask(daoTransaction).execute(product);
    }

    private static class insertAsyncTask extends AsyncTask<ProductModel, Void, Void> {
        private TransactionDAO asyncDaoTransaction;

        insertAsyncTask(TransactionDAO dao){
            this.asyncDaoTransaction = dao;
        }

        @Override
        protected Void doInBackground(ProductModel... productModels) {
            asyncDaoTransaction.insert(productModels[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<ProductModel, Void, Void> {
        private TransactionDAO asyncDaoTransaction;

        deleteAsyncTask(TransactionDAO dao){
            this.asyncDaoTransaction = dao;
        }

        @Override
        protected Void doInBackground(ProductModel... productModels) {
            asyncDaoTransaction.delete(productModels[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<ProductModel, Void, Void> {
        private TransactionDAO asyncDaoTransaction;

        updateAsyncTask(TransactionDAO dao){
            this.asyncDaoTransaction = dao;
        }

        @Override
        protected Void doInBackground(ProductModel... productModels) {
            asyncDaoTransaction.update(productModels[0]);
            return null;
        }
    }


    private static class deleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private TransactionDAO asyncDaoTransaction;

        deleteAllAsyncTask(TransactionDAO dao){
            this.asyncDaoTransaction = dao;
        }

        @Override
        protected Void doInBackground(final Void... voids) {
            asyncDaoTransaction.deleteAll();
            return null;
        }
    }
}
