package worldofzuul; // HUSK

import java.util.ArrayList; //Imports the utility for the Arraylist

/**
 * Inventory - thats all (skriv mere)
 *
 * @author emil
 */
public class Inventory { // Initializing the class Inventory

    /**
     * Next is the declaration of the ArrayList witch will be the structure for
     * the inventory. (Adding/removing items etc)
     */
    private final ArrayList<Items> inventoryList;

    /**
     * Following are the two parameters for the inventory that can be set when
     * constructed. But if the class is constructed without them being set, it
     * will fall back to these predefined values:
     *
     * maxAllowedItems with default 3
     * maxAllowedWeight with default 12
     *
     */
    private int maxAllowedItems = 3;
    private int maxAllowedWeight = 12;

    /**
     * The inventory will have some values about how many items there are in it,
     * the cummulated weight, a unique ID number for each item, (maybe more to
     * come). These attributes will be declared here:
     */
    private int sumItems;
    private int sumWeight;
    private int uniqID = 0; //The IDs start from zero

    /**
     * Inventory constructs a inventory with the possibility to set maximum
     * weight the player is allowed to carry and the maximum amount of items
     * allowed to carry. If no values are given, the defaults will be used.
     *
     * @param maxItems set max allowed amount of items in integers default is 3.
     * @param maxWeight set max allowed weight in integers, default is 12.
     *
     */
    // The version of Inventory with parameters given.
    public Inventory(int maxItems, int maxWeight) {

        this.maxAllowedItems = maxItems;
        this.maxAllowedWeight = maxWeight;
        this.inventoryList = new ArrayList<Items>();
    }

    // The version of Inventory without parameters given.
    public Inventory() {

        this.inventoryList = new ArrayList<Items>();
    }

    /**
     * getMaxItems returns the maximum amount of items allowed to carry.
     *
     * @return maximum amount of items allowed in integers.
     */
    public int getMaxItems() {

        return maxAllowedItems;
    }

    /**
     * setMaxItems sets the max amount of items allowed to carry.
     * 
     * @param x int for setting maxAllowedItems
     */
    public void setMaxItems(int x){
        this.maxAllowedItems = x;
    }

    /**
     * getMaxWeight returns the maximum cumulated weight allowed to carry.
     *
     * @return maximum cumulated weight allowed in integers.
     */
    public int getMaxWeight() {

        return maxAllowedWeight;
    }

    /**
     * setMaxWeight set the max cumulated weight allowed to carry.
     * 
     * @param x int for setting maxAllowedWeight
     */
    public void setMaxWeight(int x){
        this.maxAllowedWeight = x;
    }

    /**
     * This method adds a item, of type Item, to the inventory.
     *
     * TODO: There needs a destination too.. and a return og the ID number
     *
     * @param weight Set the weight of the item (int).
     * @param name Set the name of the item (String).
     * @param desciption Set a description (String).
     * @param papers Set wether there are papers for the item (boolean).
     */
    public void addItem(int weight, String name, String desciption, boolean papers) {

        uniqID++; // This increases the unique ID for the item

        Items item = new Items(uniqID, weight, name, desciption, papers);

        inventoryList.add(item);

    }
    
    public void remItem(int x){
        inventoryList.remove(x);
    }

    /**
     * showInventory runs a for-each loop appending all the items listed in
     * inventoryList in a string and returns it.
     *
     * @return A string containing the name value of each of the items listed in
     * the ArraList inventoryList.
     */
    public String showInventory() {
        
        int invSize = inventoryList.size();
        for (Items i: inventoryList){
            
        }
        return "test";
    }
}
