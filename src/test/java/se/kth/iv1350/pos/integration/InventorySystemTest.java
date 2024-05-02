package test.java.se.kth.iv1350.pos.integration;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.kth.iv1350.pos.integration.InventorySystem;
import se.kth.iv1350.pos.integration.ItemDTO;

public class InventorySystemTest {

    private InventorySystem inventorySystem;

    @BeforeEach
    public void setUp() {
        inventorySystem = new InventorySystem();
    }

    @Test
    public void testFindExistingItem() {
        String expectedItemId = "11127"; // An actual existing item ID selected from your inventory initialization code
        ItemDTO resultItem = inventorySystem.findItem(expectedItemId);
        assertNotNull(resultItem, "The item should be found in the inventory");
        assertEquals(expectedItemId, resultItem.getItemIdentifier(), "The item ID should match the expected ID");
    }

    @Test
    public void testFindNonExistingItem() {
        String nonExistingItemId = "99999";// An assumed non-existing item ID
        ItemDTO resultItem = inventorySystem.findItem(nonExistingItemId);
        assertNull(resultItem, "No item should be found for a non-existing item ID");
    }
}
