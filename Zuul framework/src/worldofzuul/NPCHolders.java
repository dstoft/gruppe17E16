/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuul;

import java.util.ArrayList;
import java.util.UUID;

/**
 *
 * @author DanielToft
 */
public abstract class NPCHolders {
    
    private UUID _id;
    private int _pid;
    private ArrayList<UUID> _npcIds;
    private String _description;
    
    public NPCHolders(String description, int pid) {
        this._description = description;
        this._npcIds = new ArrayList<>();
        this._id = UUID.randomUUID();
        this._pid = pid;
    }
    
    public UUID getId() {
        return this._id;
    }
    
    public int getPid() {
        return this._pid;
    }

    public String getDescription() {
        return this._description;
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
    
    // ***** SETTERS *****
    public void addNpcId(UUID npcId) {
        this._npcIds.add(npcId);
    }
    
    public void removeNpcId(UUID npcId) {
        this._npcIds.remove(npcId);
    }
    // ***** SETTERS END *****
}
