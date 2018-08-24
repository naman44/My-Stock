package com.naman.apistock.Model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class RawMaterial {

    @PrimaryKey(autoGenerate = true)
    private int rawId;
    private String name;
    private String type;

    @Ignore
    private double price;
    private String grade;
    private String manufacturer;

    public int getRawId() {
        return rawId;
    }

    public void setRawId(int rawId) {
        this.rawId = rawId;
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

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Ignore
    public boolean compareObjects(RawMaterial p1, RawMaterial p2){
        if(p1.name.equalsIgnoreCase(p2.name) &&
                p1.price == p2.price &&
                p1.grade.equalsIgnoreCase(p2.grade) &&
                p1.manufacturer.equalsIgnoreCase(p2.manufacturer) &&
                p1.type.equalsIgnoreCase(p2.type)){
            return true;
        }
        return false;
    }
}
