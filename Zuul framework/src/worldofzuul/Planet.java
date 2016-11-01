package worldofzuul;

import java.util.UUID;

public class Planet {

    //Initializing variables
    private UUID _id;
    private UUID _npcId;
    private Moon _moon;
    private String _description; //a short description of the planet
    private String _name;        //name of the planet
    private int _xCoor;          //x-coordinate of the planet
    private int _yCoor;          //y-coordinate of the planet
    private int _referenceNum;   //unique referencenumber for the planet
    public static int referenceNumCounter = 1;    //static counter for creating new referencenumbers			

    //Constructor
    public Planet(String name, String description, int xCoor, int yCoor, Moon moon, UUID id) {
        this._id = id;
        this._moon = moon;
        this._name = name;
        this._description = description;
        this._xCoor = xCoor;
        this._yCoor = yCoor;
        this._referenceNum = Planet.referenceNumCounter;
        Planet.referenceNumCounter++;
    }

    //Getters
    public boolean hasMoon() {
        return this._moon != null;
    }

    public UUID getId() {
        return this._id;
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
    
    public UUID getNpcId() {
        return this._npcId;
    }
    
    public void setNpcId(UUID npcId) {
        this._npcId = npcId;
    }
}
