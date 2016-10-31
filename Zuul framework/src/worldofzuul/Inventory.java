/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuul;

import java.util.UUID;

/**
 *
 * @author DanielToft
 */
public class Inventory {
    public UUID getUUIDFromInventoryPos(int number) {
        return UUID.randomUUID();
    }
    
    public void remItem(UUID uuid) {
        return;
    }
    
    public String showInventory() {
        return "yay inventory showing!";
    }
}
