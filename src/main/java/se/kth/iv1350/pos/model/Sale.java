package se.kth.iv1350.pos.model;

import java.time.LocalTime;
import java.time.LocalDate;
import java.util.ArrayList;

import se.kth.iv1350.pos.integration.Item;
import se.kth.iv1350.pos.integration.ItemDTO;
import se.kth.iv1350.pos.integration.SystemCreator;
import se.kth.iv1350.pos.util.Amount;

/**
 * This class contains everything a sale should contain and methods to perform a sale.
 */
public class Sale {
    private TimeAndDate timeAndDate;
    private ArrayList<Item> items;
    private Amount totalPrice;
    private double totalVAT;
    private SystemCreator externalSystemCreator;

    /**
     * Creates a new instance of a sale, initializing the combined start time and date,
     * and initial empty lists for items.
     */
    public Sale() {
        this.timeAndDate = new TimeAndDate();
        this.items = new ArrayList<>();
        this.totalPrice = new Amount(0);
        this.totalVAT = 0;
    }

    /**
     * Sets the external systems creator for the sale.
     * @param systemCreator the external systems creator.
     */
    public void setSystemCreator(SystemCreator systemCreator) {
        this.externalSystemCreator = systemCreator;
    }

    /**
     * Adds an item to the current sale based on the provided item details and quantity.
     * If the item already exists in the sale, it increases the quantity;
     * otherwise, it adds a new item to the sale list.
     * @param itemDTO Item details to add, encapsulated in ItemDTO.
     * @param quantity Quantity of the item to add.
     */
    public void getItem(ItemDTO itemDTO, int quantity) {
        Item item = findItem(itemDTO.getItemIdentifier());
        if (item != null) {
            item.increaseQuantity(quantity);
        } else {
            items.add(new Item(itemDTO, quantity));
        }
        updateTotalPriceAndVAT();
    }

    /**
     * Returns the total price of the sale including VAT.
     * @return Total price including VAT as an Amount.
     */
    public Amount getTotalPriceIncludingVAT(){
        return totalPrice;
    }

    /**
     * Returns the total VAT for the sale.
     * @return Total VAT as a double.
     */
    public double getTotalVAT(){
        return totalVAT;
    }

    /**
     * Processes the payment for the sale, updates the external systems, and calculates the change due.
     * @param cashPayment Payment made for the sale.
     * @param cashRegister Register to record the payment.
     * @return Change due to the customer.
     */
    public Amount payment(CashPayment cashPayment, CashRegister cashRegister) {
        Amount change = cashRegister.addPayment(cashPayment);
        externalSystemCreator.getInventorySystem().updateInventory(this);
        externalSystemCreator.getAccountingSystem().updateAccounting(this);
        return change;
    }

    /**
     * Returns a list of all items in the current sale.
     * @return A shallow copy of the items list.
     */
    public ArrayList<Item> getItems(){
        return new ArrayList<>(items);
    }

    /**
     * Retrieves the quantity of a specific item in the sale based on its identifier.
     * @param itemIdentifier Identifier of the item.
     * @return Quantity of the specified item.
     */
    public int getQuantityOfItem(String itemIdentifier){
        Item item = findItem(itemIdentifier);
        return item != null ? item.getQuantity() : 0;
    }

    /**
     * Creates and returns a receipt for the sale.
     * @param totalCost The total amount paid for the sale.
     * @return A new Receipt object.
     */
    public Receipt createReceipt(Amount totalCost) {
        Amount paidAmount = totalCost;
        CashPayment payment = new CashPayment(paidAmount, totalCost);
        return new Receipt(this, payment);
    }

    // Private helper methods

    /**
     * Updates the total price and VAT based on the items in the sale.
     */
    private void updateTotalPriceAndVAT() {
        double totalPrice = 0;
        double totalVAT = 0;
        for (Item item : items) {
            double itemTotal = item.getPrice() * item.getQuantity();
            totalPrice += itemTotal;
            totalVAT += itemTotal * item.getVATRate();
        }
        this.totalPrice = new Amount((int) totalPrice);
        this.totalVAT = totalVAT;
    }

    /**
     *
     * Finds an item in the sale by its identifier.
     * @param itemId The identifier of the item to find.
     * @return The Item if found, otherwise null.
     */
    private Item findItem(String itemId) {
        for (Item item : items) {
            if (item.getItemIdentifier().equals(itemId)) {
                return item;
            }
        }
        return null;
    }

    /**
     * Inner class to manage time and date together.
     */
    private class TimeAndDate {
        private LocalTime time;
        private LocalDate date;

        /**
         * Constructs a new instance of TimeAndDate, initializing it with the current time and date.
         */
        public TimeAndDate() {
            this.time = LocalTime.now();
            this.date = LocalDate.now();
        }

        /**
         * Returns the time component of this TimeAndDate instance.
         * @return The current time.
         */
        public LocalTime getTime() {
            return time;
        }

        /**
         * Returns the date component of this TimeAndDate instance.
         * @return The current date.
         */
        public LocalDate getDate() {
            return date;
        }
    }

    /**
     * Returns the start time of the sale.
     * @return The time when the sale started.
     */
    public LocalTime getStartSaleTime() {
        return timeAndDate.getTime();
    }

    /**
     * Returns the start date of the sale.
     * @return The date when the sale started.
     */
    public LocalDate getStartSaleDate() {
        return timeAndDate.getDate();
    }
}
