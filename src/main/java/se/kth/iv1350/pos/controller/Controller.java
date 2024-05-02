package se.kth.iv1350.pos.controller;

import se.kth.iv1350.pos.model.Receipt;
import se.kth.iv1350.pos.model.CashPayment;
import se.kth.iv1350.pos.integration.SystemCreator;
import se.kth.iv1350.pos.model.CashRegister;
import se.kth.iv1350.pos.integration.ItemDTO;
import se.kth.iv1350.pos.integration.ReceiptPrinter;
import se.kth.iv1350.pos.model.Sale;
import se.kth.iv1350.pos.util.Amount;


/**
 * This is the applications only controller.
 * All calls passing to the model goes through this class
 */
public class Controller {
    private SystemCreator systemCreator;
    private Sale sale;
    private CashRegister cashRegister;
    private ReceiptPrinter printer;
    
    
    /** Creates a new instance of controller
     * @param externalSystemCreator Initializes systems for accounting and inventory.
     * @param cashRegister The cash register used for processing payments.
     * @param printer The printer used for printing receipts.
     */
    public Controller(SystemCreator systemCreator, CashRegister cashRegister,ReceiptPrinter printer) {
        this.systemCreator = systemCreator;
    	systemCreator.getAccountingSystem();
        systemCreator.getInventorySystem();
    	this.cashRegister = cashRegister;
        this.printer = printer;
    }
    /**
     * Starts a new sale. 
     */
    public void makeNewSale() {
        this.sale = new Sale();  
    }
    
   

    /**
     *  Adds an item to the current sale after retrieving its details from the external inventory system.
     * @param itemIdentifier The unique identifier for the item.
     * @param quantity The quantity of the item to add.
     * @return The item details if found, otherwise null.
    
     */
    public ItemDTO enterItem(String itemIdentifier, Amount quantity) {
        ItemDTO foundItem = systemCreator.getInventorySystem().findItem(itemIdentifier);
        if (foundItem != null) {
            sale.getItem(foundItem, (int) quantity.getAmount());  
            return foundItem;
        } else {
            return null;
        }
    }

    
    /**
     * Processes payment for the items in the current sale, calculates change, updates systems, and prints the receipt.
     * @param paidAmount The amount of money paid by the customer.
     * @return The change to be returned to the customer.
     */
    public Amount payment(Amount amountPaid) {
        CashPayment payment = new CashPayment(amountPaid, sale.getTotalPriceIncludingVAT());
        Amount change = cashRegister.addPayment(payment);
        systemCreator.getInventorySystem().updateInventory(sale);
        systemCreator.getAccountingSystem().updateAccounting(sale);
        Receipt receipt = new Receipt(sale, payment);
        printer.printReceipt(receipt);
        return change;
    }
   
    
}

    
