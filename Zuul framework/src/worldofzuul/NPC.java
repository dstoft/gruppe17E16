/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuul;

import java.util.UUID;

/**
 * Holds all of the information regarding NPCs. It has methods for handling its
 * inventory, both deleting and adding items
 * @author emil_daniel
 */
public class NPC {

    private String _name;
    private String _description;
    private UUID _id;
    private int _rid;
    private int _conversationId;
    private int _nextConversationId;
    Inventory _inventory;

    public NPC(String name, String description, int rid, UUID id) {
        this._name = name;
        this._description = description;
        this._rid = rid;
        this._id = id;
        this._inventory = new Inventory();
    }

    // ***** GETTERS *****
    public UUID getId() {
        return this._id;
    }

    public int getRid() {
        return this._rid;
    }
    // ***** GETTERS END *****

    // ***** SETTERS *****
    public void setReceiverRid(int rid) {
        this._rid = rid;
    }

    public void setNextConversationId(int id) {
        this._nextConversationId = id;
    }
    // ***** SETTERS END *****

    // ***** GETTERS REGARDING INVENTORY *****
    public String getInventoryString() {
        return this._inventory.showInventory();
    }

    public String getItemInfo(int rid) {
        return this._inventory.getItemInfo(rid);
    }

    public int[] getRids() {
        return this._inventory.getItemRids();
    }
    // ***** GETTERS REGARDING INVENTORY END *****

    /**
     * Creates an item using the method in inventory
     * @param name
     * @param weight
     * @param rid the receiver id
     * @return the UUID of the newly created item
     */
    public UUID createItem(String name, int weight, int rid) {
        return this._inventory.addItem(weight, name, rid);
    }

    /**
     * Removes an item based on the UUID of that item
     * @param itemId the UUID if the item
     */
    public void removeItem(UUID itemId) {
        this._inventory.remItem(itemId);
    }

    /**
     * Removes an item based on the RID of that item
     * @param rid the receiver id of the item
     */
    public void removeItem(int rid) {
        this._inventory.remItem(rid);
    }

    /**
     * Creates a new item based on a string
     * @param info a string that contains the information of the item
     * @return the UUID of the newly created item
     */
    public UUID setItemInfo(String info) {
        return this._inventory.setItemInfo(info);
    }

}
