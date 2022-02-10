package com.techelevator.view;

import com.techelevator.Inventory;

import java.math.BigDecimal;

public class Candy extends Inventory {
    private final String TYPE = "Candy";


    //constructor
    public Candy(String name, BigDecimal price, String type) {
        super(name, price);
    }



    //method
    public String purchased() {
        return "Munch, Munch, Yum!";
    }
}
