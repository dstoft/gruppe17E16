package worldofzuul; // HUSK

import java.util.ArrayList; //Imports the utility for the Arraylist
import java.util.UUID;

/**
 * Handles inventory and handles items. Contains the limits on how big an
 * inventory is.
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
    private UUID uuid;

    /**
     * Inventory constructs a inventory with the possibility to set maximum
     * weight the player is allowed to carry and the maximum amount of items
     * allowed to carry. If no values are given, the defaults will be used.
     *
     * @param maxItems set max allowed amount of items in integers default is 3.
     * @param maxWeight set max allowed weight in integers, default is 12.
     *
     */
    public Inventory(int maxItems, int maxWeight) {

        this.maxAllowedItems = maxItems;
        this.maxAllowedWeight = maxWeight;
        uuid = UUID.randomUUID();
        this.inventoryList = new ArrayList<Items>();
    }

    /**
     * Constructor without setting limits on the inventory, which means it uses
     * the default.
     */
    public Inventory() {

        uuid = UUID.randomUUID();
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
     * @param desciption Set a description (String).
     * @param RID Sets the ID of the recipients
     * @return returns the UUID number for the added item.
     */
    public UUID addItem(int weight, String desciption, int RID) {

        UUID uid = null;
        sumWeight = sumWeight + weight;

        if (sumWeight < maxAllowedWeight && sumItems < maxAllowedItems) {

            Items item = new Items(weight, desciption, RID);

            inventoryList.add(item);

            sumItems++; //This keeps track of the amount of items. Used for limiting. Increasses with 1 for each addItem()

            uid = item.getId();

        }

        return uid;
    }

    /**
     * remItem removes a given item from the inventory, based upon the unique ID
     * returned from the addItem method.
     *
     * TODO: better implementation in regards to uniqID and idendifying items.
     *
     * @param uid UUID of item to be deleted.
     *
     */
    public void remItem(UUID uid) {

        int o = 0;

        for (Items i : inventoryList) {
            if (uid.equals(inventoryList.get(o).getId())) {
                inventoryList.remove(o);
            }
            o++;
        }

        sumItems--; //Decreases by one, to keep keeping track of the amount of items.
    }

    /**
     * Overloading!
     *
     * @param rid
     * @author Dstoft
     */
    public void remItem(int rid) {
        for (Items item : inventoryList) {
            if (item.getRID() == rid) {
                remItem(item.getId());
            }
        }
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
        String invContent = null;

        if (!isEmpty) {

            StringBuilder sb = new StringBuilder();

            int o = 0;

            for (Items i : inventoryList) {
                sb.append(o + 1);
                sb.append(": ");
                sb.append(inventoryList.get(o).getDescription());
                sb.append(". \n");
                o++;
            }

            invContent = sb.toString();

        }

        return invContent;

    }

    /**
     * Takes in the inventory position index, and finds the UUID for that item
     *
     * @param x the index in the item list
     * @return the UUID of that item
     */
    public UUID getUUIDFromInvPos(int x) {

        boolean isEmpty = inventoryList.isEmpty();
        UUID itemUUID = null;

        if (!isEmpty) {
            itemUUID = inventoryList.get(x - 1).getId();
        }

        return itemUUID;

    }

    /**
     * Returns an item as a string, which is used for sending it between
     * inventories, because the Game class does not know of Inventory
     * @param id the RID of the item you want
     * @return the string that contains all of the information that is needed
     */
    public String getItemInfo(int id) {

        String invContent = null;
        int o = 0;

        for (Items i : inventoryList) {

            int itemID = inventoryList.get(o).getRID();

            if (id == itemID) {

                StringBuilder sb = new StringBuilder();

                sb.append(inventoryList.get(0).getDescription());
                sb.append(";");
                sb.append(inventoryList.get(0).getWeight());
                sb.append(";");
                sb.append(inventoryList.get(0).getRID());

                invContent = sb.toString();

            }

            o++;

        }

        return invContent;

    }

    /**
     * Used for creating an item from a string, that is formatted with ";"
     * @param info the string that contains the information
     * @return the UUID of the added item
     */
    public UUID setItemInfo(String info) {
        String desc;
        int weight;
        int rid;
        String[] infoArray;

        infoArray = info.split(";");

        desc = infoArray[0];
        weight = Integer.parseInt(infoArray[1]);
        rid = Integer.parseInt(infoArray[2]);

        return addItem(weight, desc, rid);

    }

    /**
     * Gets all of the RIDs for this inventory
     * @return an integer array of the RIDs
     */
    public int[] getItemRids() {
        int[] returnArray = new int[this.maxAllowedItems];
        int count = 0;
        for (Items item : this.inventoryList) {
            returnArray[count] = item.getRID();
            count++;
        }
        return returnArray;
    }
}
