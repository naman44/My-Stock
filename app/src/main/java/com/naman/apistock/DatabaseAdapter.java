package com.naman.apistock;

import com.naman.apistock.Model.Inventory;
import com.naman.apistock.Model.Product;
import com.naman.apistock.Model.RawMaterial;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Product.class, RawMaterial.class, Inventory.class}, version = 1)
public abstract class DatabaseAdapter extends RoomDatabase {

    public abstract ProductDao productDao();
    public abstract RawDao rawDao();
    public abstract InventoryDao inventoryDao();
}
