/**
 *
 * @author Thilos
 */
public class Items {

//Initializing variables
    private int id; //Every item have an ID.
    private int weight; // The weight of the item.
    private String description; // The description of the item.
    private String destination; // The destination of the item
    private boolean papers; // Does the user have papers on the item

//Initializing Constructor
    public Items(int id, int weight, String desciption, String destination, boolean papers) {
        this.id = id;
        this.weight = weight;
        this.description = desciption;
        this.destination = destination;
        this.papers = papers;
    }

//Initializing Getters methods
    
    public String getDescription() {        //Method for getting the description of the item.

         return description;
    }

    public String getDestination() {        //Method for getting the destination of the item.

         return destination;
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
