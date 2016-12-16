package worldofzuul;

import java.util.UUID;

/**
 * A class that holds the information about each scenario.
 */
class Scenario implements PrintAble {

    private String name, description, path;
    private UUID id;

    /**
     * Constructor.
     *
     * @param name the name of the scenario
     * @param description the description of the scenario
     * @param path the name of the folder holding the scenario's data files
     */
    public Scenario(String name, String description, String path) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.path = path;
    }

    // ***** GETTERS *****
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    public String getPath() {
        return this.path;
    }

    public UUID getId() {
        return this.id;
    }
    // ***** GETTERS END *****
}
