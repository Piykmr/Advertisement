package com.example.advertisment;

import com.google.firebase.database.DataSnapshot;

public class BagModel {

    String bag_Price,bag_Name;
    DataSnapshot bag_Image;
    public BagModel(){}
    public BagModel(String bag_Name, String bag_Price, DataSnapshot bag_Image) {
        this.bag_Name = bag_Name;
        this.bag_Price = bag_Price;
        this.bag_Image = bag_Image;
    }

    public String getBag_Name() {
        return bag_Name;
    }

    public void setBag_Name(String bag_Name) {
        this.bag_Name = bag_Name;
    }

    public String getBag_Price() {
        return bag_Price;
    }

    public void setBag_Price(String bag_Price) {
        this.bag_Price = bag_Price;
    }

    public DataSnapshot getBag_Image() {
        return bag_Image;
    }

    public void setBag_Image(DataSnapshot bag_Image) {
        this.bag_Image = bag_Image;
    }
}
