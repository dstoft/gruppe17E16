/**
 * This class NPC is the Non playable characters there is on each planet & moon.
 * It holds setters & getters from/to the Inventory class and it is the NPC that
 * creates an Inventory to each NPC in the game.
 */
package worldofzuul;

/**
 * Imports Java utility for UUID
 */
import java.util.UUID;

/**
 *
 * @author Emil & Daniel
 */
public class NPC {

//Initializing variables
    private String _name; // Every NPC has a name
    private String _description; // Every NPC has a desciption              
    private UUID _id; // Every NPC has a UUID (Random uniq number)                               
    private int _rid; //  Every NPC has a rid, it's an ID for sending and receive information between NPC's                     
    private int _conversationId; //  Every convensation has an ID, we need to call the convensations from apart.           
    private int _nextConversationId; // A setter to set the conversation ID.
    Inventory _inventory; // An Inventory for the NPC. 

 //Initializing Constructor
    
 /**
     * @param name
     * @param description
     * @param rid
     * @param id
*/
    
    public void npc(String name, String description, int rid, UUID id) {
        this._name = name;
        this._description = description;
        this._rid = rid;
        this._id = id;
        this._inventory = new Inventory();
    }

    
 //Initializing methods
    
    public String getInventoryString() {        //Method for returning the inventory
        return this._inventory.showInventory();
    }

    public UUID createItem(String name, int weight, int rid) {      // Method for creating an Item with 3 parameters

        return this._inventory.addItem(weight, name, rid);
    }

    public void removeItem(UUID itemId) {       // Method for removing an Item from the inventory with the UUID

        this._inventory.remItem(itemId);

    }

    public void removeItem(int rid) {       // Method for removing an Item from the inventory with the rid
        this._inventory.remItem(rid);
    }

    public UUID getId() {       // Method for getting the UUID for one item.
        return this._id;
    }

    public String getItemInfo(int rid) {        //Method to get the Item info from the rid.
        return this._inventory.getItemInfo(rid);
    }

    public UUID setItemInfo(String info) {      //Method to set the item info using a string
        return this._inventory.setItemInfo(info);
    }

    public void setReceiverRid(int rid) {       //Method to set the Receiver Rid.
        this._rid = rid;
    }

    public void setNextConversationId(int id) {     //Method to set the next conversations Id.
        this._nextConversationId = id;
    }

    public int getRid() {       // Method to get the RId
        return this._rid;
    }

    public int[] getRids() {        // Method to get the Rid from a place in the inventory
        return this._inventory.getItemRids();
    }

}
