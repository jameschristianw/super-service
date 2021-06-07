package com.pottatodev.myapplication.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.pottatodev.myapplication.model.ProductModel;

import org.jetbrains.annotations.NotNull;

@Database(entities = {ProductModel.class}, version = 2, exportSchema = false)
public abstract class TransactionRoomDB extends RoomDatabase {
    public abstract TransactionDAO daoTransaction();

    private static TransactionRoomDB INSTANCE;

    static TransactionRoomDB getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TransactionRoomDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TransactionRoomDB.class, "dbTransaction").addCallback(sRoomDBCallBack).build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDBCallBack = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull @NotNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };
}
