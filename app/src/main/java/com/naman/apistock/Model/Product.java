package com.naman.apistock.Model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Product {

    @PrimaryKey(autoGenerate = true)
    private int productId;
    private String name;
    private String brandName;
    // type - HD/PET
    private String type;
    @Ignore
    private double price;
    private double weight;
    private double neckSize;
    private String color;

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getNeckSize() {
        return neckSize;
    }

    public void setNeckSize(double neckSize) {
        this.neckSize = neckSize;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Ignore
    public boolean compareObjects(Product p1, Product p2){
        if(p1.name.equalsIgnoreCase(p2.name) &&
            p1.brandName.equalsIgnoreCase(p2.brandName) &&
            p1.weight == p2.weight &&
            p1.neckSize == p2.neckSize &&
            p1.price == p2.price &&
            p1.type.equalsIgnoreCase(p2.type) &&
            p1.color.equalsIgnoreCase(p2.color)){
            return true;
        }
        return false;
    }
}
