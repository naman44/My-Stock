package com.naman.apistock;

import com.naman.apistock.Model.Product;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface ProductDao {

    @Insert
    long insertProduct(Product product);

    @Query("select * from product")
    List<Product> fetchAllProducts();
}
