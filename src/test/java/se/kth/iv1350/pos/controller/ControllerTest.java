package test.java.se.kth.iv1350.pos.controller;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import se.kth.iv1350.pos.controller.Controller;
import se.kth.iv1350.pos.integration.SystemCreator;
import se.kth.iv1350.pos.integration.ItemDTO;
import se.kth.iv1350.pos.integration.ReceiptPrinter;
import se.kth.iv1350.pos.model.CashRegister;
import se.kth.iv1350.pos.util.Amount;

public class ControllerTest {
     private Controller controller;
     private SystemCreator systemCreator;
     private ReceiptPrinter printer;
     private CashRegister cashRegister;

    @BeforeEach
    public void setUp() {
        systemCreator = new SystemCreator();
        printer = new ReceiptPrinter();
        cashRegister = new CashRegister();
        controller = new Controller(systemCreator, cashRegister, printer);
        controller.makeNewSale(); // Start a new sale for each test
    }

    @AfterEach
    public void tearDown() {
         controller = null;
         systemCreator = null;
         printer = null;
         cashRegister = null;
    } 

    @Test
    public void testMakeNewSale() {
         assertNotNull(controller, "Controller should not be null after setup");
    }

    @Test
    public void testEnterItemInvalid() {
        ItemDTO item = controller.enterItem("invalidId", new Amount(1));
        assertNull(item, "Entering an invalid item identifier should return null.");
    }

    @Test
    public void testPayment() {
        controller.enterItem("1234", new Amount(1));
        Amount amountPaid = new Amount(100);
        Amount change = controller.payment(amountPaid);
        assertNotNull(change, "Payment should calculate and return change correctly.");
        assertTrue(change.getAmount() >= 0, "Change should not be negative.");
    }

    @Test
    public void testPaymentExactAmount() {
        controller.enterItem("1234", new Amount(1));
        Amount amountPaid = new Amount(100); // Assume this matches the total cost
        Amount change = controller.payment(amountPaid);
        assertNotNull(change, "When paid amount equals the total price, change should not be null.");
    }
    
}
