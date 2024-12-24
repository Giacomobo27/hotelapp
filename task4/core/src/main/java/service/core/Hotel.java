package service.core;

import java.io.Serializable;


public class Hotel implements Serializable {
    String name;
    String address;
    int rating;
    int reviews;
    double price
    public Hotel(String name,String address, int rating,int reviews, double price ) {
        this.name = name;
        this.address = address;
        this.rating = rating;
        this.reviews = reviews;
        this.price = price,

    }
    public Hotel() {}
}

