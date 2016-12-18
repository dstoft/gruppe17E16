package worldofzuul;

/**
 * A class used for storing the lines that Game wants to have printed.
 */
public class Dashboard {

    private String savedString = "";

    /**
     * A method used to print something.
     *
     * @param toPrint what to print
     */
    public void print(String toPrint) {
        this.savedString += toPrint + "\n";
    }

    /**
     * An overloaded method, that allows Game to print a newline without writing
     * anything in the parameter list.
     */
    public void print() {
        this.savedString += "\n";
    }

    /**
     * getSavedString saves the current savedString as tempString,
     * clears savedString, and returns tempString
     *
     * @return returns the attribut tempString
     */
    public String getSavedString() {
        String tempString = this.savedString;
        this.savedString = "";
        return tempString;
    }
}
