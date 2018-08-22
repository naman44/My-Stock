package com.naman.apistock;

import android.content.Context;

import com.naman.apistock.Model.Inventory;
import com.naman.apistock.Model.Product;
import com.naman.apistock.Model.RawMaterial;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Product.class, RawMaterial.class, Inventory.class}, version = 1)
public abstract class DatabaseAdapter extends RoomDatabase {

    private static DatabaseAdapter dbAdapter;
    public abstract ProductDao productDao();
    public abstract RawDao rawDao();
    public abstract InventoryDao inventoryDao();

    public static DatabaseAdapter getInstance(Context context){
        if(dbAdapter == null){
            synchronized (context){
                dbAdapter = Room.databaseBuilder(context,
                        DatabaseAdapter.class, "sample-db").fallbackToDestructiveMigration().build();
            }
        }
        return dbAdapter;
    }
}
