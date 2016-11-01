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
 *
 * @author DanielToft
 */
public class Player {
    //Defines variables
    private UUID _currentPlanetId; //Skal være Planet istedet for String
    private Inventory _inventory; //Skal være Inventory istedet for String
    private Dashboard _dashboard;
    private int _maxFuel;
    private int _fuel;
    private int _reputation; //Maximum is 10
    
    public Player(UUID currentPlanet, int maxFuel, int startingReputation) {
        this._currentPlanetId = currentPlanet;
        this._maxFuel = maxFuel;
        this._fuel = maxFuel;
        this._reputation = startingReputation;
        
        this._inventory = new Inventory(); //Inventory istedet for String
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
    public Dashboard getDashboard() {
        return this._dashboard;
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
    
    public boolean dropItem(int itemName) {
        UUID tempUUID = this._inventory.getUUIDFromInvPos((itemName-1));
        if(tempUUID == null) {
            return false;
        } else {
            //this._inventory.remItem(tempUUID);
            return true;
        }
    }
    
    public String getInventoryString() {
        return this._inventory.showInventory();
    }
    
    
    
}
