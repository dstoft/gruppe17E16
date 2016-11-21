package worldofzuul;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Holds all of the information regarding planets, except from NPC handling, which is handled by the super class NPCHolder
 */
public class Planet extends NPCHolder {

    //Initializing variables
    private UUID _moonUuid;
    private int _xCoor;          //x-coordinate of the planet
    private int _yCoor;          //y-coordinate of the planet
    private int _referenceNum;   //unique referencenumber for the planet
    
    //static counter for creating new referencenumbers, it starts at one, because the moon always has the number 0
    public static int referenceNumCounter = 1;    		

    /**
     * Constructor
     * @param name of the planet
     * @param description of the planet
     * @param xCoor the x coordinate of the planet
     * @param yCoor the y coordinate of the planet
     * @param pid the "planet id" of the planet, which tells NPC where they should be placed by the start of the game
     */
    public Planet(String name, String description, int xCoor, int yCoor, int pid) {
        super(name, description, pid);
        this._xCoor = xCoor;
        this._yCoor = yCoor;
        this._referenceNum = Planet.referenceNumCounter;
        Planet.referenceNumCounter++;
    }

    // ***** GETTERS *****
    public boolean hasMoon() {
        return this._moonUuid != null;
    }
    
    public UUID getMoonUuid() {
        return this._moonUuid;
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
    // ***** GETTERS END *****
    
    // ***** SETTERS *****
    public void setMoonUuid(UUID moonId) {
        this._moonUuid = moonId;
    }
    // ***** SETTERS END *****
}
