package com.techelevator;

import java.math.BigDecimal;

public class Chip extends Inventory {
    //constructor

    public Chip(String name, BigDecimal price) {
        super(name, price);
    }

    //method

    public String purchased() {
        String sound = "Crunch Crunch, Yum!";
        return sound;
    }

}
