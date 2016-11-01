package worldofzuul;

import java.util.UUID;

public class Moon {

    //Initializing variables
    private String _description;
    private UUID _npcId;

    //Constructor
    public Moon(String description) {
        this._description = description;
    }

    //Getters
    public String getDescription() {
        return this._description;
    }
	

    public UUID getNpcId() {
        return this._npcId;
    }
    
    public void setNpcId(UUID npcId) {
        this._npcId = npcId;
    }
}
