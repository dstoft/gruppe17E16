package worldofzuulgui;

import java.util.UUID;

/**
 * A object used to hold information used in ChoiceBoxes or ListViews.
 */
public class ListHolderInformation {

    private String name;
    private UUID uuid;

    /**
     * Constructor.
     *
     * @param uuid the uuid that is to be saved and associated with the string.
     * @param name a string containing what should be shown.
     */
    public ListHolderInformation(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    /**
     * Getter for the UUID held by the class.
     *
     * @return a UUID
     */
    public UUID getUuid() {
        return this.uuid;
    }

    /**
     * When this object is used in ChoiceBoxes or ListViews, the toString method
     * is used to decide what to print in the lists.
     *
     * @return a string
     */
    @Override
    public String toString() {
        return this.name;
    }
}
