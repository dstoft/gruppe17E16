/**
 *
 * @author Thilos
 */
public class Items {

//Initializing variables
    private int id; //Every item have an ID.
    private int weight; // The weight of the item.
    private String description; // The description of the item.
    private int xCoor;  // The x coordinate for the destination 
    private int yCoor;  // The y coordinate for the destination 
    private boolean papers; // Does the user have papers on the item

//Initializing Constructor
    public Items(int id, int weight, String desciption, int xCoor, int yCoor, boolean papers) {
        this.id = id;
        this.weight = weight;
        this.description = desciption;
        this.xCoor = xCoor;
        this.yCoor = yCoor;
        this.papers = papers;
        
    }

//Initializing Getters methods
    
    public String getDescription() {        //Method for getting the description of the item.

         return description;
    }

    public int xCoor() {        //Method for returning  destination x coordinate

         return xCoor;
    }
    
     public int yCoor() {        //Method for returning  destination y coordinate

         return yCoor;
    }

    public int getWeight() {         //Method for getting the weight of the item.

         return weight;
    }
    
    public int getId() {        // //Method for getting the ID of the item.

         return id;
    }

    public boolean getPapers() {         //Method to check if the user have papers on the item.
         if (papers == false) {                
            System.out.println("You don't have papers on that pakage!");
                return false;
        }
                                                        //If the user have the paper, returns true, if not, returns false.
         else
            System.out.println("Your paperwork is just fine!");
              return true;
        }

}
