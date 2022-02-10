package com.techelevator;

import com.techelevator.Inventory;

import java.math.BigDecimal;

public class Gum extends Inventory {
    //constructor
    public Gum(String name, BigDecimal price) {
        super(name, price);
    }

    //method
    public String purchased() {
        return "Chew Chew, Yum!";
    }

}
