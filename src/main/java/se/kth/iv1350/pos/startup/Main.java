package se.kth.iv1350.pos.startup;

import se.kth.iv1350.pos.controller.Controller;
import se.kth.iv1350.pos.integration.SystemCreator;
import se.kth.iv1350.pos.integration.ReceiptPrinter;
import se.kth.iv1350.pos.view.View;
import se.kth.iv1350.pos.model.CashRegister;

/**
 * This is the start sequence of the entire application.
 */
public class Main {
    /**
     * * Starts the application.
     * @param args The application does not take any command line parameters.
     */
    public static void main(String[] args){
        SystemCreator systemCreator = new SystemCreator();
        ReceiptPrinter printer = new ReceiptPrinter();
        CashRegister cashRegister = new CashRegister();
        Controller contr = new Controller(systemCreator,cashRegister, printer);
        View view = new View(contr);
        view.sampleExecution();
    }
}
