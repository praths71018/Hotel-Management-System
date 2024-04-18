package service;

import model.Inventory;
import java.util.List;

public class InventoryService {
    public boolean addItem(String itemName, int quantity, double price) {
        return Inventory.addItem(itemName, quantity, price);
    }

    public void deleteItem(String itemName) {
        Inventory.deleteItem(itemName);
    }

    public String getAllItems() {
        return Inventory.getAllItems();
    }
}
