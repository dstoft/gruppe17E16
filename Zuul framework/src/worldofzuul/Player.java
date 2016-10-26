/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuul;

import com.sun.org.apache.xerces.internal.xs.PSVIProvider;
import java.util.ArrayList;

/**
 *
 * @author DanielToft
 */
public class Player {
    //Defines variables
    private String _currentPlanet; //Skal være Planet istedet for String
    private String _inventory; //Skal være Inventory istedet for String
    private Dashboard _dashboard;
    private int _maxFuel;
    private int _fuel;
    private int _reputation; //Maximum is 10
    
    public Player(String currentPlanet, int maxFuel, int startingReputation) {
        this._currentPlanet = currentPlanet;
        this._maxFuel = maxFuel;
        this._fuel = maxFuel;
        this._reputation = startingReputation;
        
        this._inventory = new String(); //Inventory istedet for String
        this._dashboard = new Dashboard();
    }
    
    // ***** SETTERS *****
    public void setCurrentPlanet(String planet) {
        this._currentPlanet = planet;
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
    public String getCurrentPlanet() {
        return this._currentPlanet;
    }
    public String getInventory() {
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
    
    public void pickup(String itemName) { //Skal dette være en string, som i det så er navnet?
        ArrayList<Item> possibleItems = this._currentPlanet.getInventory().getAllItems();
        if(possibleItems.size() > 0) {
            for(Item item : possibleItems) {
                if(itemName.equals(item.getName())) {
                    this._currentPlanet.getInventory().drop(item);
                    this._inventory.add(item);
                    System.out.println("You succesfully picked up " + item.getName() + "!");
                    return;
                }
            }
            System.out.println("Your item name does not exist on this planet!");
            //Possibly print possible items that exists on this planet
        } else {
            System.out.println("There is no items to pick up!");
        }
    }
    
    public void drop(String itemName) {
        ArrayList<Item> possibleItems = this._inventory.getAllItems();
        if(possibleItems.size() > 0) {
            for(Item item : possibleItems) {
                if(itemName.equals(item.getName())) {
                    this._currentPlanet.getInventory().add(item);
                    this._reputation -= item.getReputation(); // Watch out! The item may have negative numbers, which would result in an addition instead!
                    this._inventory.drop(item);
                    System.out.println("You succesfully dropped " + item.getName() + "!");
                    return;
                }
            }
            System.out.println("The item name does not exist in your inventory!");
            //Possibly print possible items that exists on this planet
        } else {
            System.out.println("You have no items to drop!");
        }
    }
    
    public void deliver() {
        
        int x = this._currentPlanet.getX();
        int y = this._currentPlanet.getY();
        
        ArrayList<Item> possibleItems = this._inventory.getPossibleDeliverItems(x, y);
        if(possibleItems.size() > 0) {
            for(Item item : possibleItems) {
                System.out.println("You delivered " + item.getName() + " here!");
                this._inventory.drop(item);
            }
        } else {
            System.out.println("You have no items that can be delivered here!");
            return;
        }
        
    }
    
    
    public void processCommand(Command command) {
        
        
        CommandWord commandWord = command.getCommandWord(); //Returns an object held by the command object

        if (commandWord == CommandWord.GO) { //If the command is go,
            //Here comes a movementment method from the class MovementCalculator, which is extended!
        }
        else if (commandWord == CommandWord.PICKUP) {
            this.pickup(command.getSecondWord());
        }
        else if (commandWord == CommandWord.DROP){
            this.drop(command.getSecondWord());
        }
        else if (commandWord == CommandWord.DELIVER){
            this.deliver();
        }
        
        
    }
    
}
