package worldofzuul;

import java.util.UUID;

/**
 * Holds all of the information regarding a moon, they don't have a position,
 * because their UUID will get assigned to a planet
 * @author DanielToft
 */
public class Moon extends NPCHolders {
    public Moon(String description, int pid) {
        super(description, pid);
    }
}
