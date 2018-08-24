package com.naman.apistock.Model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity (indices = {@Index(value ="productId"),
                    @Index(value = "rawId")},
        foreignKeys = {@ForeignKey(entity = Product.class, parentColumns = "productId", childColumns = "productId", onDelete = CASCADE),
                        @ForeignKey(entity = RawMaterial.class, parentColumns = "rawId", childColumns = "rawId", onDelete = CASCADE)})
public class Inventory {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private Integer productId;
    private Integer rawId;
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

    public Integer getRawId() {
        return rawId;
    }

    public void setRawId(Integer rawId) {
        this.rawId = rawId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
