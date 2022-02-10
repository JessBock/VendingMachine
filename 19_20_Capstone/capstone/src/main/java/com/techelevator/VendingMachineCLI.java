package com.techelevator;

import com.techelevator.view.Menu;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Scanner;

public class VendingMachineCLI {

    private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
    private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
    private static final String MAIN_MENU_OPTION_EXIT = "Exit";
    private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT};
    private static final String FILE_PATH = "C:\\Users\\Student\\workspace\\module-1-capstone-team-2\\19_20_Capstone\\capstone\\ExampleFiles\\VendingMachine.txt";
    private static final String SUB_MENU_OPTION_FEED_MONEY = "Feed Money";
    private static final String SUB_MENU_OPTION_SELECT_PRODUCT = "Select Product";
    private static final String SUB_MENU_OPTION_FINISH_TRANSACTION = "Finish Transaction";
    private static final String[] SUB_MENU_OPTIONS = {SUB_MENU_OPTION_FEED_MONEY, SUB_MENU_OPTION_SELECT_PRODUCT, SUB_MENU_OPTION_FINISH_TRANSACTION};


    private Menu menu;

    public VendingMachineCLI(Menu menu) {
        this.menu = menu;
    }

    public void run() {
        while (true) {
            VendingMachine vendingMachine = new VendingMachine(FILE_PATH);

            String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);
            if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
                // display vending machine items
                for (Map.Entry<String, Inventory> entry : vendingMachine.getInventoryMap().entrySet()) {
                    if (entry.getValue().getNumberRemaining() <= 0) {
                        System.out.println(entry.getKey() + ": " + entry.getValue().getName() + " - Sold Out");
                    }
                    System.out.println(entry.getKey() + ": " + entry.getValue().getName() + " - $" + entry.getValue().getPrice());
                }

            } else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
                while (true) {
                    String subChoice = (String) menu.getChoiceFromOptions(SUB_MENU_OPTIONS);
                    Scanner purchaseScanner = new Scanner(System.in);

                    if (subChoice.equals(SUB_MENU_OPTION_FEED_MONEY)) {
                        System.out.println("Enter Amount to Add:");
                        int amountToAdd = Integer.parseInt(purchaseScanner.nextLine());
                        vendingMachine.feedMoney(amountToAdd);
                    } else if (subChoice.equals(SUB_MENU_OPTION_SELECT_PRODUCT)) {
                        if (vendingMachine.getBalance().equals(BigDecimal.ZERO)) {
                            System.out.println("Please deposit money before making a selection.");
                        } else {
                            System.out.println("Enter Selection");
                            String inputSelection = purchaseScanner.nextLine();
                            vendingMachine.selectProduct(inputSelection);
                        }
                    } else if (subChoice.equals(SUB_MENU_OPTION_FINISH_TRANSACTION)) {
                        vendingMachine.finishTransaction();
                        break;
                    }
                }
                // do purchase
            } else if (choice.equals(MAIN_MENU_OPTION_EXIT)) {
                //exit
                break;
            }
        }
    }

    public static void main(String[] args) {
        Menu menu = new Menu(System.in, System.out);
        VendingMachineCLI cli = new VendingMachineCLI(menu);
        cli.run();
    }
}
