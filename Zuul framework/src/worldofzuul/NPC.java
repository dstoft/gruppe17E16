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
    //The number used by the user to reference the NPC during runtime
    public static int referenceCounter = 0;
    
    private String _name;
    private String _description;
    private UUID _id;
    private int _referenceNumber; //The number used by the user to reference the NPC during runtime
    private int _rid; //Identifies which Item the NPC has to receive by the start of the game
    private int _pid; //Identifies where the NPC should be placed at the start of the game
    private UUID _packageId; //Which Item UUID the NPC has to receive
    private UUID _planetId; //Which Planet/Moon UUID the NPC is placed at
    private int _chanceToMove;
    private int _conversationId;
    private int _nextConversationId;
    Inventory _inventory;

    /**
     * Constructor
     * @param name of the NPC
     * @param description of the NPC
     * @param rid used to identify which items this NPC has to receive by the start of the game
     * @param pid used to tell where the NPC should be placed (on a moon or planet) at the start of the game
     * @param conversationId the first conversation id (which conversation file) the NPC should use at the start of the game
     * @param chanceToMove whether or not the NPC can move. 0 means completely no movement, 10 means certain of moving, in between means x/10 chance to move
     */
    public NPC(String name, String description, int rid, int pid, int conversationId, int chanceToMove) {
        this._name = name;
        this._description = description;
        this._rid = rid;
        this._pid = pid;
        this._chanceToMove = chanceToMove;
        this._conversationId = conversationId;
        this._nextConversationId = -1;
        this._id = UUID.randomUUID();
        this._inventory = new Inventory();
        
        this._referenceNumber = NPC.referenceCounter;
        NPC.referenceCounter++;
    }

    // ***** GETTERS *****
    public UUID getId() {
        return this._id;
    }
    
    public int getReferenceNumber() {
        return this._referenceNumber;
    }
    
    public UUID getPlanetId() {
        return this._planetId;
    }

    public int getChanceToMove() {
        return this._chanceToMove;
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
