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
     * maxAllowedItems with default 3 maxAllowedWeight with default 12
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
    public void setMaxItems(int x) {
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
    public void setMaxWeight(int x) {
        this.maxAllowedWeight = x;
    }

    /**
     * addItem adds a item, of type Item, to the inventory.
     *
     * TODO: There needs a destination too.. The uniqID could be implemented
     * better in regards of how to remove items again.
     *
     * @param weight Set the weight of the item (int).
     * @param name Set the name of the item (String).
     * @param desciption Set a description (String).
     * @param xCoor Sets the x coordinate for destination
     * @param yCoor Sets the y coordinate for destination
     * @param papers Set wether there are papers for the item (boolean).
     * @return returns the unique ID number for the added item.
     */
    public int addItem(String name, int weight, String desciption, int xCoor, int yCoor, boolean papers) {

        uniqID++; // This increases uniqID each time a item is added
        Items item = new Items(uniqID, name, weight, desciption, xCoor, yCoor, papers);

        inventoryList.add(item);

        return uniqID;

    }

    /**
     * remItem removes a given item from the inventory, based upon the unique ID
     * returned from the addItem method.
     *
     * TODO: better implementation in regards to uniqID and idendifying items.
     *
     * @param x int for witch item to remomve
     */
    public void remItem(int x) {
        inventoryList.remove(x - 1);
    }

    /**
     * showInventory runs a for-each loop appending all the items listed in
     * inventoryList in a string and returns it.
     *
     * @return A string containing the name value of each of the items listed in
     * the ArraList inventoryList.
     */
    public String showInventory() {

        boolean isEmpty = inventoryList.isEmpty();
        String invContent;

        if (isEmpty) {
            invContent = "The inventory is empty";
        } else {

            invContent = "START ";

            StringBuilder sb = new StringBuilder();

            sb.append(invContent);
            int o = 0;

            for (Items i : inventoryList) {
                sb.append(inventoryList.get(o).getName());
                sb.append(", ");
                o++;
            }

            sb.append("STOP");

            invContent = sb.toString();

        }

        return invContent;

    }
}
