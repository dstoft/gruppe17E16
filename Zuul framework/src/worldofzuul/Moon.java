package worldofzuul;

import java.util.UUID;

/**
 * Holds all of the information regarding a moon, they don't have a position,
 * because their UUID will get assigned to a planet
 * @author DanielToft
 */
public class Moon extends NPCHolder {
    
    private UUID parentPlanetUuid;
    
    public Moon(String name, String description, int pid) {
        super(name, description, pid);
    }
    
    public UUID getParentPlanetUuid() {
        return this.parentPlanetUuid;
    }
    
    public void setParentPlanetUuid(UUID parentPlanetUuid) {
        this.parentPlanetUuid = parentPlanetUuid;
    }
}
