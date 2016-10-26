package worldofzuul;

import java.util.ArrayList;

/**
 * This class controls the flow of the game, it contains the while loop that
 * keeps the game running even after a command has been issued, this recognizes
 * the function each command has, and processes both words.
 *
 * To use it, simply create an object of the type Game, and call the method
 * .play() Emil Bøgh Harder, Kasper H. Christensen, Malte Engelsted Rasmussen,
 * Matias Marek, Daniel Anton Jørgensen, Daniel Skjold Toft Note: Commented by
 * Gruppe 17, E16, Software/IT 1. semester
 *
 *
 * @author Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */
public class Game {

    //Defines instance variables
    private Parser parser;
    private Dashboard _dashboard;
    private Player _player;
    ArrayList<Planet> _planets;
    private MovementCalculator _movementCalculator;

    /**
     * Constructor for the class Game, using the method createRooms() it creates
     * the rooms, sets current room and creates a new parser object
     */
    public Game() {

        parser = new Parser(); //Creates a new object of the type Parser
        this._player = new Player("wow", 100, 10);
        this._dashboard = new Dashboard(); // Creates a new object of the type Dashboard. 
        this._movementCalculator = new MovementCalculator();

        //createPlanets(); 
    }

    /**
     * This is the function to call if you want to launch the game! It prints
     * the welcome message, and then it loops, taking your commands, until the
     * game ends.
     */
    public void play() {
        printWelcome(); //Prints a welcome message

        //Note, the while-loop below, is basically a do..while loop, because the value to check is set to false right before the loop itself
        //meaning, no matter what, the loop will run through at least once
        boolean finished = false;
        while (!finished) { //While it is not finished
            Command command = parser.getCommand(); //Returns a new object, holding the information, regarding the line typed by the user
            finished = processCommand(command); //Saves the boolean, whether the player wants to quit, in finished,
        }
        this._dashboard.print("Thank you for playing.  Good bye."); //Print an end statement, this only happens when the game stops
    }

    /**
     * A simple method to print a small welcome message and the description of
     * the starting room
     */
    private void printWelcome() {
        this._dashboard.print();
        this._dashboard.print("Welcome to the World of Zuul!");
        this._dashboard.print("World of Zuul is a new, incredibly boring adventure game.");
        this._dashboard.print("Type '" + CommandWord.HELP + "' if you need help."); //Command.HELP is found in the enum CommandWord, this returns the string corresponding to it
        this._dashboard.print();
    }

    /**
     * Processes a command based on the parameter. This function figures out
     * where to head whenever you send a command, it calls other methods. This
     * method only processes the first word in the command, and leaves the
     * processing of the second word to the methods it call
     *
     * @param command is an object of the type Command, it uses the class
     * CommandWord, an enum, to recognize the command parsed in through the
     * parameter
     * @return a boolean telling the program whether to quit or not, return true
     * when the player wants to quit
     */
    private boolean processCommand(Command command) {
        boolean wantToQuit = false; //Defines a variable, controls whether to quit or not

        CommandWord commandWord = command.getCommandWord(); //Returns an object held by the command object

        if (commandWord == CommandWord.UNKNOWN) { //If the command is unknown
            this._dashboard.print("I don't know what you mean..."); //Print a simple String
            return false; //Return that we do not want to quit
        }

        if (commandWord == CommandWord.HELP) { //If the command is help,
            printHelp(); //Call the method printHelp, to prrint help for the user
        } /*
        else if (commandWord == CommandWord.GO) { //If the command is go,
            goRoom(command); //Calls the method goRoom, and pass the command object along
        }
         */ else if (commandWord == CommandWord.QUIT) { //If the command is quit,
            wantToQuit = quit(command); //Use the quit() method to figure out whether the player really wants to quit, save the returned value
        } else {
            this._player.processCommand(command);
        }

        return wantToQuit; //Return the boolean, whether the player wants to quit or not
    }

    /**
     * Prints a small message regarding the game, and prints all available
     * commands
     */
    private void printHelp() {
        //Prints a few statements regarding the state of the game
        this._dashboard.print("You are lost. You are alone. You wander");
        this._dashboard.print("around at the university.");
        this._dashboard.print();
        this._dashboard.print("Your command words are:");

        parser.showCommands(); //Prints out all of the command words known to the system
    }

