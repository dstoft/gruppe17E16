

public class Planet {

	//Initializing variables
	private String description;
	private String name;
	private int xCoor;
	private int yCoor;
	private int numOfMoons;
		

	//Creating list for planets
	public static ArrayList<Planet> planets;
	planets = new ArrayList<Planet>;


	//Constructor
	public Planet(String name, String description, int xCoor, int yCoor, int numOfMoons) {		
		this.name = name;
		this.description = description;
		this.xCoor = xCoor;
		this.yCoor = yCoor;
		this.numOfMoons = numOfMoons;
		planets.add(this);	//Adding the created object to the list of planets

	}


	//Getters
	public int getNumOfMoons() {
		return moons.size();
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