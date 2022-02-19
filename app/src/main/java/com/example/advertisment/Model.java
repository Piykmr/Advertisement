package com.example.advertisment;

public class Model {
    String name,email,address,phone,design;
    int image;


    public Model(String name, String email, String address, String phone, String design,int image) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.design = design;
        this.image = image;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesign() { return design; }

    public void setDesign(String design) { this.design = design; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getImage() { return image; }

    public void setImage(int image) {
        this.image = image;
    }
}
