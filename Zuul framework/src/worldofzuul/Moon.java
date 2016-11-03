package worldofzuul;

import java.util.UUID;

/**
 * Holds all of the information regarding a moon, they don't have a position,
 * because their UUID will get assigned to a planet
 * @author DanielToft
 */
public class Moon {

    //Initializing variables
    private String _description;
    private UUID _npcId;
    private UUID _Uuid;

    //Constructor
    public Moon(String description) {
        this._description = description;
    }

    // ***** GETTERS *****
    public String getDescription() {
        return this._description;
    }
    
    public UUID getId() {
        return this._Uuid;
    }

    public UUID getNpcId() {
        return this._npcId;
    }
    // ***** GETTERS END *****
    
    // ***** SETTERS *****
    public void setNpcId(UUID npcId) {
        this._npcId = npcId;
    }
    // ***** SETTERS END *****
}
