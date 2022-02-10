package com.techelevator;

import com.techelevator.Inventory;

import java.math.BigDecimal;

public class Candy extends Inventory {

    //constructor
    public Candy(String name, BigDecimal price) {
        super(name, price);
    }

    //method
    public String purchased() {
        return "Munch Munch, Yum!";
    }
}
