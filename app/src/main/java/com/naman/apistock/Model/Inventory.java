package com.naman.apistock.Model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity (indices = @Index(value ="productId"),
        foreignKeys = @ForeignKey(entity = Item.class, parentColumns = "itemId", childColumns = "productId", onDelete = CASCADE))
public class Inventory {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private Integer productId;
    private int quantity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
