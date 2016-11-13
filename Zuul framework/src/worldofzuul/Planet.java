package worldofzuul;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Holds all of the information regarding planets
 * @author DanielToft
 */
public class Planet {

    //Initializing variables
    private UUID _id;
    private ArrayList<UUID> _npcIds;
    private Moon _moon;
    private String _description; //a short description of the planet
    private String _name;        //name of the planet
    private int _pid;
    private int _xCoor;          //x-coordinate of the planet
    private int _yCoor;          //y-coordinate of the planet
    private int _referenceNum;   //unique referencenumber for the planet
    public static int referenceNumCounter = 1;    //static counter for creating new referencenumbers			

    //Constructor
    public Planet(String name, String description, int xCoor, int yCoor, Moon moon, int pid) {
        this._id = UUID.randomUUID();
        this._moon = moon;
        this._name = name;
        this._description = description;
        this._pid = pid;
        this._xCoor = xCoor;
        this._yCoor = yCoor;
        this._referenceNum = Planet.referenceNumCounter;
        Planet.referenceNumCounter++;
        this._npcIds = new ArrayList<>();
    }

    // ***** GETTERS *****
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
    
    public UUID[] getNpcIds() {
        UUID[] returnArray = new UUID[this._npcIds.size()];
        int i = 0;
        for(UUID uuid : this._npcIds) {
            returnArray[i] = uuid;
            i++;
        }
        return returnArray;
    }
    
    public boolean hasNpcId(UUID id) {
        return this._npcIds.contains(id);
    }
    
    public int getPid() {
        return this._pid;
    }
    // ***** GETTERS END *****
    
    // ***** SETTERS *****
    public void addNpcId(UUID npcId) {
        this._npcIds.add(npcId);
    }
    
    public void removeNpcId(UUID npcId) {
        this._npcIds.remove(npcId);
    }
    // ***** SETTERS END *****
}
