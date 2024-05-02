package test.java.se.kth.iv1350.pos.model;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

import se.kth.iv1350.pos.integration.ItemDTO;
import se.kth.iv1350.pos.model.CashPayment;
import se.kth.iv1350.pos.model.CashRegister;
import se.kth.iv1350.pos.model.Receipt;
import se.kth.iv1350.pos.model.Sale;
import se.kth.iv1350.pos.util.Amount;

public class ReceiptTest {
    private Sale testSale;
    private CashRegister cashRegister;
    private Receipt receipt;

    @Before
    public void setUp() {
        cashRegister = new CashRegister();
        testSale = new Sale();
        testSale.getItem(new ItemDTO("Orange", 0.6, 15, "Fresh orange", "001"), 2);
        testSale.getItem(new ItemDTO("Banana", 0.6, 5, "Fresh banana", "002"), 3);
        CashPayment payment = new CashPayment(new Amount(100), testSale.getTotalPriceIncludingVAT());
        cashRegister.addPayment(payment);
        receipt = new Receipt(testSale, payment);
    }

    @After
    public void tearDown() {
        testSale = null;
        cashRegister = null;
        receipt = null;
    }

    @Test
    public void testReceiptIncludesOrangeDetails() {
        String receiptString = receipt.toString();
        assertTrue("Receipt should include details for Orange", receiptString.contains("Orange x2 - 15.00 sek each"));
    }

    @Test
    public void testReceiptIncludesBananaDetails() {
        String receiptString = receipt.toString();
        assertTrue("Receipt should include details for Banana", receiptString.contains("Banana x3 - 5.00 sek each"));
    }

    @Test
    public void testReceiptShowsTotalPriceIncludingVAT() {
        String receiptString = receipt.toString();
        double totalPrice = testSale.getTotalPriceIncludingVAT().getAmount();
        assertTrue("Receipt should show total price including VAT", receiptString.contains(String.format("Total Price (incl. VAT): %.2f sek", totalPrice)));
    }

    @Test
    public void testReceiptShowsFormattedTotalVAT() {
        String receiptString = receipt.toString();
        double vatValue = testSale.getTotalVAT();
        String expectedTotalVAT = String.format("Total VAT: %.2f sek", vatValue);
        assertTrue("Receipt should show correctly formatted total VAT", receiptString.contains(expectedTotalVAT));
    }

    @Test
    public void testReceiptShowsAmountPaid() {
        String receiptString = receipt.toString();
        assertTrue("Receipt should show amount paid", receiptString.contains("Amount Paid: 100.00 sek"));
    }

    @Test
    public void testReceiptShowsFormattedChange() {
        String receiptString = receipt.toString();
        Amount changeGiven = new Amount(100).minus(testSale.getTotalPriceIncludingVAT());
        String expectedChange = String.format("Change: %.2f sek", (double)changeGiven.getAmount());
        assertTrue("Receipt should show correctly formatted change", receiptString.contains(expectedChange));
    }
}
