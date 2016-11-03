/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuul;

import com.sun.org.apache.xerces.internal.xs.PSVIProvider;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Holds all of the information regarding the player
 * Contains methods for accessing the players inventory
 * @author DanielToft
 */
public class Player {
    //Defines variables
    private UUID _currentPlanetId; //The planet the player is currently at
    private Inventory _inventory; //The players inventory
    private int _maxFuel; //How much fuel the player can hold
    private int _fuel; //How much fuel the player currently has
    private int _reputation; //How much reputation the player has
    
    public Player(UUID currentPlanet, int maxFuel, int startingReputation) {
        this._currentPlanetId = currentPlanet;
        this._maxFuel = maxFuel;
        this._fuel = maxFuel;
        this._reputation = startingReputation;
        
        this._inventory = new Inventory();
    }
    
    // ***** SETTERS *****
    public void setCurrentPlanet(UUID planetId) {
        this._currentPlanetId = planetId;
        this.setFuel(this._maxFuel);
    }
    public void setFuel(int fuel) {
        if(fuel < this._maxFuel) {
            this._fuel = fuel;
        } else {
            this._fuel = this._maxFuel;
        }
    }
    public void setReputation(int reputation) {
        this._reputation = reputation;
    }
    // ***** SETTERS END *****
    
    // ***** GETTERS *****
    public UUID getCurrentPlanetId() {
        return this._currentPlanetId;
    }
    public Inventory getInventory() {
        return this._inventory;
    }
    public int getMaxFuel() {
        return this._maxFuel;
    }
    public int getFuel() {
        return this._fuel;
    }
    public int getReputation() {
        return this._reputation;
    }
    // ***** GETTERS END *****
    
    // ***** HANDLING INVENTORY *****
    /**
     * Drops an item
     * @param itemName the inventory position as a number
     * @return whether or not it was dropped
     */
    public boolean dropItem(int itemName) {
        UUID tempUUID = this._inventory.getUUIDFromInvPos((itemName-1));
        if(tempUUID == null) {
            return false;
        } else {
            this._inventory.remItem(tempUUID);
            return true;
        }
    }
    
    /**
     * Attemps to add an item from an itemString (contains all of its information in a string)
     * @param itemString a string containing item's information
     * @return whether or not it was succesfully added
     */
    public boolean addItem(String itemString) {
        if(this._inventory.setItemInfo(itemString) == null) {
            return false;
        }
        return true;
    }
    
    /**
     * Gets a description of the inventory
     * @return a string
     */
    public String getInventoryString() {
        return this._inventory.showInventory();
    }
    
    /**
     * Gets all the items' rids in inventory 
     * @return an array of integers containing the rids
     */
    public int[] getInventoryRids() {
        return this._inventory.getItemRids();
    }
    // ***** HANDLING INVENTORY END *****
}
