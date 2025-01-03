package service.core;

import java.io.Serializable;


public class Hotel implements Serializable {
    public String name;
    public String address;
    public int rating;
    public double price;

    public Hotel(String name, String address, int rating, double price ) {
        this.name = name;
        this.address = address;
        this.rating = rating;
        this.price = price;
    }
    public Hotel() {}//empty or not?

    public String getName() {
        return name;
     }
     public void setName(String name) {
        this.name = name;
     }
    @Override
    public String toString() {
        return "Hotel {name="+name+",address="+address+",rating="+rating+",price="+price+"}";
    }
}

