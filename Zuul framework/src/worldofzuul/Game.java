package worldofzuul;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

/**
 * This class controls the flow of the game, it contains the while loop that keeps the game running even after a command has been issued, this recognizes the function each command has, and processes both words.
 *
 * To use it, simply create an object of the type Game, and call the method .play(). Written by Emil Bøgh Harder, Kasper H. Christensen, Malte Engelsted Rasmussen, Matias Marek, Daniel Anton Jørgensen & Daniel Skjold Toft. Note: Commented by Gruppe 17, E16, Software/IT 1. semester
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
    private HashMap<UUID, Planet> _planets;
    private HashMap<UUID, Moon> _moons;
    
    /**
     * Three maps of NPCs, the first one, npcs, holds all of the npcs,
     * which is used for starting a conversation, figuring out which npc is adressed, etc.
     * The difference of civilians and rebels are simply their movement pattern.
     * Rebels only move to and from moons, where civilians only move to and from planets.
     * The creation of a list allows methods of handling both civilians and rebels,
     * when there is no difference, f.ex. creation of a new conversation.
     * The only thing needed by the NPC is the conversation id, which is common for both NPC types.
     * The only reason for the separation into two maps, is purely due to the movement pattern,
     * as that is the only thing that separates them.
     */
    private HashMap<UUID, NPC> _npcs; 
    private HashMap<UUID, NPC> _civilians;
    private HashMap<UUID, NPC> _rebels;
    private HashMap<UUID, Items> _items;
    private MovementCalculator _movementCalculator;
    private FileHandler _fileHandler;
    private Conversation _currentConversation;
    private int time;
    private UUID _startingPlanet;

    /**
     * Constructor for the class Game, using the method createRooms() it creates the rooms, sets current room and creates a new parser object
     */
    public Game() {
        this._planets = new HashMap<>();
        this._moons = new HashMap<>();
        this._npcs = new HashMap<>();
        this._civilians = new HashMap<>();
        this._rebels = new HashMap<>();
        this._items = new HashMap<>();
        this._movementCalculator = new MovementCalculator();
        this._fileHandler = new FileHandler();
        
        this._startingPlanet = this.createPlanets();
        this.createNpcs();
        this.createItems();
        this.time = 0;

        parser = new Parser(); //Creates a new object of the type Parser
        this._player = new Player(this._startingPlanet, 100, 10);
        this._dashboard = new Dashboard(); // Creates a new object of the type Dashboard. 
        

        //createPlanets(); 
        //createNpcs();
    }

    /**
     * This is the function to call if you want to launch the game! It prints the welcome message, and then it loops, taking your commands, until the game ends.
     */
    public void play() {
        printWelcome(); //Prints a welcome message
<<<<<<< HEAD

        this.printHighScore();
        
        this._player.setCurrentPlanet(this._startingPlanet);
        
        //Start conversation or use the greet command for first encounter?
        this.startConversation(this._planets.get(this._player.getPlanetId()).getNpcIds()[0]);
        
=======

        System.out.println("NOTE: MAKE THE PLAYER GO TO THE FIRST PLANET WHEN PRESSING PLAY, THAT WAY THE FIRST CONVERSATION WILL START!");

        this.travelToPlanet(this._player, this._startingPlanet);

>>>>>>> Papers
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
     * A simple method to print a small welcome message and the description of the starting room
     */
    private void printWelcome() {
        this._dashboard.print();
        this._dashboard.print("Welcome to the World of Zuul!");
        this._dashboard.print("World of Zuul is a new, incredibly boring adventure game.");
        this._dashboard.print("Type '" + CommandWord.HELP + "' if you need help."); //Command.HELP is found in the enum CommandWord, this returns the string corresponding to it
    }

    /**
     * Processes a command based on the parameter. This function figures out where to head whenever you send a command, it calls other methods. This method only processes the first word in the command, and leaves the processing of the second word to the methods it call
     *
     * @param command is an object of the type Command, it uses the class CommandWord, an enum, to recognize the command parsed in through the parameter
     * @return a boolean telling the program whether to quit or not, return true when the player wants to quit
     */
    private boolean processCommand(Command command) {
        boolean wantToQuit = false; //Defines a variable, controls whether to quit or not

        CommandWord commandWord = command.getCommandWord(); //Returns an object held by the command object

        if (commandWord == CommandWord.UNKNOWN) { //If the command is unknown
            this._dashboard.print("I don't know what you mean..."); //Print a simple String
            incrementTime(1); // Adds 1 to the time if a person types wrong
            return false; //Return that we do not want to quit
        }

        if (commandWord == CommandWord.HELP) { //If the command is help,
            printHelp(); //Call the method printHelp, to prrint help for the user
            incrementTime(1); // Adds 1 to the time
        } else if (commandWord == CommandWord.QUIT) { //If the command is quit,
            wantToQuit = quit(command); //Use the quit() method to figure out whether the player really wants to quit, save the returned value
            incrementTime(1); // Adds 1 to the time
        } else if (commandWord == CommandWord.GO) { //If the command is go,
            //Here comes a movementment method from the class MovementCalculator, which is extended.
            UUID planetId = this.getPlanetIdFromReferenceNumber(command.getSecondWord());
            if (planetId == null) {
                return false;
            }
            this.travelToPlanet(this._player, planetId);
            
        } else if (commandWord == CommandWord.DROP) {
            this.dropItem(command.getSecondWord());
            incrementTime(1); // Adds 1 to the time
        } else if (commandWord == CommandWord.PRINT) {
            this.whichPrint(command.getSecondWord());
            incrementTime(1); // Adds 1 to the time
        } else if (commandWord == CommandWord.SCAN) {
            this.whichScan(command.getSecondWord());
            incrementTime(1); // Adds 1 to the time
        } else if (commandWord == CommandWord.SAY) {
            this.processAnswer(command.getSecondWord());
<<<<<<< HEAD
        } else if(commandWord == CommandWord.GREET) {
            this.processGreet(command.getSecondWord());
        } else if(commandWord == CommandWord.WARP) {
            UUID planetId = this.getPlanetIdFromReferenceNumber(command.getSecondWord());
            if(planetId == null) { return false; }
            this.processWarp(this._player, planetId);
=======
            incrementTime(1); // Adds 1 to the time
>>>>>>> Papers
        }

        return wantToQuit; //Return the boolean, whether the player wants to quit or not
    }

    /**
     * Prints a small message regarding the game, and prints all available commands
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
     * The method that gets called if you type "quit", it only quits if no second word exists
     *
     * @param command this command has two words, however, this method only uses the second, as the first has already been processed
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
     * Calculates the planets that a certain position can travel to, based on the amount of fuel. Uses Game's list of all planets, and movementcalculator
     *
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
     *
     * @param secondWord the second word that the user typed in
     */
    public void whichScan(String secondWord) {
        if (secondWord == null) {
            this._dashboard.print("The second word in the command was not recognized, please use one of the following second words (like \"scan all\"):");
            this._dashboard.print("\"all\", for printing all planets\n\"possible\", for printing all planets you can reach\n[planet id], for getting the description of a specific planet");
            return;
        }

        if (secondWord.equals("all")) {
            this.printAllPlanets();
        } else if (secondWord.equals("possible")) {
            this.printPossiblePlanets();
<<<<<<< HEAD
        } else if(secondWord.equals("npcs")) {
            this.printPossibleNpcs();
        } else if(this.printSpecPlanet(secondWord)) {
            
=======
        } else if (this.printSpecPlanet(secondWord)) {

>>>>>>> Papers
        } else {
            this._dashboard.print("\"" + secondWord + "\" was not recognized, please use: "
                    + "\n\t\"scan all\" for showing all planets and their ids,"
                    + "\n\t\"scan possible\" for showing all planets and their ids you can travel to,"
                    + "\n\t\"scan npcs\" for showing all NPCs on this planet and their ids, that you can \"greet [id]\","
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
        UUID currentPlanetId = this._player.getPlanetId();
        int[] currentPosition = getPositionCoordinates(currentPlanetId);
        
        if(this._planets.containsKey(currentPlanetId)) {
            if(this._planets.get(currentPlanetId).hasMoon()) {
                Moon moon = this._moons.get(this._planets.get(currentPlanetId).getMoonUuid());
                toPrint += "0: " + moon.getName() + ", ";
            }
        }
        
        ArrayList<Planet> planetList = this.getPossiblePlanets(currentPosition[0], currentPosition[0], this._player.getFuel());
        for (Planet planet : planetList) {
            if (this._player.getPlanetId() == planet.getId()) {
                continue;
            }
            toPrint += planet.getReferenceNum() + ": " + planet.getName() + ", ";
        }
        this._dashboard.print(toPrint);
    }
    
    /**
     * Prints the NPCs that the user can currently talk to, f.ex. when arriving a planet or moon.
     * This method can be called during runtime using the command "scan npcs".
     */
    public void printPossibleNpcs() {
        NPCHolder npcHolder = getNPCHolderFromUuid(this._player.getPlanetId());
        
        if(npcHolder.getNpcIds().length < 0) {
            this._dashboard.print("There is no NPCs to talk to at this location!");
            return;
        }
        
        this._dashboard.print("These are the NPCs you can talk to here: ");
        for(UUID npcUuid : npcHolder.getNpcIds()) {
            NPC npc = this._npcs.get(npcUuid);
            this._dashboard.print(npc.getReferenceNumber() + ": " + npc.getName() + " is described as " + npc.getDescription());
        }
        this._dashboard.print("Use the command \"greet [id]\" to start a conversation with the NPC.");
    }

    /**
     * A method to figuring out what is to happen based on the second word
     *
     * @param secondWord the second word that the user typed in
     */
    public void whichPrint(String secondWord) {
        if (secondWord == null) {
            this._dashboard.print("The second word in the command was not recognized, please use one of the following second words (like \"print stats\"):");
            this._dashboard.print("\"stats\", for viewing your stats\n\"position\", for viewing your position\n\"inventory\", for getting information about your inventory");
            return;
        }

        if (secondWord.equals("stats")) {
            this.printPlayerStats();
        } else if (secondWord.equals("position")) {
            this.printPlayerPosition();
        } else if (secondWord.equals("inventory")) {
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
        this._dashboard.print("You have used" + this.checkTimers() + "time");
    }

    /**
     * Prints the player's current planet's position and name
     */
    public void printPlayerPosition() {
        UUID currentPlanetId = this._player.getPlanetId();
        NPCHolder npcHolder = getNPCHolderFromUuid(currentPlanetId);
        int[] currentPosition = getPositionCoordinates(currentPlanetId);
        this._dashboard.print("Current planet name:  " + npcHolder.getName());
        this._dashboard.print("This is your current position: " + "(" + currentPosition[0] + ";" + currentPosition[1] + ")");
    }

    /**
     * Prints information about the inventory, if it is empty, it does not tell the player how to drop an item
     */
    public void printInventory() {
        for (UUID uuid : this._player.getInventoryUuids()) {
            Items curItems = this._items.get(uuid);
            this._dashboard.print(curItems.getReferenceNumber() + ": " + curItems.getDescription() + " weighting " + curItems.getWeight());
            
            if(this._planets.containsKey(this._npcs.get(curItems.getNpcId()).getPlanetId())) {
                Planet deliveryPlanet = this._planets.get(this._npcs.get(curItems.getNpcId()).getPlanetId());
                this._dashboard.print(" - and it has to be delivered at: [" + deliveryPlanet.getXCoor() + ";" + deliveryPlanet.getYCoor() + "] " + deliveryPlanet.getName());
            } else {
                Moon deliveryMoon = this._moons.get(this._npcs.get(curItems.getNpcId()).getPlanetId());
                Planet parentPlanet = this._planets.get(deliveryMoon.getParentPlanetUuid());
                this._dashboard.print(" - and it has to be delivered at the moon called " + deliveryMoon.getName() + " of the planet: [" + parentPlanet.getXCoor() + ";" + parentPlanet.getYCoor() + "] " + parentPlanet.getName());
            }
        }
    }

    /**
     * A method for getting information regarding a specific planet
     *
     * @param secondWord the second word that the user typed in
     * @return whether or not the secondWord refered to a planet
     */
    public boolean printSpecPlanet(String secondWord) {
        //Change it to int, and then find that number in the planets list!
        //Remember to add "try catch"!
        UUID id = this.getPlanetIdFromReferenceNumber(secondWord);
        if (id == null) {
            return false;
        } else {
            this._planets.get(id).getDescription();
            return true;
        }
    }

    /**
     * Changes the position (planet) of the character refered in the parameter
     *
     * @param characterToTravel which character to move
     * @param planetId which planet to move to
     */
<<<<<<< HEAD
    public void travelToPlanet(Player characterToTravel, UUID nextPositionUuid) {
        int[] currentPosition = getPositionCoordinates(this._player.getPlanetId());
        int[] nextPosition = getPositionCoordinates(nextPositionUuid);
        NPCHolder nextNpcHolder = getNPCHolderFromUuid(nextPositionUuid);
        
        if(this._movementCalculator.isReachable(currentPosition[0], currentPosition[1], nextPosition[0], nextPosition[1], characterToTravel.getFuel())) {
            this._dashboard.print("Now traveling to " + nextNpcHolder.getName());
            characterToTravel.setCurrentPlanet(nextPositionUuid);
            
            tryNpcMovement();
            
            this._dashboard.print("Refilled fuel tank!");
            this._player.setFuel(this._player.getMaxFuel());
            
            this._dashboard.print("Use the \"greet [id]\" to start a conversation with an NPC. Use \"scan npcs\" to show which NPCs are on this planet.");
=======
    public void travelToPlanet(Player characterToTravel, UUID planetId) {
        Planet nextPlanet = this._planets.get(planetId), curPlanet = this._planets.get(characterToTravel.getPlanetId());
        if (this._movementCalculator.isReachable(curPlanet.getXCoor(), curPlanet.getYCoor(), nextPlanet.getXCoor(), nextPlanet.getYCoor(), characterToTravel.getFuel())) {
            this._dashboard.print("Now traveling to " + this._planets.get(planetId).getName());
            characterToTravel.setCurrentPlanet(planetId);

            this._dashboard.print("Refilled fuel tank!");
            this._player.setFuel(this._player.getMaxFuel());
            int travelTime = 10;
            incrementTime(this._movementCalculator.calculateDistance(curPlanet.getXCoor(), curPlanet.getYCoor(), nextPlanet.getXCoor(), nextPlanet.getYCoor()) / travelTime);
            this.startConversation();
>>>>>>> Papers
        } else {
            this._dashboard.print("Sorry, you're unable to reach the planet you were trying to travel to, try moving to a closer planet and try again.");
        }
    }

    /**
     * A method used for processing the "warp" command during runtime.
     * Looks very much like the travelToPlanet method. However this uses the Warp fuel as a limiting factor.
     * As Warp fuel is different from regular fuel it also uses different movement calculations.
     * There is a possibility of not traveling, if you do not have enough warp fuel.
     * @param characterToTravel which character that should be moved
     * @param nextPositionUuid which planet or moon that is the intended target.
     */
    public void processWarp(Player characterToTravel, UUID nextPositionUuid) {
        int[] currentPosition = getPositionCoordinates(this._player.getPlanetId());
        int[] nextPosition = getPositionCoordinates(nextPositionUuid);
        NPCHolder nextNpcHolder = getNPCHolderFromUuid(nextPositionUuid);
        
        if(this._movementCalculator.isWarpReachable(currentPosition[0], currentPosition[1], nextPosition[0], nextPosition[1], characterToTravel.getWarpfuel())) {
            this._dashboard.print("Now warping to " + nextNpcHolder.getName());
            characterToTravel.setCurrentPlanet(nextPositionUuid);
            characterToTravel.setWarpfuel(characterToTravel.getWarpfuel() - this._movementCalculator.calculateWarpFuelUsage(currentPosition[0], currentPosition[1], nextPosition[0], nextPosition[1]));
            
            this._dashboard.print("Use the \"greet [id]\" to start a conversation with an NPC. Use \"scan npcs\" to show which NPCs are on this planet.");
        } else {
            this._dashboard.print("Sorry, you're unable to reach the planet you were trying to warp to, try moving to a closer planet and try again.");
        }
    }
    
    /**
     * A method used for processing the "greet" command during runtime.
     * 
     * @param secondWord 
     */
    public void processGreet(String secondWord) {
        if(secondWord == null) {
            this._dashboard.print("Use the greet command by writting \"greet [id]\". Write \"scan npcs\" to show possible NPCs and their ids.");
            return;
        }
        
        int secondWordNumber = -1;
        try {
            secondWordNumber = Integer.parseInt(secondWord);
        } catch(NumberFormatException e) {
            
        }
        
        NPCHolder npcHolder = getNPCHolderFromUuid(this._player.getPlanetId());
        
        if(secondWordNumber != -1) {
            for(UUID npcUuid : npcHolder.getNpcIds()) {
                if(secondWordNumber == this._npcs.get(npcUuid).getReferenceNumber()) {
                    this.startConversation(this._npcs.get(npcUuid).getId());
                    return;
                }
            }
        } else {
            this._dashboard.print("NPCid was not recognized, please only use the id numbers to refer to NPCs. Write \"scan npcs\" to show possible NPCs and their ids.");
        }
    }
    
    /**
     * A method for starting a conversation with the NPC on the planet, that the player is currently at
     */
    public void startConversation(UUID npcId) {
        //IF the NPC has a nextConversationId (if it is not null) use that!
        // Starting conversation!
        //UUID npcId = this._planets.get(this._player.getPlanetId()).getNpcIds()[0];
        NPC npc = this._npcs.get(npcId);
        if(npc.hasNextConversationId()) {
            npc.setConversationId(npc.getNextConversationId());
            npc.setNextConversationId(-1);
        }
        this._currentConversation = new Conversation(npc.getConversationId());
        this._currentConversation.setNpcId(npcId);
        this._currentConversation.createWholeConversation(this._fileHandler.getText(this._currentConversation.getConversationId()));
        this._dashboard.print("A connection with " + npc.getName() + " has been established...");
        this._dashboard.print(npc.getName() + " looks like " + npc.getDescription());
        this._dashboard.print(npc.getName() + ": " + this._currentConversation.getQText());
        this._dashboard.print("You can answer using the \"say\" command: " + this._currentConversation.getPossibleAnswers());
    }

    /**
     * Takes in what the user has answered using the say command, and figures out whether it is recognized It also calls for the execution of the execution line
     *
     * @param answer is the second word that the user typed in along with say
     */
    public void processAnswer(String answer) {
        if (answer == null) {
            this._dashboard.print("You have to say something!");
            return;
        }

        if (this._currentConversation == null) {
            this._dashboard.print("Sorry, you can't use say when you have no ongoing conversation!");
            return;
        }
<<<<<<< HEAD
        
        NPCHolder npcHolder = getNPCHolderFromUuid(this._player.getPlanetId());
        
        //UUID npcId = this._planets.get(this._player.getPlanetId()).getNpcId();
        if(!npcHolder.hasNpcId(this._currentConversation.getNpcId())) {
=======

        UUID npcId = this._planets.get(this._player.getPlanetId()).getNpcId();
        if (npcId != this._currentConversation.getNpcId()) {
>>>>>>> Papers
            this._dashboard.print("Sorry, you're no longer at the same position as the NPC and can therefore not talk with him!");
            this._currentConversation = null;
            return;
        }

        this._currentConversation.processAnswer(answer.toLowerCase());
<<<<<<< HEAD
        if(this._currentConversation.hasCurrentAnswer()) {
            this._dashboard.print(this._npcs.get(this._currentConversation.getNpcId()).getName() + ": " + this._currentConversation.getReactText());
            if(!this.processExecution(this._currentConversation.getExecutionLine(), this._currentConversation.getNpcId())) {
                if(this._currentConversation.getNextLineNumber() == -1) {
=======
        if (this._currentConversation.hasCurrentAnswer()) {
            this._dashboard.print(this._currentConversation.getReactText());
            if (!this.processExecution(this._currentConversation.getExecutionLine(), npcId)) {
                if (this._currentConversation.getNextLineNumber() == -1) {
>>>>>>> Papers
                    this._currentConversation = null;
                    this._dashboard.print("Conversation has been terminated");
                    return;
                }
                this._currentConversation.setNextQuestion(this._currentConversation.getNextLineNumber());
            }
            this._dashboard.print(this._npcs.get(this._currentConversation.getNpcId()).getName() + ": " + this._currentConversation.getQText());
            this._dashboard.print("You can answer: " + this._currentConversation.getPossibleAnswers());
        } else {
            this._dashboard.print(this._npcs.get(this._currentConversation.getNpcId()).getName() + ": Sorry, I don't know how to respond to that answer.");
            this._dashboard.print("The only answers I seek: " + this._currentConversation.getPossibleAnswers());
        }
    }

    /**
     * Figures out what should happen according to the parameter executionLine, and calls the relevant methods
     *
     * @param executionLine which commands that are to be executed
     * @param npcId which npc that the conversation is with
     * @return whether or not the conversation's question was changed during the execution commands
     */
    public boolean processExecution(String executionLine, UUID npcId) {
        boolean changedQuestion = false;
        String[] allExecutions;
        allExecutions = executionLine.split(",");
        for (String eachExecution : allExecutions) {
            String[] executionSplit = eachExecution.split(":");
            if (executionSplit[0].equals("deliverPackage")) {
                this.deliverPackage(npcId);
            } else if (executionSplit[0].equals("pickupPackage")) {
                //Where should the conversation go if you do not have space?
                if (!this.pickupPackage(npcId)) {
                    //You were unable to pick up all the items the NPC has, so what should happen now? Terminate conversation? Head to another question?
                    //"checkPickup" will only check for one item, should this too?
                }
            } else if (executionSplit[0].equals("nextConvoId")) {
                try {
                    int convoId = Integer.parseInt(executionSplit[1]);
                    this._npcs.get(npcId).setNextConversationId(convoId);
                } catch (NumberFormatException e) {

                }
            } else if (executionSplit[0].equals("checkPackage")) {
                this.checkPackage(npcId, executionSplit[1]);
                changedQuestion = true;
            } else if (executionSplit[0].equals("checkPickup")) {
                this.checkPickup(npcId, executionSplit[1]);
                changedQuestion = true;
            } else if (executionSplit[0].equals("removeReputation")) {
                try {
                    int reputationAmount = Integer.parseInt(executionSplit[1]);
                    this._player.setReputation((this._player.getReputation() - reputationAmount));
                } catch (NumberFormatException e) {

                }
            }
        }

        return changedQuestion;
    }
<<<<<<< HEAD
    
    /**
     * A method used for execution commands from the Conversation.
     * The method delivers a package according to the package id an NPC has.
     * This method does not include if the player has the package or not,
     * and should therefore only be executed after the "checkPackage".
     * @param npcId the npc which has to receive the package
     */
=======

>>>>>>> Papers
    public void deliverPackage(UUID npcId) {
        Items item = this._items.get(this._npcs.get(npcId).getPackageId());
        this._player.setReputation(this._player.getReputation() + item.getReputationWorth());
        this._player.removeItem(item.getId(), item.getWeight());
        if (this.time <= this.time + 20) {
            this._player.setReputation(this._player.getReputation() + 5);
        }
        if (!item.getPapers()) {
            this._player.setReputation(this._player.getReputation() - 15);
        }
    }
<<<<<<< HEAD
    
    /**
     * This method is executed from the execution commands from Conversation.
     * This method picks up every package the NPC has, 
     * if you don't have space for the package you're trying to pick up, 
     * it will print that it failed.
     * @param npcId the npc that the user picks up packages from
     * @return whether you succeeded or not to pick up all the packages
     */
=======

>>>>>>> Papers
    public boolean pickupPackage(UUID npcId) {
        for (UUID itemUuid : this._npcs.get(npcId).getInventoryUuids()) {
            if (this._player.addItem(itemUuid, this._items.get(itemUuid).getWeight())) {
                this._dashboard.print("You picked up " + this._items.get(itemUuid).getDescription());
                this._npcs.get(npcId).removeItem(itemUuid, this._items.get(itemUuid).getWeight());
                this._items.get(itemUuid).setDeliveryTime(20);
            } else {
                this._dashboard.print("You were unable to pick up " + this._items.get(itemUuid).getDescription() + ", since you don't have space in your inventory!");
                return false;
            }
        }
        return true;
    }
<<<<<<< HEAD
    
    /**
     * A method used to execute executionlines from Conversation.
     * It will set two different next questions, 
     * according to whether the player has a package UUID that is equal to what the NPC wants delivered.
     * @param npcId the npc that the player wants to deliver to
     * @param executionSplit used to get the two different question numbers that you have to proceed to
     */
=======

>>>>>>> Papers
    public void checkPackage(UUID npcId, String executionSplit) {
        String[] whichQuestion = executionSplit.split("|");
        int[] questionNumbers = new int[2];
        try {
            //Note, the split command somehow splits "1|2" into three array indexes: "1", "|" and "2"
            questionNumbers[0] = Integer.parseInt(whichQuestion[0]);
            questionNumbers[1] = Integer.parseInt(whichQuestion[2]);
        } catch (NumberFormatException e) {
            System.out.println("Runtime error?");
        }

        for (UUID itemUuid : this._player.getInventoryUuids()) {
            if (this._npcs.get(npcId).getPackageId() == itemUuid) {
                this._currentConversation.setNextQuestion(questionNumbers[0]);
                return;
            }
        }

        //System.out.println("Setting question to second option! which is: " + questionNumbers[1]);
        this._currentConversation.setNextQuestion(questionNumbers[1]);
    }
<<<<<<< HEAD
    
    /**
     * This method is used to execute executionlines from Conversation.
     * This sets two different question numbers according to whether the npc has any packages to pickup or not.
     * @param npcId the npc to check whether it has items to pickup or not
     * @param executionSplit used to extract which question to head to next
     */
=======

>>>>>>> Papers
    public void checkPickup(UUID npcId, String executionSplit) {
        String[] whichQuestion = executionSplit.split("|");
        int[] questionNumbers = new int[2];
        try {
            questionNumbers[0] = Integer.parseInt(whichQuestion[0]);
            questionNumbers[1] = Integer.parseInt(whichQuestion[2]);
        } catch (NumberFormatException e) {

        }
<<<<<<< HEAD
        
        if(this._npcs.get(npcId).getInventoryUuids().length > 0) { //The NPC can have 0 items
=======

        if (this._npcs.get(npcId).getInventoryUuids().length > 0) { //What? Can the NPC have no items? Then why check for it?
>>>>>>> Papers
            Items curItem = this._items.get(this._npcs.get(npcId).getInventoryUuids()[0]);
            if (this._player.hasInventorySpaceFor(curItem.getWeight())) {
                this._currentConversation.setNextQuestion(questionNumbers[0]);
                return;
            }
        }

        this._currentConversation.setNextQuestion(questionNumbers[1]);
    }

    /**
     * Changes a planet reference number to the planet's UUID. Can catch an exception
     *
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
            return null;
        }
<<<<<<< HEAD
        
        if(planetNumber == 0) {
            UUID curUuid = this._player.getPlanetId();
            Planet curPlanet = this._planets.get(curUuid);
            if(curPlanet.hasMoon()) {
                return curPlanet.getMoonUuid();
            } else {
                this._dashboard.print("Sorry, there is no moon to travel to at this planet!");
                return null;
            }
        }
        
        for(Planet planet : this._planets.values()) {
            if(planetNumber == planet.getReferenceNum()) {
=======

        for (Planet planet : this._planets.values()) {
            if (planetNumber == planet.getReferenceNum()) {
>>>>>>> Papers
                return planet.getId();
            }
        }

        //Print the valid planet names!
        this.printAllPlanets();

        return null;
    }

    /**
     * Changes a item reference number to the item's UUID. Can catch an exception
     *
     * @param secondWord the second word that the user typed in
     * @return the UUID of the corresponding item
     */
    public UUID getItemIdFromReferenceNumber(String secondWord) {
        int itemNumber = -1;
        try {
            itemNumber = Integer.parseInt(secondWord);
        } catch (Exception e) {
            this._dashboard.print("Invalid item id, \"" + secondWord + "\" was not recognized, use \"print inventory\" to show your items and their ids!");
            //this._dashboard.print(e.toString());
            return null;
        }

        for (Items item : this._items.values()) {
            if (itemNumber == item.getReferenceNumber()) {
                return item.getId();
            }
        }

        //Print the valid item names!
        //this.printInventory();
        return null;
    }

    /**
     * Drops an item according to it's id, if the item id is not recognized, it will print so
     *
     * @param itemName the second word that the user typed in
     */
<<<<<<< HEAD
    public void dropItem(String itemReferenceNumber) {
        UUID itemUuid = this.getItemIdFromReferenceNumber(itemReferenceNumber);
        
        for(UUID itemId : this._player.getInventoryUuids()) {
            if(itemId == itemUuid) {
=======
    public void dropItem(String itemName) {
        UUID itemUuid = this.getItemIdFromReferenceNumber(itemName);

        for (UUID itemId : this._player.getInventoryUuids()) {
            if (itemId == itemUuid) {
>>>>>>> Papers
                this._player.removeItem(itemId, this._items.get(itemId).getWeight());
                this._player.setReputation(this._player.getReputation()-this._items.get(itemId).getReputationWorth());
                return;
            }
        }
        this._dashboard.print("Sorry, you do not hold such item id, please use \"print inventory\" to show your items and their ids.");
    }

    /**
     * Creates the planets!
     *
     * @return what UUID the player should be starting on
     */
    public UUID createPlanets() {
<<<<<<< HEAD
        /*
        UUID returnUuid = null;
        //Creating the items list
        int i = 0;
        while(true) {
            if(!this._fileHandler.doesFileExist("data/planets/" + i + ".json")) {
                break;
            }
            Planet newPlanet = this._fileHandler.getJSON("data/planets/" + i + ".json", Planet.class);
            this._planets.put(newPlanet.getId(), newPlanet);
            i++;
            
            if(newPlanet.getPid() == 0) {
                returnUuid = newPlanet.getId();
            }
        }
        
        return returnUuid;
        */
        
        
        Planet newPlanet = new Planet("hej", "wow!", 1, 1, 0);
        this._planets.put(newPlanet.getId(), newPlanet);
        
        newPlanet = new Planet("Starter!", "starterdesc!", 20, 20, 1);
        this._planets.put(newPlanet.getId(), newPlanet);
        
        createMoons();
        
        return newPlanet.getId();
        
    }
    
    /**
     * This creates the moons from JSON files, and places them according to their PID.
     * This method assumes that every Moon has a PID that will match a planet's PID.
     */
    public void createMoons() {
        /*
        int i = 0;
        while(true) {
            if(!this._fileHandler.doesFileExist("data/moons/" + i + ".json")) {
                break;
            }
            Moon newMoon = this._fileHandler.getJSON("data/moons/" + i + ".json", Moon.class);
            this._moons.put(newMoon.getId(), newMoon);
            i++;
        }
        */
        
        Moon newMoon = new Moon("navn","hej!", 0);
        this._moons.put(newMoon.getId(), newMoon);
        
        newMoon = new Moon("navn","hej2!", 1);
        this._moons.put(newMoon.getId(), newMoon);
        
        HashMap<Integer, Planet> planetPids = new HashMap<>();
        for(Planet planet : this._planets.values()) {
            planetPids.put(planet.getPid(), planet);
        }
        
        for(Moon moon : this._moons.values()) {
            if(planetPids.containsKey(moon.getPid())) {
                planetPids.get(moon.getPid()).setMoonUuid(moon.getId());
                moon.setParentPlanetUuid(planetPids.get(moon.getPid()).getId());
            }
        }
=======
        UUID curUUID = UUID.randomUUID();
        this._planets.put(curUUID, new Planet("hej", "wow!", 1, 1, new Moon("wow1 moon!"), curUUID));

        UUID starterUUID = UUID.randomUUID();
        this._planets.put(starterUUID, new Planet("Starter!", "starterdesc!", 20, 20, new Moon("wowmoon2!"), starterUUID));
        return starterUUID;
>>>>>>> Papers
    }

    /**
     * Creates the NPCs
     */
    public void createNpcs() {
<<<<<<< HEAD
        /*
        int i = 0;
        while(true) {
            if(!this._fileHandler.doesFileExist("data/npcs/" + i + ".json")) {
                break;
            }
            NPC newNpc = this._fileHandler.getJSON("data/npcs/" + i + ".json", NPC.class);
            this._npcs.put(newNpc.getId(), newNpc);
            i++;
        }
        */
        
        //A method for creating NPCs
        
        NPC newNpc = new NPC("Planet1NPC", "He be wow!", -1, 0, 1, 0);
        this._npcs.put(newNpc.getId(), newNpc);
        this._civilians.put(newNpc.getId(), newNpc);
        
        newNpc = new NPC("Planet2NPC", "He be not wow!!", 1, 1, 1, 0);
        this._npcs.put(newNpc.getId(), newNpc);
        this._civilians.put(newNpc.getId(), newNpc);
        
        newNpc = new NPC("Planet2NPC2", "He be not wow!!", 1, 1, 1, 0);
        this._npcs.put(newNpc.getId(), newNpc);
        this._civilians.put(newNpc.getId(), newNpc);
        
        ArrayList<NPCHolder> npcHolders = new ArrayList<>();
        for(Planet planet : this._planets.values()) {
            npcHolders.add(planet);
        }
        placeNpcs(this._civilians.values(), npcHolders);
        
        createRebels();
        
    }
    
    /**
     * A method used to create the rebels from JSON files.
     * The method calls the method placeNPCs with the list of rebels and moons.
     */
    private void createRebels() {
        /*
        int i = 0;
        while(true) {
            if(!this._fileHandler.doesFileExist("data/npcs/" + i + ".json")) {
                break;
            }
            NPC newNpc = this._fileHandler.getJSON("data/npcs/" + i + ".json", NPC.class);
            this._npcs.put(newNpc.getId(), newNpc);
            i++;
        }
        */
        
        //A method for creating NPCs
        
        NPC newNpc = new NPC("Rebel1", "He be wow!", -1, 0, 1, 0);
        this._npcs.put(newNpc.getId(), newNpc);
        this._rebels.put(newNpc.getId(), newNpc);
        
        newNpc = new NPC("Rebel2", "He be not wow!!", -1, 1, 1, 0);
        this._npcs.put(newNpc.getId(), newNpc);
        this._rebels.put(newNpc.getId(), newNpc);
        
        newNpc = new NPC("Rebel3", "He be not wow!!", -1, 1, 1, 10);
        this._npcs.put(newNpc.getId(), newNpc);
        this._rebels.put(newNpc.getId(), newNpc);
        
        ArrayList<NPCHolder> npcHolders = new ArrayList<>();
        for(Moon moon : this._moons.values()) {
            npcHolders.add(moon);
        }
        placeNpcs(this._rebels.values(), npcHolders);
    }
    
    /**
     * A method for placing NPCs according to the two parameters.
     * The method goes through 3 steps when placing NPCs:
     * 1. it tries to match NPCs with PIDs and planets/moons with PIDs. Which can mean several NPCs at the same planet.
     * 2. it then adds NPCs (who has no PID) to planets/moons without NPCs.
     * 3. it then adds NPCs (who has no PID) to random planets/moons.
     * These steps makes sure, that if there is NPCs without PIDs, these NPCs will be placed on empty planets/moons,
     * and when there is no more empty planets/moons, NPCs without PIDs will be placed "randomly".
     * @param npcList the list of NPCs to place (this "list" comes from the .values() from a HashMap)
     * @param holdersList of the type NPCHolder, which is the superclass for Planets and Moons. NPCHolder holds the information and behaviour that handles NPCs at planets/moons.
     */
    public void placeNpcs(Collection<NPC> npcList, ArrayList<NPCHolder> holdersList) {
        //An array list that holds the planets/moons without an NPC.
        //By the start all planets/moons are a part of this list.
        //The planets/moons are removed from this list when they get an NPC.
        ArrayList<NPCHolder> hasNoNpc = new ArrayList<>();
        //A HashMap, which makes it easy for this method to fecth the right planet/moon according to their PID.
        //A PID is unique for each planet/moon.
        HashMap<Integer, NPCHolder> planetPids = new HashMap<>();
        //A list used for step 2 and 3 (see the method Javadoc). 
        //It holds all of the NPCs without a PID.
        ArrayList<NPC> hasNoPid = new ArrayList<>();
        
        //Place all of the planets/moons in the lists.
        for(NPCHolder npcHolder : holdersList) {
            hasNoNpc.add(npcHolder);
            planetPids.put(npcHolder.getPid(), npcHolder);
        }
        
        //Goes through the whole list of NPCs that has to be placed, and places them if they have an PID.
        //If they don't have a PID, the NPC will be added to the list "hasNoPid".
        for(NPC npc : npcList) {
            if(npc.getPid() == -1) {
                hasNoPid.add(npc);
            } else {
                planetPids.get(npc.getPid()).addNpcId(npc.getId());
                npc.setPlanetId(planetPids.get(npc.getPid()).getId());
                hasNoNpc.remove(planetPids.get(npc.getPid()));
            }
        }
        
        //Goes through the NPC list that has no PID and places them on empty planets/moons,
        // stops when there are not empty planets/moons left.
        int i = 0;
        for(NPC npc : hasNoPid) {
            //If the planet/moon list that has no NPC is empty, break this loop
            if(hasNoNpc.isEmpty()) { 
                break;
            }
            
            if(i > hasNoNpc.size()) {
                break;
            }
            
            hasNoNpc.get(i).addNpcId(npc.getId());
            npc.setPlanetId(hasNoNpc.get(i).getId());
            hasNoNpc.remove(i);
            i++;
        }
        
        //The NPCHolder list is made into an array, as it is easier to acces random entries in that compared to a list.
        NPCHolder[] planets = new NPCHolder[holdersList.size()];
        holdersList.toArray(planets);
        for(NPC npc : hasNoPid) {
            //If the NPC already has a planet, skip placing them.
            if(npc.getPlanetId() != null) { 
                continue;
            }
            
            //Random which planet/moon that should get the next NPC
            i = (int)(Math.random()*holdersList.size());
            
            planets[i].addNpcId(npc.getId());
            npc.setPlanetId(planets[i].getId());
        }
    }
    
    /**
     * This method is used for creating items from JSON files, determining receivers and placing the items.
     * This method follows the algorithm (it is simplified here):
     * 1. Creating the items from JSON files, and filling necessary lists
     * 2. Making sure that there is as many items used run time as there is NPCs (and more filling of lists)
     * 3a. Finding and adding receivers according to the RIDs of both NPCs and items.
     * 3b. Finding and adding receivers that have no RIDs
     * 4a. Finding and placing items at the right NPCs based on PIDs
     * 4b. Finding and placing items without PIDs
     */
    public void createItems() {
        //There is more JSON files with items, than there actually has to be used in game.
        //This list holds all the items currently in use
        ArrayList<Items> itemsUsed = new ArrayList<>();
        
        //Contains the items that has no delivery and pickup place.
        //All items starts out in these lists, and slow gets removed during this method.
        ArrayList<Items> itemsHaveNoDelivery = new ArrayList<>();
        ArrayList<Items> itemsHaveNoPickup = new ArrayList<>();
        
        //Contains the NPCS that has no package it needs delivered and item the player can pickup.
        //All items starts out in these lists, and slow gets removed during this method.
        ArrayList<NPC> npcsHaveNoDelivery = new ArrayList<>();
        ArrayList<NPC> npcsHaveNoPickup = new ArrayList<>();
        
        //Holds all of the items and npcs that has a PID or RID
        HashMap<Integer, Items> itemsWithRid = new HashMap<>(); //Could just a be a list, as the key is never used
        HashMap<Integer, Items> itemsWithPid = new HashMap<>(); //Could just a be a list, as the key is never used
        HashMap<Integer, NPC> npcsWithRid = new HashMap<>();
        HashMap<Integer, NPC> npcsWithPid = new HashMap<>();
        
        //Filling the lists with NPCs
        for(NPC npc : this._npcs.values()) {
            npcsHaveNoDelivery.add(npc);
            npcsHaveNoPickup.add(npc);
            if(npc.getRid() != -1) {
                npcsWithRid.put(npc.getRid(), npc);
            } else if(npc.getPid() != -1) {
                npcsWithPid.put(npc.getPid(), npc);
            }
        }
        
        //1. Creating the items from JSON
        int i = 0;
        while(true) {
            if(!this._fileHandler.doesFileExist("data/items/" + i + ".json")) {
                break;
            }
            Items newItem = this._fileHandler.getJSON("data/items/" + i + ".json", Items.class);
            this._items.put(newItem.getId(), newItem);
            i++;
            if(npcsWithPid.containsKey(newItem.getPid()) || npcsWithRid.containsKey(newItem.getRid())) {
                itemsUsed.add(newItem);
=======
        ArrayList<Planet> hasNoNpc = new ArrayList<>();
        ArrayList<NPC> hasNoPlanet = new ArrayList<>();
        int index;
        for (Planet planet : this._planets.values()) {
            hasNoNpc.add(planet);
        }

        //A method for creating NPCs
        UUID curId = UUID.randomUUID();
        this._npcs.put(curId, new NPC("Planet1NPC", "He be wow!", 0, 1, curId));
        if (hasNoNpc.size() > 0) {
            index = (int) Math.random() * hasNoNpc.size();
            hasNoNpc.get(index).setNpcId(curId);
            this._npcs.get(curId).setPlanetId(hasNoNpc.get(index).getId());
            hasNoNpc.remove(index);
        } else {
            hasNoPlanet.add(this._npcs.get(curId));
        }

        curId = UUID.randomUUID();
        this._npcs.put(curId, new NPC("Planet2NPC", "He be not wow!!", 1, 1, curId));
        if (hasNoNpc.size() > 0) {
            index = (int) Math.random() * hasNoNpc.size();
            hasNoNpc.get(index).setNpcId(curId);
            this._npcs.get(curId).setPlanetId(hasNoNpc.get(index).getId());
            hasNoNpc.remove(index);
        } else {
            hasNoPlanet.add(this._npcs.get(curId));
        }

        //What about fucking moons?
        //Change moons to be of the type planet? Then have a boolean called "isMoon"?
    }

    public void createItems() {
        HashMap<Integer, Items> hasNoNpc = new HashMap<>();
        Items newItem = new Items(2, "wow item", 0);
        this._items.put(newItem.getId(), newItem);
        hasNoNpc.put(newItem.getRID(), newItem);

        newItem = new Items(3, "wow2 item", 1);
        this._items.put(newItem.getId(), newItem);
        hasNoNpc.put(newItem.getRID(), newItem);

        for (Items item : this._items.values()) {
            if (item.getRID() == -1) {
                item.setPapersFalse();
            } else {
                item.setPapersTrue();
>>>>>>> Papers
            }

        }
<<<<<<< HEAD
        
        //2. Fill up itemsUsed, so that it has as many items as there are NPCs
        ArrayList<Items> allItems = new ArrayList<>(this._items.values());
        //As long as the size of items used is smaller than the list of NPCs
        while(itemsUsed.size() < this._npcs.size()) {
            while(true) {
                int randomIndex = (int)(Math.random()*this._items.size());
                
                //If the random picked item is already stated as being used,
                // it will skip the rest of the while(true) and generate a new random index.
                if(itemsUsed.contains(allItems.get(randomIndex))) {
                    continue;
                }
                
                itemsUsed.add(allItems.get(randomIndex));
=======
    }

    // rid på -1 sæt til false. Ellers sæt math.random
    ArrayList<NPC> hasNoPackageDelivery = new ArrayList<>();
    ArrayList<Items> hasNpcDelivery = new ArrayList<>();

    for (NPC npc

    : this._npcs.values () 
        ) {
            if (npc.getRid() == -1) {
            hasNoPackageDelivery.add(npc);
            continue;
        }
        if (hasNoNpc.containsKey(npc.getRid())) {
            npc.setPackageId(hasNoNpc.get(npc.getRid()).getId());
            hasNoNpc.get(npc.getRid()).setNpcId(npc.getId());
            hasNpcDelivery.add(hasNoNpc.get(npc.getRid()));
            hasNoNpc.remove(npc.getRid());
        }
    }

    for (NPC npc : hasNoPackageDelivery

    
        ) {
            if (hasNoNpc.size() > 0) {
            int itemRid = 0;
            for (Items item : hasNoNpc.values()) {
                npc.setReceiverRid(item.getRID());
                hasNpcDelivery.add(hasNoNpc.get(npc.getRid()));
                itemRid = item.getRID();
>>>>>>> Papers
                break;
            }
            hasNoNpc.remove(itemRid);
        }
<<<<<<< HEAD
        
        //START: Filling the lists and hashmaps for items
        for(Items item : itemsUsed) {
            itemsHaveNoDelivery.add(item);
            itemsHaveNoPickup.add(item);
            if(item.getRid() != -1) {
                itemsWithRid.put(item.getRid(), item);
            } else if(item.getPid() != -1) {
                itemsWithPid.put(item.getPid(), item);
            }
        }
        //END: Filling the lists and hashmaps for items
        
        //START: 2a. Adding receivers to both items and npcs
        for(Items item : itemsWithRid.values()) {
            //Uses the NPC HashMaps
            if(npcsWithRid.containsKey(item.getRid())) {
                NPC npc = npcsWithRid.get(item.getRid());
                npc.setPackageId(item.getId());
                item.setNpcId(npc.getId());
                
                itemsHaveNoDelivery.remove(item);
                npcsHaveNoDelivery.remove(npc);
            }
        }
        
        //2b. Adding receivers for items and NPCs without an RID
        for(Items item : itemsHaveNoDelivery) {
            if(npcsHaveNoDelivery.size() > 0) {
                int randomNpcIndex = (int)(Math.random()*npcsHaveNoDelivery.size());
                NPC npc = npcsHaveNoDelivery.get(randomNpcIndex);
                item.setNpcId(npc.getId());
                npc.setPackageId(item.getId());
                
                npcsHaveNoDelivery.remove(npc);
            } else {
                break;
            }
        }
        //END: Adding receivers to both items and npcs
        
        //START: 3a. Adding where the items are going to be picked up
        for(Items item : itemsWithPid.values()) {
            if(npcsWithPid.containsKey(item.getPid())) {
                NPC npc = npcsWithPid.get(item.getPid());
                npc.addItem(item.getId(), item.getWeight());
                
                itemsHaveNoPickup.remove(item);
                npcsHaveNoPickup.remove(npc);
            }
        }
        
        //3b. Adding where items without and PID are going to be picked up
        for(Items item : itemsHaveNoPickup) {
            if(npcsHaveNoPickup.size() > 0) {
                while(true) {
                    int randomNpcIndex = (int)(Math.random()*npcsHaveNoPickup.size());
                    NPC npc = npcsHaveNoPickup.get(randomNpcIndex);
                    
                    //Avoid placing the item at the NPC that is set as the receiver
                    if(npc.getPackageId() == item.getId()) {
                        continue;
                    }
                    
                    npc.addItem(item.getId(), item.getWeight());
                    npcsHaveNoPickup.remove(npc);
                    break;
                }
            } else {
                break;
            }
        }
        //END: Adding where the items are going to be picked up
    }
    
    /**
     * Gets a NPCHolder object from an UUID, this can either be a planet or moon UUID.
     * @param positionUuid a planet or moon UUID
     * @return the NPCHolder object
     */
    public NPCHolder getNPCHolderFromUuid(UUID positionUuid) {
        if(this._planets.containsKey(positionUuid)) {
            return this._planets.get(positionUuid);
        } else {
            return this._moons.get(positionUuid);
        }
    }
    
    /**
     * Gets the coordinates of a position based on a UUID
     * @param positionUuid a planet or moons UUID
     * @return an integer array of the size 2, with the x on index 0 and y on index 1
     */
    public int[] getPositionCoordinates(UUID positionUuid) {
        Planet planet;
        if(this._planets.containsKey(positionUuid)) {
            planet = this._planets.get(positionUuid);
        } else {
            planet = this._planets.get(this._moons.get(positionUuid).getParentPlanetUuid());
        }
        int[] returnArray = new int[2];
        returnArray[0] = planet.getXCoor();
        returnArray[1] = planet.getYCoor();
        return returnArray;
            
    }
    
    /**
     * This method prepares and calls the method that does the actual calculation for whether the NPC should move or not.
     */
    public void tryNpcMovement() {
        ArrayList<NPCHolder> npcHolders = new ArrayList<>();
        for(Moon moon : this._moons.values()) {
            npcHolders.add(moon);
        }
        tryNpcMovementCalculations(this._rebels.values(), npcHolders);
        
        npcHolders = new ArrayList<>();
        for(Planet planet : this._planets.values()) {
            npcHolders.add(planet);
        }
        tryNpcMovementCalculations(this._civilians.values(), npcHolders);
    }
    
    /**
     * A method that goes through all of the NPCs passed in the parameter
     * and places them at the planets/moons passed in the parameter.
     * @param npcList The NPCs that has to be placed
     * @param holdersList The places the NPCs can be placed
     */
    public void tryNpcMovementCalculations(Collection<NPC> npcList, ArrayList<NPCHolder> holdersList) {
        for(NPC npc : npcList) {
            NPCHolder[] npcHolders = new NPCHolder[holdersList.size()];
            holdersList.toArray(npcHolders);
            
            if(npc.getChanceToMove() > 0) {
                int randomNumber = (int)(Math.random()*10);
                if(npc.getChanceToMove() > randomNumber) {
                    int randomPlanet = (int)(Math.random()*npcHolders.length);
                    
                    //Make sure that the random generated new position, 
                    // is not already the NPC's position.
                    while(npcHolders[randomPlanet].getId() == npc.getPlanetId()) {
                        randomPlanet = (int)(Math.random()*npcHolders.length);
                    }
                    
                    //Move the NPC
                    getNPCHolderFromUuid(npc.getPlanetId()).removeNpcId(npc.getId());
                    npc.setPlanetId(npcHolders[randomPlanet].getId());
                    npcHolders[randomPlanet].addNpcId(npc.getId());
                }
            }
        }
    }
    
    /**
     * Prints both the highscore fetched from the highscore file and the current player's highscore
     */
    public void printHighScore() {
        HighScore currentHighScore = this._fileHandler.getJSON("highscore.json", HighScore.class);
        HighScore playerScore = new HighScore(this._player.getReputation(), 2, "matias");
        this._dashboard.print(currentHighScore.toString());
        this._dashboard.print(playerScore.toString());
    }

    /**
     * Checks the current player's highscore, and if that highscore is better than the one fetched from the JSON file,
     * save the current player's highscore as the highest.
     */
    public void saveHighScore() {
        //Creates a new highsore object based on the current player's stats
        HighScore playerScore = new HighScore(this._player.getReputation(), 2, "matias");  // tid : 2 og name :  matias er blot place holders. 
        
        //Read the highscore JSON file, if it exists!
        if (!this._fileHandler.doesFileExist("highscore.json")) {
            this._fileHandler.writeToFile("highscore.json", playerScore.toJsonString());
        } else {
            HighScore currentHighScore = this._fileHandler.getJSON("highscore.json", HighScore.class);
            if (playerScore.getRep() == currentHighScore.getRep()) {
                if (playerScore.getTime() > currentHighScore.getTime()) {
                    //Save the highscore!
                    this._fileHandler.writeToFile("highscore.json", playerScore.toJsonString());
                } else if (playerScore.getTime() < currentHighScore.getTime()) {
                    this._dashboard.print("Sorry, the current highscore managed to get the same score, with a better time");
                    this._dashboard.print("Your score was: " + playerScore.getRep());
                } else if (playerScore.getTime() == playerScore.getTime()) { //Trolling, should possibly be removed.
                    this._dashboard.print("You managed to get exactly the same score and time, as the previous highscore player");
                    this._dashboard.print("As programmers we didnt think this was possible, therefor we have no other option, to declare Matias Marek as the ruler and all time HighScore champion");

                }

            } else if (playerScore.getRep() > currentHighScore.getRep()) {
                //Save high score!
                this._fileHandler.writeToFile("highscore.json", playerScore.toJsonString());
            } else {
                this._dashboard.print("Sorry, you didn't beat the highscore! Cunt");
                this._dashboard.print("Your score was: " + playerScore.getRep());
            }

        }

=======
    }

    ArrayList<NPC> allNpcs = new ArrayList<>();
    for (NPC npc

    : this._npcs.values () 
        ) {
            allNpcs.add(npc);
    }

    int index = 0;
    for (Items item : hasNpcDelivery

    
        ) {
            if (index >= allNpcs.size()) {
            index = 0;
        }

        if (item.getRID() == allNpcs.get(index).getRid()) {
            index++;
            if (index >= allNpcs.size()) {
                index = 0;
            }
        }

        allNpcs.get(index).addItem(item.getId(), item.getWeight());
        index++;
    }
}

/**
 * Methods for chancing the time ingame
 */
public void incrementTime(int i) { // This method + to the time
        time = +i;
    }

    public void decrementTime(int i) { // This method  -  to the time
        time = -i;

    }

    public int checkTimers() { // This method checks the times and returns it        
        return time;
>>>>>>> Papers
    }
}
