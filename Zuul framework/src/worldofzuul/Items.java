package worldofzuul; // HUSK

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
    private int id; //Every item have an ID.
    private int weight; // The weight of the item.
    private String name; // The name of the item
    private String description; // The description of the item.
<<<<<<< HEAD
    private String destination; // The destination of the item - paused for now..
    private boolean papers; // Does the user have papers on the item

//Initializing Constructor (destination er lige gemt pt..)
    public Items(int id, int weight, String name, String desciption, boolean papers) {
=======
    private int xCoor;  // The x coordinate for the destination 
    private int yCoor;  // The y coordinate for the destination 
    private boolean papers; // Does the user have papers on the item

//Initializing Constructor
    public Items(int id, int weight, String desciption, int xCoor, int yCoor, boolean papers) {
>>>>>>> 9da079cac7eb89fcc9a18d9533b4fa40e455b736
        this.id = id;
        this.weight = weight;
        this.name = name;
        this.description = desciption;
<<<<<<< HEAD
=======
        this.xCoor = xCoor;
        this.yCoor = yCoor;
>>>>>>> 9da079cac7eb89fcc9a18d9533b4fa40e455b736
        this.papers = papers;
        
    }

//Initializing Getters methods
    
    public String getDescription() {        //Method for getting the description of the item.

         return description;
    }
    
    public String getName() {        //Method for getting the description of the item.

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
