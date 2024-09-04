package com.example.adminpanel;

public class orderdibo {
    String address,phone,name,price,profileimage;

    public orderdibo() {
    }

    public orderdibo(String name,String address, String phone, String price, String profileimage) {
        this.name = name;
        this.price = price;
        this.profileimage = profileimage;
        this.address=address;
        this.phone = phone;

    }

    public String getname() {
        return name;
    }

    public String getprice() {
        return price;
    }

    public String getprofileimage() {
        return profileimage;
    }
    public String getphone(){return phone;}

    public String getaddress(){return address;}
}
