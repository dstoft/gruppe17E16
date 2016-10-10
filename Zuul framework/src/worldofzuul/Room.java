
package worldofzuul;

import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;


public class Room 
{
    //Defined variables
    private String description;
    private HashMap<String, Room> exits;

    /**
     * Constructor, creates a room with a description (from the parameter), and creates a new HashMap with the room's exits
     * @param description used to describe the current room being created
     */
    public Room(String description) 
    {
        this.description = description;
        //The HashMap contains the exits, where strings points to a certain object handle, that handle is the room next to the current
        exits = new HashMap<String, Room>();
    }

    /**
     * Puts a string and object handle into the HashMap, using the two parameters
     * @param direction is something like west or north,
     * @param neighbor of the type Room, meaning this is an object handle that points towards the room (when going east or north)
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor); //A HashMap method to put stuff into the HashMap
    }

    /**
     * A method to return the short description of the room
     * @return 
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * A method to return the description and it's exits
     * @return 
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString();
    }

    /**
     * A method used to return a string that contains all of the possible exits (from the HashMap), separated by a ' ',
     * it uses a foreach loop to loop through the keys in the HashMap
     * @return 
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet(); //Creates a collection (similiar to an array), it holds all of the keys used in the HashMap
        for(String exit : keys) { //Foreach of those keys
            returnString += " " + exit; //Append a ' ' along with the key
        }
        return returnString;
    }

    //Gets the object handle from the HashMap based on the direction parsed in
    public Room getExit(String direction) 
    {
        return exits.get(direction); //Return the room which corresponds to the direction parsed in, in the parameter
    }
}

