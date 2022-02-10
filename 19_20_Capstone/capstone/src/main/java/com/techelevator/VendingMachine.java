package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class VendingMachine {
    private BigDecimal balance = BigDecimal.valueOf(0.00);
    private Map<String, Inventory> inventoryMap = new TreeMap<>();


    //constructor
    public VendingMachine(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            try (Scanner fileScanner = new Scanner(file);) {
                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    String[] lineArray = line.split("\\|");
                        if (lineArray[3].equalsIgnoreCase("Chip")) {
                           // String chipName = lineArray[1];
                            BigDecimal chipPrice = new BigDecimal(lineArray[2]);
                            Chip chip = new Chip(lineArray[1], chipPrice);
                            inventoryMap.put(lineArray[0], chip);
                        } else if (lineArray[3].equalsIgnoreCase("Drink")) {
                            BigDecimal drinkPrice = new BigDecimal(lineArray[2]);
                            Drink drink = new Drink(lineArray[1], drinkPrice);
                            inventoryMap.put(lineArray[0], drink);
                        } else if (lineArray[3].equalsIgnoreCase("Candy")) {
                            BigDecimal candyPrice = new BigDecimal(lineArray[2]);
                            Candy candy = new Candy(lineArray[1], candyPrice);
                            inventoryMap.put(lineArray[0], candy);
                        } else if (lineArray[3].equalsIgnoreCase("Gum")) {
                            BigDecimal gumPrice = new BigDecimal(lineArray[2]);
                            Gum gum = new Gum(lineArray[1], gumPrice);
                            inventoryMap.put(lineArray[0], gum);
                        }
                }
            } catch (FileNotFoundException ex) {
                System.out.println("Vending.txt File Not Found");
            }
        }
    }

    // getters
    public BigDecimal getBalance() {
        return balance;
    }

    public Map<String, Inventory> getInventoryMap() {
        return inventoryMap;
    }

    // methods
    public BigDecimal feedMoney(int moneyAdded) {
        balance = balance.add(BigDecimal.valueOf(moneyAdded));
        return balance;
    }
    public void selectProduct(String key) {
        if (balance.equals(0.00)) {
            System.out.println("Please deposit money before making a selection.");
        }
        if (inventoryMap.get(key).getNumberRemaining() > 0 && (balance.compareTo(inventoryMap.get(key).getPrice())) >= 0) {
            balance = balance.subtract(inventoryMap.get(key).getPrice());
            System.out.println(inventoryMap.get(key).purchased());
            inventoryMap.get(key).decrementNumberRemaining();
            inventoryMap.get(key).incrementNumberPurchased();
        } else if (inventoryMap.get(key).getNumberRemaining() <= 0) {
            System.out.println("Sold Out");
        } else if (balance.compareTo(inventoryMap.get(key).getPrice()) < 0) {
            System.out.println("Please Add Additional Funds");
        }
    }

    public void finishTransaction() {
        int quarter = 0;
        int dime = 0;
        int nickle = 0;
        BigDecimal remainingBalance = balance;

        while (balance.compareTo(BigDecimal.ZERO) > 0) {
            if (balance.compareTo(BigDecimal.valueOf(0.25)) >= 0) {
                quarter ++;
               balance = balance.subtract(BigDecimal.valueOf(0.25));
            } else if (balance.compareTo(BigDecimal.valueOf(0.10)) >= 0) {
                dime ++;
                balance = balance.subtract(BigDecimal.valueOf(0.10));
            } else if (balance.compareTo(BigDecimal.valueOf(0.05)) >= 0) {
                nickle ++;
                balance = balance.subtract(BigDecimal.valueOf(0.05));
            }
        }
        System.out.println("$" + remainingBalance + " in " + quarter + " quarter(s), " + dime + " dime(s), and " + nickle + " nickle(s).");
    }
}
