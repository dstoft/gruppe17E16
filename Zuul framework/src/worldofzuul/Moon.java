
public class Moon {

	//Initializing variables
	private String desciption;
	private int xCoor;
	private int yCoor;
	private Planet planet;

	//Creating list for moons
	public static ArrayList<Moon> moons;
	moons = new ArrayList<Moon>;

	//Constructor
	public Moon(String desciption, int xCoor, int yCoor, Planet planet) {
		this.desciption = desciption;
		this.xCoor = xCoor;
		this.yCoor = yCoor;
		moons.add(this);	//Adding the created object of type Moon to the list of moons
	}


	//Getters
	public String getDescription() {
		return desciption;
	}

	public PLanet getPlanet() {
		return planet;
	}

	//Only returns the coordinates if the player is on the moon/planet (same coordinates)
	//Idea here is that the moon wont be shown to the user, unless he can travel to it
	//Should this be checked somewhere else???
	public int getXCoor() {
		if (xCoor == Player.xCoor)	//syntax?
			return xCoor;
		else
			return 0;
	}

	public int getYCoor() {
		if (yCoor == Player.yCoor)	//syntax?		
			return yCoor;
		else
			return 0;
	}



}