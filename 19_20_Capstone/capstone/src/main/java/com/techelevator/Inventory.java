package com.techelevator;

import java.math.BigDecimal;

public abstract class Inventory {

    private String name;
    private BigDecimal price;
    private int numberRemaining = 5;
    private int numberPurchased = 0;

    //constructor

    public Inventory(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    //getters

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getNumberRemaining() {
        return numberRemaining;
    }

    public int getNumberPurchased() {
        return numberPurchased;
    }

    //setters

    public void setNumberRemaining(int numberRemaining) {
        this.numberRemaining = numberRemaining;
    }

    public void setNumberPurchased(int numberPurchased) {
        this.numberPurchased = numberPurchased;
    }

    //method

    public abstract String purchased();

    public void decrementNumberRemaining() {
        numberRemaining --;
    }

    public void incrementNumberPurchased() {
        numberPurchased ++;
    }

}
