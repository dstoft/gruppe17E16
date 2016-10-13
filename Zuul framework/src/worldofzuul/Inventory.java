import java.util.ArrayList; //Imports the utility for the Arraylist
/**
 *
 * @author Thilos
 */
public class Inventory { // Initializing the class Inventory
    
 //Initializing variables   
    private int maxWeight = 12;      //The inventory has a max weight that it can hold.
    private int maxItems = 3;      //The inventory has a max number of items there can be in it
    private ArrayList<Items> itemList; //ArrayList of the items.
    
//Initializing Constructor
public Inventory (int maxWeight, int maxItems) {
        this.maxItems = maxItems;
        this.maxWeight = maxWeight;
        this.itemList = new ArrayList<>();
}

//Initializing Getters methods
public int getMaxWeight() {        //Method for getting the maxWeight of the inventory

         return maxWeight;
    }

public int getMaxItems() {        //Method for getting the max number of items for the inventory

         return maxItems;
    }

public ArrayList<Items> getItemList(){     //Initializing Showitems methods

        return this.itemList; 
}

 //Initializing print methods
public void printItemList(){

    //for each loop og Print alle navnene

}        





// Add method

}





