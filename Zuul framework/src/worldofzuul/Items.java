package worldofzuul; // HUSK

import java.util.UUID;

/**
 * The class Items is based on that there is several different items that the player can get. These Items must be delivered from one planet to another through a receiver id (RID). Each item has a unique id and description & the item has a weight. There is a limit of how much weight and how many items the player can have. The paperwork on each item can have an effect of the difficulty to deliver the pakage.
 */
public class Items {

    public static int referenceCounter = 0;

    //Initializing variables
    private UUID id; //Every item have an ID
    private int weight; // The weight of the item
    private String description; // The description of the item
    private int rid;  // The RID for the destination
    private UUID npcId;
    private int referenceNumber;
    private int deliverytime; // Each item have a delivery time
    private boolean papers; // Does the user have papers on the item

    /**
     * The constructor
     *
     * @param weight
     * @param desciption
     * @param rid the id of where the item is to be delivered
     * @param deliverytime is the time to deliver the item
     */
    public Items(int weight, String desciption, int rid, int deliverytime, boolean papers) {
        this.id = UUID.randomUUID();
        this.weight = weight;
        this.description = desciption;
        this.rid = rid;
        this.referenceNumber = Items.referenceCounter;
        Items.referenceCounter++;
        this.deliverytime = deliverytime;
        this.papers = papers;

    }

    // ***** GETTERS *****
    public String getDescription() {        //Method for getting the description of the item.

        return description;
    }

    public int getRID() {        //Method for returning  destination RID

        return rid;
    }

    public UUID getNpcId() {
        return this.npcId;
    }

    public int getWeight() {         //Method for getting the weight of the item.

        return weight;
    }

    public UUID getId() {         //Method for getting the ID of the item.

        return id;
    }

    public int getReferenceNumber() {       //Method for getting the Referencenumber
        return this.referenceNumber;
    }

    public int getDeliveryTime() {          // Method for getting the deliverty time

        return this.deliverytime;
    }

    public boolean getPapers() {            // Method for getting the papers
        return papers;
    }

    // ***** GETTERS END *****
    // ***** SETTERS *****
    public void setNpcId(UUID npcId) {          // Method for setting the npc id
        this.npcId = npcId;
    }

    public void setDeliveryTime(int deliverytime) {         // Method for setting the delivery time.
        this.deliverytime = deliverytime;
    }

    public boolean setPapersFalse() {           //method for setting the papers to false
        return papers = false;
    }

    public boolean setPapersTrue() {            //method for setting the papers to true
        return papers = true;
    }

    // ***** SETTERS END *****
}
