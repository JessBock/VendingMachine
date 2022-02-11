package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class VendingMachine {
    private BigDecimal balance = BigDecimal.valueOf(0.00);
    private Map<String, Inventory> inventoryMap = new TreeMap<>();
    private final File LOG =  new File("C:\\Users\\Student\\workspace\\module-1-capstone-team-2\\19_20_Capstone\\capstone\\ExampleFiles\\Log.txt");
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss a");
    private DecimalFormat decimalFormat = new DecimalFormat("0.00");
    private int quarter = 0;
    private int dime = 0;
    private int nickel = 0;
    private BigDecimal totalSales;

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

    public File getLOG() {
        return LOG;
    }

    public int getQuarter(){
        return quarter;
    }

    public int getDime(){
        return dime;
    }

    public int getNickel(){
        return nickel;
    }

    public BigDecimal getTotalSales(){
        return totalSales;
    }


    // methods
    public BigDecimal feedMoney(int moneyAdded) {
        balance = balance.add(BigDecimal.valueOf(moneyAdded));
        System.out.println("Your remaining balance is $" + decimalFormat.format(balance) + ".");
        try (PrintWriter printWriter = new PrintWriter(new FileOutputStream(LOG, true))) {
            printWriter.println(LocalDateTime.now().format(dateTimeFormatter) + " FEED MONEY: $" + decimalFormat.format(BigDecimal.valueOf(moneyAdded)) + " $" + decimalFormat.format(balance));
        } catch (FileNotFoundException ex) {
            System.out.println("Log File Not Found.");
        }
        return balance;
    }

    public void selectProduct(String key) {
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
        try (PrintWriter printWriter = new PrintWriter(new FileOutputStream(LOG, true))) {
            printWriter.println(LocalDateTime.now().format(dateTimeFormatter) + " PURCHASED - " + inventoryMap.get(key).getName() + ": $" + decimalFormat.format(inventoryMap.get(key).getPrice()) + " $" + decimalFormat.format(balance));
        } catch (FileNotFoundException ex) {
            System.out.println("Log File Not Found.");
        }
        System.out.println("Your remaining balance is $" + decimalFormat.format(balance) + " .");
    }

    public void finishTransaction() {
        quarter = 0;
        dime = 0;
        nickel = 0;
        BigDecimal remainingBalance = balance;

        while (balance.compareTo(BigDecimal.ZERO) > 0) {
            if (balance.compareTo(BigDecimal.valueOf(0.25)) >= 0) {
                quarter++;
                balance = balance.subtract(BigDecimal.valueOf(0.25));
            } else if (balance.compareTo(BigDecimal.valueOf(0.10)) >= 0) {
                dime++;
                balance = balance.subtract(BigDecimal.valueOf(0.10));
            } else if (balance.compareTo(BigDecimal.valueOf(0.05)) >= 0) {
                nickel++;
                balance = balance.subtract(BigDecimal.valueOf(0.05));
            }
        }
        try (PrintWriter printWriter = new PrintWriter(new FileOutputStream(LOG, true))) {
            printWriter.println(LocalDateTime.now().format(dateTimeFormatter) + " GIVE CHANGE: $" + decimalFormat.format(remainingBalance) + " $" + decimalFormat.format(balance));
        } catch (FileNotFoundException ex) {
            System.out.println("Log File Not Found.");
        }
        System.out.println("$" + decimalFormat.format(remainingBalance) + " in " + quarter + " quarter(s), " + dime + " dime(s), and " + nickel + " nickel(s).");
    }

    public void printSalesReport(){
        totalSales = BigDecimal.ZERO;
        for (Map.Entry<String, Inventory> entry : inventoryMap.entrySet()) {
            System.out.println(entry.getValue().getName() + "|" + entry.getValue().getNumberPurchased());
            totalSales = totalSales.add((entry.getValue().getPrice()).multiply(BigDecimal.valueOf(entry.getValue().getNumberPurchased())));
        }
        System.out.println("\n**TOTAL SALES** $" + decimalFormat.format(totalSales));
    }
}
