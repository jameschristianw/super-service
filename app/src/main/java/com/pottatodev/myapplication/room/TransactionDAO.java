package com.pottatodev.myapplication.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.pottatodev.myapplication.model.ProductModel;

import java.util.List;

@Dao
public interface TransactionDAO {
    @Query("SELECT * FROM tableTransaction")
    LiveData<List<ProductModel>> getAllTransaction();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(ProductModel product);

    @Delete
    void delete(ProductModel product);

    @Update
    void update(ProductModel product);

    @Query("DELETE FROM tableTransaction")
    void deleteAll();
}
