package com.techelevator;

import com.techelevator.view.Menu;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
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
    private static final String SECRET_MENU_SALES_REPORT = "Print Sales Report";
    private static final String SECRET_MENU_AUDIT_LOG = "Print Audit Log";
    private static final String[] SECRET_MENU_OPTIONS = {SECRET_MENU_SALES_REPORT, SECRET_MENU_AUDIT_LOG, MAIN_MENU_OPTION_EXIT};
    private DecimalFormat decimalFormat = new DecimalFormat("0.00");


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
                    System.out.println(entry.getKey() + ": " + entry.getValue().getName() + " - $" + decimalFormat.format(entry.getValue().getPrice()));
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
            } else if (choice.equals(menu.getPassword())) {
                while (true) {
                    String secretChoice = (String) menu.getChoiceFromOptions(SECRET_MENU_OPTIONS);
                    if (secretChoice.equals(SECRET_MENU_AUDIT_LOG)) {
                        Scanner scanner = new Scanner(System.in);
                        System.out.println("Please enter log date (MM-DD-YYYY)");
                        String input = scanner.nextLine();

                        String filePath = ("C:\\Users\\Student\\workspace\\module-1-capstone-team-2\\19_20_Capstone\\capstone\\ExampleFiles\\Log-" + input + ".txt");
                        File logFile = new File(filePath);
                        if (logFile.exists()) {
                            try (Scanner fileScanner = new Scanner(logFile)) {
                                while (fileScanner.hasNextLine()) {
                                    String line = fileScanner.nextLine();
                                    System.out.println(line);
                                }
                            } catch (FileNotFoundException ex) {
                                System.out.println("Audit Log File Not Found");
                            }
                        }
                    } else if (secretChoice.equals(SECRET_MENU_SALES_REPORT)) {
                        vendingMachine.printSalesReport();

                    } else if (secretChoice.equals(MAIN_MENU_OPTION_EXIT)) {
                        //exit
                        break;

                    }
                }

            }

        }
    }

    public static void main(String[] args) {
        Menu menu = new Menu(System.in, System.out);
        VendingMachineCLI cli = new VendingMachineCLI(menu);
        cli.run();
    }
}
