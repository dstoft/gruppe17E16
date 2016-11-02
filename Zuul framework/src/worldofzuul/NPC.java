/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuul;

import java.util.UUID;

/**
 *
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

    public void npc(String name, String description, int rid, UUID id) {
        this._name = name;
        this._description = description;
        this._rid = rid;
        this._id = id;
        this._inventory = new Inventory();
    }

    

    public String getInventoryString() {
        return this._inventory.showInventory();
    }

    public UUID createItem(String name, int weight, int rid) {

        return this._inventory.addItem(weight, name, rid);
    }

    public void removeItem(UUID itemId) {

        this._inventory.remItem(itemId);

    }
    
    public void removeItem(int rid) {
        this._inventory.remItem(rid);
    }

    public UUID getId() {
        return this._id;
    }

    public String getItemInfo(int rid) {
        return this._inventory.getItemInfo(rid);
    }
    
    public UUID setItemInfo(String info) {
        return this._inventory.setItemInfo(info);
    }
    
    public void setReceiverRid(int rid) {
        this._rid = rid;
    }
    
    public void setNextConversationId(int id) {
        this._nextConversationId = id;
    }
    
    public int getRid() {
        return this._rid;
    }
    
    public int[] getRids() {
        return this._inventory.getItemRids();
    }

}
