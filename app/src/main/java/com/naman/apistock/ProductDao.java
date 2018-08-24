package com.naman.apistock;

import com.naman.apistock.Model.Product;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ProductDao {

    @Insert
    long insertProduct(Product product);

    @Query("select * from product")
    List<Product> fetchAllProducts();

    @Query("delete from product where productId=:id")
    int deleteProduct(int id);

    @Update
    int updateProduct(Product product);
}
