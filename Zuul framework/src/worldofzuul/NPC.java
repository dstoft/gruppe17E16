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
    private int _pid;
    private UUID _packageId;
    private UUID _planetId;
    private int _conversationId;
    private int _nextConversationId;
    Inventory _inventory;

    public NPC(String name, String description, int rid, int pid, int conversationId, UUID id) {
        this._name = name;
        this._description = description;
        this._rid = rid;
        this._pid = pid;
        this._conversationId = conversationId;
        this._nextConversationId = -1;
        this._id = id;
        this._inventory = new Inventory();
    }

    // ***** GETTERS *****
    public UUID getId() {
        return this._id;
    }
    
    public UUID getPlanetId() {
        return this._planetId;
    }

    public int getRid() {
        return this._rid;
    }
    
    public int getPid() {
        return this._pid;
    }
    
    public UUID getPackageId() {
        return this._packageId;
    }
    
    public String getName() {
        return this._name;
    }
    
    public String getDescription() {
        return this._description;
    }
    
    public int getConversationId() {
        return this._conversationId;
    }
    
    public int getNextConversationId() {
        return this._conversationId;
    }
    // ***** GETTERS END *****

    // ***** SETTERS *****
    public void setReceiverRid(int rid) {
        this._rid = rid;
    }
    
    public void setPlanetId(UUID planetId) {
        this._planetId = planetId;
    }

    public void setConversationId(int id) {
        this._conversationId = id;
    }
    
    public void setNextConversationId(int id) {
        this._nextConversationId = id;
    }
    
    public void setPackageId(UUID uuid) {
        this._packageId = uuid;
    }
    // ***** SETTERS END *****

    public boolean hasNextConversationId() {
        return this._nextConversationId != -1;
    }
    
    // ***** GETTERS REGARDING INVENTORY *****
    public UUID[] getInventoryUuids() {
        return this._inventory.getInventoryUuids();
    }
    // ***** GETTERS REGARDING INVENTORY END *****

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

}
