package com.techelevator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class VendingMachineTest {

    private VendingMachine vendingMachine;

    @Before
    public void setup() {
        String filePath = "C:\\Users\\Student\\workspace\\module-1-capstone-team-2\\19_20_Capstone\\capstone\\ExampleFiles\\VendingMachine.txt";
        vendingMachine = new VendingMachine(filePath);
    }

    @Test
    public void create_inventory_using_constructor() {

        Assert.assertEquals("Potato Crisps", vendingMachine.getInventoryMap().get("A1").getName());
        Assert.assertEquals(new BigDecimal("3.05"), vendingMachine.getInventoryMap().get("A1").getPrice());
        Assert.assertEquals("Moonpie", vendingMachine.getInventoryMap().get("B1").getName());
        Assert.assertEquals(new BigDecimal("1.80"), vendingMachine.getInventoryMap().get("B1").getPrice());
        Assert.assertEquals("Cola", vendingMachine.getInventoryMap().get("C1").getName());
        Assert.assertEquals(new BigDecimal("1.25"), vendingMachine.getInventoryMap().get("C1").getPrice());
        Assert.assertEquals("U-Chews", vendingMachine.getInventoryMap().get("D1").getName());
        Assert.assertEquals(new BigDecimal("0.85"), vendingMachine.getInventoryMap().get("D1").getPrice());

    }

    @Test
    public void feedMoney_works_with_expected_value() {

        vendingMachine.feedMoney(5);

        Assert.assertEquals(new BigDecimal("5.0"), vendingMachine.getBalance());


    }

    @Test
    public void selectProduct_works_with_expected_value() {

        vendingMachine.feedMoney(5);
        vendingMachine.selectProduct("B2");

        Assert.assertEquals(new BigDecimal("3.50"), vendingMachine.getBalance());
        Assert.assertEquals(1, vendingMachine.getInventoryMap().get("B2").getNumberPurchased());
        Assert.assertEquals(4, vendingMachine.getInventoryMap().get("B2").getNumberRemaining());
    }

    @Test
    public void finishTransaction_balance_goes_to_zero() {

        vendingMachine.feedMoney(5);
        vendingMachine.selectProduct("A1");
        vendingMachine.finishTransaction();

        Assert.assertEquals(new BigDecimal("0.00"), vendingMachine.getBalance());
        Assert.assertEquals(7, vendingMachine.getQuarter());
        Assert.assertEquals(2, vendingMachine.getDime());
        Assert.assertEquals(0, vendingMachine.getNickel());

    }

    @Test
    public void salesReport_Total_is_accurate() {

        vendingMachine.feedMoney(20);
        vendingMachine.selectProduct("B3");
        vendingMachine.selectProduct("A1");
        vendingMachine.selectProduct("D2");
        vendingMachine.finishTransaction();
        vendingMachine.printSalesReport();

        Assert.assertEquals(new BigDecimal("5.50"), vendingMachine.getTotalSales());

    }
}
