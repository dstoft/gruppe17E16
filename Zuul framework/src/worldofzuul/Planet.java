
public class Planet {

    //Initializing variables
    private final String description;
    private final String name;
    private final int xCoor;
    private final int yCoor;
    private final int numOfMoons;
			

    /** Creating list of planets.
     * This list will contain all created instanses of the class Planet
     * as per the constructor.
     */
    public static ArrayList<Planet> planets = new ArrayList<>();
    
    //Constructor
    public Planet(String name, String description, int xCoor, int yCoor, 
                  int numOfMoons, NPC npc) {	
            
	this.name = name;
	this.description = description;
	this.xCoor = xCoor;
	this.yCoor = yCoor;
	this.numOfMoons = numOfMoons;
	planets.add(this);	//Adding the created object to the list of planets
    this.npc = npc;
    }


    //Getters
    public int getNumOfMoons() {
    	return numOfMoons;
    }

    public String getName() {
	return name;
    }

    public String getDescription() {
	return description;
    } 

    public int getXCoor() {
	return xCoor;
    }

    public int getYCoor() {
	return yCoor;
    }
}
