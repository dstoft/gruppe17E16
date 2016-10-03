package worldofzuul;
import java.util.HashMap;

/**
 * This class is used to hold the handles to each CommandWord object (the enum)
 * 
 */
public class CommandWords
{
    private HashMap<String, CommandWord> validCommands; //Contains the commands known to the system, gotten by the enum class

    /**
     * The for each loop is used to put each command's object into the HashMap,
     * so that the handle to object can always be retrieved
     */
    public CommandWords()
    {
        validCommands = new HashMap<String, CommandWord>();
        for(CommandWord command : CommandWord.values()) {
            if(command != CommandWord.UNKNOWN) {
                validCommands.put(command.toString(), command);
            }
        }
    }

    /**
     * 
     * @param commandWord
     * @return 
     */
    public CommandWord getCommandWord(String commandWord)
    {
        CommandWord command = validCommands.get(commandWord); //Fetches the handle (from the HashMap) to the object 
                                                              //that corresponds the first command word
        if(command != null) { //If the command is valid,
            return command; //It returns the object from the HashMap
        }
        else { //If it is not a valid command
            return CommandWord.UNKNOWN; //Return the unknown enum
        }
    }
    
    public boolean isCommand(String aString)
    {
        return validCommands.containsKey(aString);
    }

    public void showAll() 
    {
        //Note: the "key" in the HashMap is the index of the HashMap, like the number inside [] in an array
        for(String command : validCommands.keySet()) { //For each row in the HashMap, save the "key"
            System.out.print(command + "  "); //print out the index, note: the command is merely a String
        }
        System.out.println(); //Print a new line at the end
    }
}
