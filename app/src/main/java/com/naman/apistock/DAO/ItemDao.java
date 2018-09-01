package com.naman.apistock.DAO;

import com.naman.apistock.Model.Item;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ItemDao {

    @Insert
    void insertItem(Item item);

    @Query("select * from item")
    List<Item> fetchAllItems();

    @Query("select * from item where itemType=:type")
    List<Item> fetchItemsByType(String type);

    @Query("delete from item where itemId=:id")
    void deleteItems(int id);

    @Update
    void updateItem(Item item);
}
