package se.kth.iv1350.pos.view;

import se.kth.iv1350.pos.controller.Controller;
import se.kth.iv1350.pos.integration.ItemDTO;
import se.kth.iv1350.pos.util.Amount;

/**
 * Placeholder for the real view. Hardcoded with 
 * calls to all system operations in the controller.
 * @author charl
 */
public class View {
    private Controller controller;
    /**
     * Creates a new instance.
     * @param contr , The controller used for all calls to other layers.
     */
    public View(Controller contr){
        this.controller = contr;
    }
    /**
     * Preforms a fake sale, by calling all system operaitions in the controller.
     */
    public void sampleExecution() {
        System.out.println("Cashier starts new sale.\n");
        controller.makeNewSale(); 
        System.out.println("Cashier enters items. \n");   
        
        // Handling output from enterItem, assuming it needs a description if not null
        ItemDTO item = controller.enterItem("11127", new Amount(5)); // Apples
        System.out.println(item != null ? item.toString() : "Item not found");
        item = controller.enterItem("11123", new Amount(2)); // Bread
        System.out.println(item != null ? item.toString() : "Item not found");
        item = controller.enterItem("11132", new Amount(1)); // Chicken
        System.out.println(item != null ? item.toString() : "Item not found");
        item = controller.enterItem("11135", new Amount(3)); // Tea
        System.out.println(item != null ? item.toString() : "Item not found");   
    
        System.out.println("Cashier enters the paid amount. \n");
        Amount change = controller.payment(new Amount(500)); 
        System.out.println("Change returned: " + change.getAmount()+ "\n");
        
    }
    
}
