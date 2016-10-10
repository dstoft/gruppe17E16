


package worldofzuul;

/**
 * This class controls the flow of the game, it contains the while loop that keeps the game running
 * even after a command has been issued, this recognizes the function each command has, and processes
 * both words.
 * 
 * To use it, simply create an object of the type Game, and call the method .play()
 * Emil Bøgh Harder, Kasper H. Christensen, Malte Engelsted Rasmussen, Matias Marek, Daniel Anton Jørgensen, Daniel Skjold Toft
 * Note: Commented by Gruppe 17, E16, Software/IT 1. semester
 * 
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */
public class Game 
{
    //Defines instance variables
    private Parser parser;
    private Room currentRoom;
        
    /**
     * Constructor for the class Game, using the method createRooms() it creates the rooms, sets current room 
     * and creates a new parser object
     */
    public Game() 
    {
        createRooms(); //Calls the method that creates all of the rooms
        parser = new Parser(); //Creates a new object of the type Parser
    }

    /**
     * A method used to create the rooms, 
     * if you want to change the layout of the rooms, this is where you should do it!
     */
    private void createRooms()
    {
        //Defines variables of the type Room
        Room outside, theatre, pub, lab, office;
      
        //Creates the new objects and creates the description along with it
        outside = new Room("outside the main entrance of the university");
        theatre = new Room("in a lecture theatre");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        
        //Sets the exits for the Room "outside"
        outside.setExit("east", theatre);
        outside.setExit("south", lab);
        outside.setExit("west", pub);

        //Sets the exits for the Room "theatre"
        theatre.setExit("west", outside);
        
        //....
        pub.setExit("east", outside);

        //....
        lab.setExit("north", outside);
        lab.setExit("east", office);

        //....
        office.setExit("west", lab);

        //Sets the current room to the Room Outside
        currentRoom = outside;
    }

    /**
     * This is the function to call if you want to launch the game! It prints the welcome message,
     * and then it loops, taking your commands, until the game ends.
     */
    public void play() 
    {            
        printWelcome(); //Prints a welcome message

        //Note, the while-loop below, is basically a do..while loop, because the value to check is set to false right before the loop itself
        //meaning, no matter what, the loop will run through at least once
        boolean finished = false;
        while (! finished) { //While it is not finished
            Command command = parser.getCommand(); //Returns a new object, holding the information, regarding the line typed by the user
            finished = processCommand(command); //Saves the boolean, whether the player wants to quit, in finished,
        }
        System.out.println("Thank you for playing.  Good bye."); //Print an end statement, this only happens when the game stops
    }

    /**
     * A simple method to print a small welcome message and the description of the starting room
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help."); //Command.HELP is found in the enum CommandWord, this returns the string corresponding to it
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Processes a command based on the parameter. This function figures out where to head whenever you send a command,
     * it calls other methods. This method only processes the first word in the command,
     * and leaves the processing of the second word to the methods it call
     * @param command is an object of the type Command, it uses the class CommandWord, an enum,
     * to recognize the command parsed in through the parameter
     * @return a boolean telling the program whether to quit or not, return true when the player wants to quit
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false; //Defines a variable, controls whether to quit or not

        CommandWord commandWord = command.getCommandWord(); //Returns an object held by the command object

        if(commandWord == CommandWord.UNKNOWN) { //If the command is unknown
            System.out.println("I don't know what you mean..."); //Print a simple String
            return false; //Return that we do not want to quit
        }

        if (commandWord == CommandWord.HELP) { //If the command is help,
            printHelp(); //Call the method printHelp, to prrint help for the user
        }
        else if (commandWord == CommandWord.GO) { //If the command is go,
            goRoom(command); //Calls the method goRoom, and pass the command object along
        }
        else if (commandWord == CommandWord.QUIT) { //If the command is quit,
            wantToQuit = quit(command); //Use the quit() method to figure out whether the player really wants to quit, save the returned value
        }
        return wantToQuit; //Return the boolean, whether the player wants to quit or not
    }

    /**
     * Prints a small message regarding the game, and prints all available commands
     */
    private void printHelp() 
    {
        //Prints a few statements regarding the state of the game
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        
        parser.showCommands(); //Prints out all of the command words known to the system
    }

    /**
     * A method which recognizes which room you want to go to (or not, if the second word is not valid),
     * this is only called when you have already checked that the first word is "go"
     * @param command this command has two words, however, this method only uses the second,
     * as the first has already been processed
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) { //If the command does not have a second word,
            System.out.println("Go where?"); //Prints a simple statement
            return; //Quit the current method (no more code in this method gets executed
        }

        String direction = command.getSecondWord(); //Save the second word from the Command object in a String

        Room nextRoom = currentRoom.getExit(direction); //Gets the room that corresponds to the exit (it is returned from the Room object)

        if (nextRoom == null) { //If the room is not a valid/known room
            System.out.println("There is no door!"); //Print a simple statement
        }
        else { //If it is a valid room
            currentRoom = nextRoom; //Set the current room to the next room, meaning the character just moved!
            System.out.println(currentRoom.getLongDescription()); //Print the description of the current room
        }
    }

    /**
     * The method that gets called if you type "quit", it only quits if no second word exists
     * @param command this command has two words, however, this method only uses the second,
     * as the first has already been processed
     * @return true if the user has no second word, and therefore wants to quit
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) { //If the command passed in the parameter has a second word, the quit command must be a mistake
            System.out.println("Quit what?"); //meaning the game won't quit!
            return false; //Returns false, meaning the system will not quit
        }
        else { //If there is no second word,
            return true; //Return true, meaning the game will quit!
        }
    }
}