    /**
     * The method that gets called if you type "quit", it only quits if no
     * second word exists
     *
     * @param command this command has two words, however, this method only uses
     * the second, as the first has already been processed
     * @return true if the user has no second word, and therefore wants to quit
     */
    private boolean quit(Command command) {
        if (command.hasSecondWord()) { //If the command passed in the parameter has a second word, the quit command must be a mistake
            this._dashboard.print("Quit what?"); //meaning the game won't quit!
            return false; //Returns false, meaning the system will not quit
        } else { //If there is no second word,
            return true; //Return true, meaning the game will quit!
        }
    }

    public ArrayList<Planet> getPossiblePlanets(int startX, int startY, int currentFuel) {
        ArrayList<Planet> reachablePlanets = new ArrayList<>();
        for (Planet planet : this._planets) {
            if (this._movementCalculator.isReachable(startX, startY, planet.getXCoor(), planet.getYCoor(), currentFuel)) {
                reachablePlanets.add(planet);
            }
        }
        return reachablePlanets;
    }

    public void printAllPlanets() {
        this._dashboard.print("This is a list of all planets  ");
        String toPrint = "";
        for (Planet planet : this._planets) {
            toPrint += planet.getName() + ", ";
        }
        this._dashboard.print(toPrint);

    }

    public void printPlayerStats() {
        this._dashboard.print("this your remaining fuel level" + this._player.getFuel());

        this._dashboard.print(" This is your current Reputation " + this._player.getReputation());

    }

    public void printPlayerPosition() {
        this._dashboard.print("Current planet name:  " + this._player.getName());
        this._dashboard.print("This is your current position: " + "(" + this._player.getCurrentPlanet().getXCoor() + "," + this._player.getCurrentPlanet().getYCoor() + ")");

    }

    public void printPossiblePlanets() {
        planetList = this._movementCalculator.getPossiblePlanets(this._player.getCurrentPlanet().getXCoor(), this._player.getCurrentPlanet().getYCoor(), this._player.getFuel(), this._planets);
        for (Planet planet : this.getPossiblePlanets(int  {
            startX
        }
        , int startY, int currentFuel 
            ) ) {
          toPrint += planet.getName() + ",";

        }
        this._dashboard.print(planet.getName());
    }

// we need a method, in the player class,  // getInventory String i player klassen skal laves (), showInventory. 
    public void printInventory() {

        ArrayList<Inventory> myInventory = Inventory.inventory;
        for (Inventory inventory : myInventory) {
            this._dashboard.print(inventory.getName()); // hvad er metoden til at finde navnet på de ting vi har i inventory 
        }

    }

    // a method where i print details about a specific planet 
    // HUSK AT SLETTE, skal vi have et input for bruger, kan vi lave en case hvor at jeg matcher om det brugeren har indtastet svare til et planet navn, hvor efter jeg printer hvad der er information om planeten. Eller hvordan skal denne laves ? ? ? ?   Vi laver et for each loop, burger vi istedet da, vi ikke ved hvor langt vores case skal være, eller hvis vi i fremtiden har tænkt os at gøre det. 
    // hvis brugen taster et navn forkert, skal vi her printe " Sorry didn’t recognize that planet name, make sure to use only small characters,  do you want to see a list of all planets ?  Or a list of the planet that is reachanble for you"  // here we can refer to commands to see allPlanets or Reachable planets, tell the user what the command is perhaps ? ? ? 
    // Boolean, om vi foundWord, hvis ja display  print information til planenten ? Det skal jeg have fat i fra planet folkende. NEJ  så følg hvad jeg  har skrevet i notaten i det overstående, hvis 
    public void printSpecPlanet(String secondWord) {
        
        // her skal vi huske at taste kommando til at kunne se all planets og possible planets !!

        for (Planet planet : this._planets) {
           if(secondWord.equals(planet.getName())) {
               this._dashboard.print(planet.getDescription()); 
           } else {
                this._dashboard.print("Sorry  "+ secondWord + " was not recognized, make sure you are typing it right.");  // we could write :" Sorry + was not reconized make sure
                this._dashboard.print("If you wish to see a list of all planets press******* HALLLLÅÅÅ HER SKAL VI TASTE KOMMANDO TIL AT SE ALL PLANETS !?!?!??!!?!?!?!??!?! ") ; 
                this._dashboard.print(" If you wish to see a list of possible planets press ********* HALLLLÅÅÅ HER SKAL VI TASTE KOMMANDO TIL AT SE POSSIBLE PLANETS !?!?!??!!?!?!?!??!?")
           }
    
    
    
    
        }
    }

}
