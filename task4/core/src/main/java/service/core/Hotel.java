package service.core;

import java.io.Serializable;


public class Hotel implements Serializable {
    String name;
    int rating;
    int reviews;
    double price
    public Hotel(String name, int rating,int reviews, double price ) {
        this.name = name;
        this.rating = rating;
        this.reviews = reviews;
        this.price = price
    }
}

