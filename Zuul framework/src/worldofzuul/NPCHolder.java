/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuul;

import java.util.ArrayList;
import java.util.UUID;

/**
 * A super class for Moons and Planets, as they have holding an NPC in common.
 * This class holds the information about which NPC is at what planet.
 * Along with other common attributes, as id, name, pid and description.
 * @author DanielToft
 */
public abstract class NPCHolder {
    
    private UUID _id;
    private int _pid;
    private ArrayList<UUID> _npcIds;
    private String _description;
    private String _name;
    private boolean isWar;      //If there is war on the planet
    
    /**
     * Constructor
     * @param name the name of the planet / moon
     * @param description the description of the planet / moon
     * @param pid the pid, which tells the game which NPCs should be placed where at the beginning of the game.
     */
    public NPCHolder(String name, String description, int pid) {
        this._name = name;
        this._description = description;
        this._npcIds = new ArrayList<>();
        this._id = UUID.randomUUID();
        this._pid = pid;
    }
    
    // ****** GETTERS ******
    public UUID getId() {
        return this._id;
    }
    
    public int getPid() {
        return this._pid;
    }

    public String getName() {
        return this._name;
    }

    public String getDescription() {
        return this._description;
    }
    
    /**
     * Gets all of the NPC ids that is currently at the planet / moon.
     * @return an array of UUIDs
     */
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
    // ***** GETTERS END *****
    
    // ***** SETTERS *****
    public void addNpcId(UUID npcId) {
        this._npcIds.add(npcId);
    }
    
    public void removeNpcId(UUID npcId) {
        this._npcIds.remove(npcId);
    }
    // ***** SETTERS END *****
    
        private boolean setIsWarTrue() {    // Sets war to true

        return isWar = true;
    }

    private boolean setIsWarFalse() {       //sets war to false
        return isWar = false;

    }

    public boolean getIsWar() { // getters to get the variable IsWar

        return isWar;
    }

    public void warPossibility() { // The chance of war for the each planet. 
        double number = Math.random();
        System.out.println("New wars just started");

        if (number >= 0.1) {
            setIsWarTrue();
        } else {
            setIsWarFalse();
        }

    }

    
}
