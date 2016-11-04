package worldofzuul; // HUSK

import java.util.UUID;


/**
 *
 * @author Thilos
 */

public class Items {
    
   /**
    * The class Items is based on that there is several different items that the player can get. 
    * These Items must be delivered from one planet to another through a x/y coordinate.
    * Each item has a unique number and description & the item has a weight.
    * There is a limit of how much weight and how many items the player can have.
    * The paperwork on each item can have an effect of the difficulty to deliver the pakage. 
*/
    
//Initializing variables
    private UUID id; //Every item has an ID
    private int weight; // The weight of the item
    private String description; // The description of the item
    private int rid;  // The RID for the destination
    //private boolean papers; // Does the user have papers on the item

//Initializing Constructor

    /**
     *
     * @param weight
     * @param desciption
     * @param RID
     */
    public Items(int weight, String desciption, int rid) {
        this.id = UUID.randomUUID();
        this.weight = weight;
        this.description = desciption;
        this.rid = rid;
        //this.papers = papers;
        
    }

//Initializing Getters methods
    
    public String getDescription() {        //Method for getting the description of the item.

         return description;
    }

    public int getRID() {        //Method for returning  destination RID

         return rid;
    }

    public int getWeight() {         //Method for getting the weight of the item.

         return weight;
    }
    
    public UUID getId() {        // //Method for getting the ID of the item.

         return id;
    }

    /*public boolean getPapers() {         //Method to check if the user have papers on the item.
    return papers;
    }*/

}
