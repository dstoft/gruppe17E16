package worldofzuul;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Holds all of the information regarding planets, except from NPC handling, which is handled by the super class NPCHolder
 */
public class Planet extends NPCHolder {

    //Initializing variables
    private UUID moonUuid;
    private int xCoor;          //x-coordinate of the planet
    private int yCoor;          //y-coordinate of the planet
    private int referenceNum;   //unique referencenumber for the planet
    
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
        this.xCoor = xCoor;
        this.yCoor = yCoor;
        this.referenceNum = Planet.referenceNumCounter;
        Planet.referenceNumCounter++;
    }

    // ***** GETTERS *****
    public boolean hasMoon() {
        return this.moonUuid != null;
    }
    
    public UUID getMoonUuid() {
        return this.moonUuid;
    }

    public int getXCoor() {
        return this.xCoor;
    }

    public int getYCoor() {
        return this.yCoor;
    }

    public int getReferenceNum() {
        return this.referenceNum;
    }
    // ***** GETTERS END *****
    
    // ***** SETTERS *****
    public void setMoonUuid(UUID moonId) {
        this.moonUuid = moonId;
    }
    // ***** SETTERS END *****
}
