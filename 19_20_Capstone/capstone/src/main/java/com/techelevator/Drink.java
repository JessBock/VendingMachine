package com.techelevator;

import java.math.BigDecimal;

public class Drink extends Inventory {

    //constructor

    public Drink(String name, BigDecimal price) {
        super(name, price);
    }

    //method

    public String purchased() {
        String sound = "Glug Glug, Yum!";
        return sound;
    }
}
