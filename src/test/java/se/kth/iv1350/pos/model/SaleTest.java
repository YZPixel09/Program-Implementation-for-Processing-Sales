package test.java.se.kth.iv1350.pos.model;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDate;

import se.kth.iv1350.pos.integration.ItemDTO;
import se.kth.iv1350.pos.model.CashPayment;
import se.kth.iv1350.pos.model.CashRegister;
import se.kth.iv1350.pos.model.Sale;
import se.kth.iv1350.pos.util.Amount;

public class SaleTest {
    private Sale sale;
    private CashRegister cashRegister;

    @Before
    public void setUp() {
        sale = new Sale();
        cashRegister = new CashRegister();
    }

    @After
    public void tearDown() {
        sale = null;
        cashRegister = null;
    }

    @Test
    public void testAddNewItem() {
        ItemDTO newItem = new ItemDTO("Grapes", 0.06, 30, "Fresh Green Grapes", "001");
        int quantity = 2;
        sale.getItem(newItem, quantity);
        assertEquals("Failed to add new item.", 2, sale.getQuantityOfItem("001"));
    }

    @Test
    public void testAddExistingItem() {
        ItemDTO newItem = new ItemDTO("Mushrooms", 0.06, 15, "Fresh Wild Mushrooms", "002");
        sale.getItem(newItem, 1);
        sale.getItem(newItem, 3);
        assertEquals("Failed to update quantity of existing item.", 4, sale.getQuantityOfItem("002"));
    }

    @Test
    public void testPaymentAndChange() {
        ItemDTO item = new ItemDTO("Grapes", 0.06, 30, "Fresh Green Grapes", "001");
        sale.getItem(item, 1);  // Add item to the sale
        Amount totalCost = sale.getTotalPriceIncludingVAT();  // Total cost including VAT
        Amount paidAmount = new Amount(50);  // Amount paid by the customer
        CashPayment payment = new CashPayment(paidAmount, totalCost);
        Amount change = cashRegister.addPayment(payment);  // Process the payment
        assertEquals("Incorrect change calculation.", 20, change.getAmount());  // Verify the change returned
    }

    
    @Test
    public void testGetDate() {
        Sale sale = new Sale();
        LocalDate expectedDate = LocalDate.now();
        LocalDate actualDate = sale.getStartSaleDate();
        
        assertEquals("The date should be today's date", expectedDate, actualDate);
    }
}
