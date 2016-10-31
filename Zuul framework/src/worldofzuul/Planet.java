
public class Planet {

    //Initializing variables
    private UUID _npcId;
    private Moon _moon;
    private String _description; //a short description of the planet
    private String _name;        //name of the planet
    private int _xCoor;          //x-coordinate of the planet
    private int _yCoor;          //y-coordinate of the planet
    private int _referenceNum;   //unique referencenumber for the planet
    public static int referenceNumCounter = 1;    //static counter for creating new referencenumbers			

    
    //Constructor
    public Planet(String name, String description, int xCoor, int yCoor, UUID npcId, Moon moon) {	
    this._moon = moon;       
	this._name = name;
	this._description = description;
	this._xCoor = xCoor;
	this._yCoor = yCoor;
    this._npcId = npcId;
    this._referenceNum = Planet.referenceNumCounter;
    Planet.referenceNumCounter++;
    }


    //Getters
    public boolean hasMoon() {
        this._moon != null;
    }

    public String getName() {
	   return this._name;
    }

    public String getDescription() {
	   return this._description;
    } 

    public int getXCoor() {
	   return this._xCoor;
    }

    public int getYCoor() {
	   return this._yCoor;
    }

    public int getReferenceNum() {
        return this._referenceNum;
    }
}
