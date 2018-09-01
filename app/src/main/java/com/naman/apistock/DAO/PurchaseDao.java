package com.naman.apistock.DAO;

import com.naman.apistock.Model.Purchase;

import java.util.Date;
import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface PurchaseDao {

    @Insert
    long insertPurchase(Purchase purchase);

    @Query("select * from purchase")
    List<Purchase> fetchAllPurchase();

    @Query("select * from purchase where id=:id")
    Purchase fetchPurchaseById(int id);

    @Query("select * from purchase where date=:date")
    List<Purchase> fetchPurchaseByDate(String date);

    @Query("select * from purchase where date between :start and :end")
    List<Purchase> fetchPurchaseByDateRange(String start, String end);

    @Update
    int updatePurchase(Purchase purchase);

    @Delete
    int deletePurchase(Purchase purchase);
}
