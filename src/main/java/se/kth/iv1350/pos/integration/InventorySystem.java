
package se.kth.iv1350.pos.integration;

import java.util.ArrayList;
import java.util.List;

import se.kth.iv1350.pos.model.Sale;

/**
 * Represents an external inventory system
 */
public class InventorySystem {
    private List<ItemDTO> inventory = new ArrayList<>();

     /**
     * Creates a new instance of the inventory system(dummy).
     */
    public InventorySystem(){
        addItems();
    }

    /**
         * Searches for an item within the inventory using the provided item ID.
         * @param itemId the item ID employed to locate a specific item.
         * @return return the appropriate item, or null if no matching item is found.
         */
	public ItemDTO findItem(String itemId) {
		for(int i = 0; i < inventory.size(); i++) {
			if(inventory.get(i).getItemIdentifier() .equals((itemId)) ) {
				return inventory.get(i);
			}
		}
		return null;
	}


    /**
     * The function is a placeholder for when the system updates the inventory in the external system.
     * @param sale an object holding the infromaiton about the current sale.
     */
    public void updateInventory(Sale sale){
        System.out.println("Items logged, Inventory system updated");
    }
  


    private void addItems(){
        inventory.add(new ItemDTO("Apples", 0.06, 23, "Fresh apples", "11127"));
        inventory.add(new ItemDTO("Bread", 0.06, 28, "Whole wheat bread", "11123"));
        inventory.add(new ItemDTO("Chicken", 0.06, 150, "Free range chicken", "11132"));
        inventory.add(new ItemDTO("Tea", 0.06, 30, "Green tea", "11135"));
    }
}
