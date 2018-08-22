package com.naman.apistock;

import com.naman.apistock.Model.Inventory;
import java.util.List;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface InventoryDao {

    @Insert
    void insertInventory(Inventory inventory);

    @Query("select * from inventory")
    List<Inventory> fetchAllInventory();

    @Query("select * from inventory where productId=:id")
    Inventory fetchInventoryById(int id);

    @Query("select * from inventory where productId is not null")
    Inventory fetchProductInventory();
}
