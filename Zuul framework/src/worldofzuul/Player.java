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
 * Holds all of the information regarding the player. 
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
    public UUID getPlanetId() {
        return this._currentPlanetId;
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
    public UUID[] getInventoryUuids() {
        return this._inventory.getInventoryUuids();
    }
    
    /**
     * Creates an item using the method in inventory
     * @param uuid
     * @param weight
     * @return the UUID of the newly created item
     */
    public boolean addItem(UUID uuid, int weight) {
        return this._inventory.addItem(uuid, weight);
    }

    /**
     * Removes an item based on the UUID of that item
     * @param itemId the UUID if the item
     * @param weight
     */
    public void removeItem(UUID itemId, int weight) {
        this._inventory.remItem(itemId, weight);
    }
    
    public boolean hasInventorySpaceFor(int weight) {
        return this._inventory.hasSpaceFor(weight);
    }
    // ***** HANDLING INVENTORY END *****
}
