package worldofzuul;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

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
    HashMap<UUID, Planet> _planets;
    HashMap<UUID, NPC> _npcs;
    private MovementCalculator _movementCalculator;
    private Conversation _currentConversation;

    /**
     * Constructor for the class Game, using the method createRooms() it creates
     * the rooms, sets current room and creates a new parser object
     */
    public Game() {
        this._planets = new HashMap<UUID, Planet>();
        UUID currentPlanetId = this.createPlanets();
        
        
        parser = new Parser(); //Creates a new object of the type Parser
        this._player = new Player(currentPlanetId, 100, 10);
        this._dashboard = new Dashboard(); // Creates a new object of the type Dashboard. 
        this._movementCalculator = new MovementCalculator();

        //createPlanets(); 
        //createNpcs();
    }

    /**
     * This is the function to call if you want to launch the game! It prints
     * the welcome message, and then it loops, taking your commands, until the
     * game ends.
     */
    public void play() {
        printWelcome(); //Prints a welcome message
        
        System.out.println("NOTE: MAKE THE PLAYER GO TO THE FIRST PLANET WHEN PRESSING PLAY, THAT WAY THE FIRST CONVERSATION WILL START!");

        //Note, the while-loop below, is basically a do..while loop, because the value to check is set to false right before the loop itself
        //meaning, no matter what, the loop will run through at least once
        boolean finished = false;
        while (!finished) { //While it is not finished
            this._dashboard.print();
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
        } else if (commandWord == CommandWord.QUIT) { //If the command is quit,
            wantToQuit = quit(command); //Use the quit() method to figure out whether the player really wants to quit, save the returned value
        } else if (commandWord == CommandWord.GO) { //If the command is go,
            //Here comes a movementment method from the class MovementCalculator, which is extended!
            UUID planetId = this.getPlanetIdFromReferenceNumber(command.getSecondWord());
            if(planetId == null) { return false; }
            this.travelToPlanet(this._player, planetId);
            this.startConversation();
        } else if (commandWord == CommandWord.DROP) {
            this.dropItem(command.getSecondWord());
        } else if (commandWord == CommandWord.PRINT) {
            this.whichPrint(command.getSecondWord());
        } else if (commandWord == CommandWord.SCAN) {
            this.whichScan(command.getSecondWord());
        } else if (commandWord == CommandWord.SAY) {
            this.processAnswer(command.getSecondWord());
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

    /**
     * Calculates the planets that a certain position can travel to, based on the amount of fuel.
     * Uses Game's list of all planets, and movementcalculator
     * @param startX starting position
     * @param startY starting position
     * @param currentFuel the amount of fuel that can be expended
     * @return a list of planets that are possible to travel to
     */
    public ArrayList<Planet> getPossiblePlanets(int startX, int startY, int currentFuel) {
        ArrayList<Planet> reachablePlanets = new ArrayList<>();
        for (Planet planet : this._planets.values()) {
            if (this._movementCalculator.isReachable(startX, startY, planet.getXCoor(), planet.getYCoor(), currentFuel)) {
                reachablePlanets.add(planet);
            }
        }
        return reachablePlanets;
    }

    /**
     * A method to figuring out what is to happen based on the second word
     * @param secondWord the second word that the user typed in
     */
    public void whichScan(String secondWord) {
        if(secondWord == null) {
            this._dashboard.print("The second word in the command was not recognized, please use one of the following second words (like \"scan all\"):");
            this._dashboard.print("\"all\", for printing all planets\n\"possible\", for printing all planets you can reach\n[planet id], for getting the description of a specific planet");
            return;
        }
        
        if(secondWord.equals("all")) {
            this.printAllPlanets();
        } else if(secondWord.equals("possible")) {
            this.printPossiblePlanets();
        } else if(this.printSpecPlanet(secondWord)) {
            
        } else {
            this._dashboard.print("\"" + secondWord + "\" was not recognized, please use: "
                    + "\n\t\"scan all\" for showing all planets and their ids,"
                    + "\n\t\"scan possible\" for showing all planets and their ids you can travel to,"
                    + "\n\t\"scan [id]\" for showing a specific id, the id can be found like: [id:planet name] when scanning possible or all planets.");
        }
    }
    
    /**
     * A method for printing all planets
     */
    public void printAllPlanets() {
        this._dashboard.print("This is a list of all planets and their ids:");
        String toPrint = "";
        for (Planet planet : this._planets.values()) {
            toPrint += planet.getReferenceNum() + ": " + planet.getName() + ", ";
        }
        this._dashboard.print(toPrint);

    }
    
    /**
     * Print the possible planets that the player can travel to
     */
    public void printPossiblePlanets() {
        String toPrint = "";
        UUID currentPlanetId = this._player.getCurrentPlanetId();
        ArrayList<Planet> planetList = this.getPossiblePlanets(this._planets.get(currentPlanetId).getXCoor(), this._planets.get(currentPlanetId).getYCoor(), this._player.getFuel());
        for (Planet planet : planetList) {
            if(this._player.getCurrentPlanetId() == planet.getId()) { continue; }
            toPrint += planet.getReferenceNum() + ": " + planet.getName() + ", ";
        }
        this._dashboard.print(toPrint);
    }

    /**
     * A method to figuring out what is to happen based on the second word
     * @param secondWord the second word that the user typed in
     */
    public void whichPrint(String secondWord) {
        if(secondWord == null) {
            this._dashboard.print("The second word in the command was not recognized, please use one of the following second words (like \"print stats\"):");
            this._dashboard.print("\"stats\", for viewing your stats\n\"position\", for viewing your position\n\"inventory\", for getting information about your inventory");
            return;
        }
        
        if(secondWord.equals("stats")) {
            this.printPlayerStats();
        } else if(secondWord.equals("position")) {
            this.printPlayerPosition();
        } else if(secondWord.equals("inventory")) {
            this.printInventory();
        } else {
            this._dashboard.print("The second word you wrote is not recognized, please only use: stats, position or invetory!");
        }
    }
    
    /**
     * A method for printing the player's stats
     */
    public void printPlayerStats() {
        this._dashboard.print("Current fuel: " + this._player.getFuel());
        this._dashboard.print("Current reputation: " + this._player.getReputation());

    }

    /**
     * Prints the player's current planet's position and name
     */
    public void printPlayerPosition() {
        UUID currentPlanetId = this._player.getCurrentPlanetId();
        this._dashboard.print("Current planet name:  " + this._planets.get(currentPlanetId).getName());
        this._dashboard.print("This is your current position: " + "(" + this._planets.get(currentPlanetId).getXCoor() + ";" + this._planets.get(currentPlanetId).getYCoor() + ")");
    }

    /**
     * Prints information about the inventory, if it is empty, it does not tell the player how to drop an item
     */
    public void printInventory() {
        if(this._player.getInventoryString() != null) {
            this._dashboard.print(this._player.getInventoryString());
            this._dashboard.print("To drop an item, write \"drop [id]\", using the id from [id:item name].");
        } else {
            this._dashboard.print("You have an empty inventory!");
        }
    }

    /**
     * A method for getting information regarding a specific planet
     * @param secondWord the second word that the user typed in
     * @return whether or not the secondWord refered to a planet
     */
    public boolean printSpecPlanet(String secondWord) {
        //Change it to int, and then find that number in the planets list!
        //Remember to add "try catch"!
        UUID id = this.getPlanetIdFromReferenceNumber(secondWord);
        if(id == null) {
            return false;
        } else {
            this._planets.get(id).getDescription();
            return true;
        }
    }
    
    /**
     * Changes the position (planet) of the character refered in the parameter
     * @param characterToTravel which character to move
     * @param planetId which planet to move to
     */
    public void travelToPlanet(Player characterToTravel, UUID planetId) {
        this._dashboard.print("Now traveling to " + this._planets.get(planetId).getName());
        characterToTravel.setCurrentPlanet(planetId);
        
        this._dashboard.print("Refilled fuel tank!");
        this._player.setFuel(this._player.getMaxFuel());
    }
    
    /**
     * A method for starting a conversation with the NPC on the planet, that the player is currently at
     */
    public void startConversation() {
        // Starting conversation!
    }
    
    /**
     * Takes in what the user has answered using the say command, and figures out whether it is recognized
     * It also calls for the execution of the execution line
     * @param answer is the second word that the user typed in along with say
     */
    public void processAnswer(String answer) {
        if(answer == null) {
            this._dashboard.print("You have to say something!");
            return;
        }
        
        if(this._currentConversation == null) {
            this._dashboard.print("Sorry, you can't use say when you have no ongoing conversation!");
            return;
        }
        
        UUID npcId = this._planets.get(this._player.getCurrentPlanetId()).getNpcId();
        if(npcId != this._currentConversation.getNpcId()) {
            this._dashboard.print("Sorry, you're no longer at the same position as the NPC and can therefore not talk with him!");
            this._currentConversation = null;
            return;
        }
        
        this._currentConversation.processAnswer(answer.toLowerCase());
        if(this._currentConversation.hasCurrentAnswer()) {
            this._dashboard.print(this._currentConversation.getReactText());
            if(this.processExecution(this._currentConversation.getExecutionLine(), npcId)) {
                this._currentConversation.setNextQuestion(this._currentConversation.getNextLineNumber());
            }
            this._dashboard.print(this._currentConversation.getQText());
        } else {
            this._dashboard.print("Sorry, I don't know how to respond to that answer.");
            this._dashboard.print(this._currentConversation.getPossibleAnswers());
        }
    }
    
    /**
     * Figures out what should happen according to the parameter executionLine, and calls the relevant methods
     * @param executionLine which commands that are to be executed
     * @param npcId which npc that the conversation is with
     * @return whether or not the conversation's question was changed during the execution commands
     */
    public boolean processExecution(String executionLine, UUID npcId) {
        boolean changedQuestion = false;
        String[] allExecutions;
        allExecutions = executionLine.split(",");
        for(String eachExecution : allExecutions) {
            String[] executionSplit = eachExecution.split(":");
            if(executionSplit[0].equals("deliverPackage")) {
                int[] rids = this._player.getInventoryRids();
                for(int i = 0; i < rids.length; i++) {
                    if(rids[i] == this._npcs.get(npcId).getRid()) {
                        //Is that all that should happen when delivering a package?
                        this._player.dropItem(i);
                        //Add reputation!
                    } else {
                        //The player does not have the package, how did it get so far then?
                    }
                }
            } else if(executionSplit[0].equals("pickupPackage")) {
                //Where should the conversation go if you do not have space?
                int[] rids = this._npcs.get(npcId).getRids();
                for(int i = 0; i < rids.length; i++) {
                    if(this._player.addItem(this._npcs.get(npcId).getItemInfo(rids[i]))) {
                        //THERE WAS SPACE! YAY!
                        this._npcs.get(npcId).removeItem(rids[i]);
                    } else {
                        //WHAT IF THERE WAS NOT SPACE?!
                    }
                }
                
            } else if(executionSplit[0].equals("nextConvoId")) {
                try {
                    int convoId = Integer.parseInt(executionSplit[1]);
                    this._npcs.get(npcId).setNextConversationId(convoId);
                } catch(NumberFormatException e) {
                    
                }
            } else if(executionSplit[0].equals("checkPackage")) {
                String[] whichQuestion = executionSplit[1].split("|");
                int[] questionNumbers = new int[2];
                try {
                    questionNumbers[0] = Integer.parseInt(whichQuestion[0]);
                    questionNumbers[1] = Integer.parseInt(whichQuestion[1]);
                } catch(NumberFormatException e) {
                    
                }
                
                int[] playerRids = this._player.getInventoryRids();
                for(int i = 0; i < playerRids.length; i++) {
                    if(this._npcs.get(npcId).getRid() == playerRids[i]) {
                        this._currentConversation.setNextQuestion(questionNumbers[0]);
                        changedQuestion = true;
                        break;
                    }
                }
                if(!changedQuestion) {
                    this._currentConversation.setNextQuestion(questionNumbers[1]);
                    changedQuestion = true;
                }
            } else if(executionSplit[0].equals("checkPickup")) {
                String[] whichQuestion = executionSplit[1].split("|");
                int[] questionNumbers = new int[2];
                try {
                    questionNumbers[0] = Integer.parseInt(whichQuestion[0]);
                    questionNumbers[1] = Integer.parseInt(whichQuestion[1]);
                } catch(NumberFormatException e) {
                    
                }
                
                int[] npcRids = this._npcs.get(npcId).getRids();
                for(int i = 0; i < npcRids.length; i++) {
                    if(this._npcs.get(npcId).getRid() == npcRids[i]) {
                        this._currentConversation.setNextQuestion(questionNumbers[0]);
                        changedQuestion = true;
                        break;
                    }
                }
                if(!changedQuestion) {
                    this._currentConversation.setNextQuestion(questionNumbers[1]);
                    changedQuestion = true;
                }
            } else if(executionSplit[0].equals("removeReputation")) {
                try {
                    int reputationAmount = Integer.parseInt(executionSplit[1]);
                    this._player.setReputation((this._player.getReputation()-reputationAmount));
                } catch(NumberFormatException e) {
                    
                }
            }
        }
        
        return changedQuestion;
    }
    
    /**
     * Changes a planet reference number to the planet's UUID.
     * Can catch an exception
     * @param secondWord the second word that the user typed in
     * @return the UUID of the corresponding planet
     */
    public UUID getPlanetIdFromReferenceNumber(String secondWord) {
        int planetNumber = -1;
        try {
            planetNumber = Integer.parseInt(secondWord);
        } catch (Exception e) {
            this._dashboard.print("Please only use id numbers to refer to which planet you want to travel to!");
            //this._dashboard.print(e.toString());
        }
        
        for(Planet planet : this._planets.values()) {
            if(planetNumber == planet.getReferenceNum()) {
                return planet.getId();
            }
        }
        
        //Print the valid planet names!
        return null;
    }
    
    /**
     * Drops an item according to it's id, this method also attempts to change the input to an integer
     * @param itemName the second word that the user typed in
     */
    public void dropItem(String itemName) {
        if(itemName == null) {
            this._dashboard.print("The second word in the command was not recognized, please use a number as the second word (like \"drop 1\")");
            return;
        }
        
        int itemNumber = -1;
        try {
            itemNumber = Integer.parseInt(itemName);
        } catch(Exception e) {
            this._dashboard.print("Please only use the id numbers to refer to which item you want to drop!");
            //this._dashboard.print(e.toString());
        }
        
        if(!this._player.dropItem(itemNumber)) {
            this._dashboard.print("Invalid item id, \"" + itemName + "\" was not recognized, use \"print inventory\" to show your items and their ids!");
        }
    }

    /**
     * Creates the planets!
     * @return what UUID the player should be starting on
     */
    public UUID createPlanets() {
        UUID curUUID = UUID.randomUUID();
        this._planets.put(curUUID, new Planet("hej", "wow!", 1, 1, new Moon("wow1 moon!"), curUUID));
        
        UUID starterUUID = UUID.randomUUID();
        this._planets.put(starterUUID, new Planet("Starter!", "starterdesc!", 20, 20, new Moon("wowmoon2!"), starterUUID));
        return starterUUID;
    }
    
    /**
     * Creates the NPCs
     */
    public void createNpcs() {
        //A method for creating NPCs
    }
}
