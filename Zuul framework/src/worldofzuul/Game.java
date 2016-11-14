package worldofzuul;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * This class controls the flow of the game, it contains the while loop that
 * keeps the game running even after a command has been issued, this recognizes
 * the function each command has, and processes both words.
 *
 * To use it, simply create an object of the type Game, and call the method
 * .play(). Written by Emil Bøgh Harder, Kasper H. Christensen, Malte Engelsted Rasmussen,
 * Matias Marek, Daniel Anton Jørgensen & Daniel Skjold Toft. Note: Commented by
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
    HashMap<UUID, Moon> _moons;
    HashMap<UUID, NPC> _npcs;
    HashMap<UUID, Items> _items;
    private MovementCalculator _movementCalculator;
    private FileHandler _fileHandler;
    private Conversation _currentConversation;
    
    private UUID _startingPlanet;

    /**
     * Constructor for the class Game, using the method createRooms() it creates
     * the rooms, sets current room and creates a new parser object
     */
    public Game() {
        this._planets = new HashMap<>();
        this._npcs = new HashMap<>();
        this._items = new HashMap<>();
        this._movementCalculator = new MovementCalculator();
        this._fileHandler = new FileHandler();
        
        this._startingPlanet = this.createPlanets();
        this.createNpcs();
        this.createItems();
        
        
        parser = new Parser(); //Creates a new object of the type Parser
        this._player = new Player(this._startingPlanet, 100, 10);
        this._dashboard = new Dashboard(); // Creates a new object of the type Dashboard. 
        

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

        this._player.setCurrentPlanet(this._startingPlanet);
        
        //Start conversation or use the greet command for first encounter?
        this.startConversation(this._planets.get(this._player.getPlanetId()).getNpcIds()[0]);
        
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
        } else if (commandWord == CommandWord.DROP) {
            this.dropItem(command.getSecondWord());
        } else if (commandWord == CommandWord.PRINT) {
            this.whichPrint(command.getSecondWord());
        } else if (commandWord == CommandWord.SCAN) {
            this.whichScan(command.getSecondWord());
        } else if (commandWord == CommandWord.SAY) {
            this.processAnswer(command.getSecondWord());
        } else if(commandWord == CommandWord.GREET) {
            this.processGreet(command.getSecondWord());
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
        } else if(secondWord.equals("npcs")) {
            this.printPossibleNpcs();
        } else if(this.printSpecPlanet(secondWord)) {
            
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
        ArrayList<Planet> planetList = this.getPossiblePlanets(this._planets.get(currentPlanetId).getXCoor(), this._planets.get(currentPlanetId).getYCoor(), this._player.getFuel());
        for (Planet planet : planetList) {
            if(this._player.getPlanetId() == planet.getId()) { continue; }
            toPrint += planet.getReferenceNum() + ": " + planet.getName() + ", ";
        }
        this._dashboard.print(toPrint);
    }
    
    public void printPossibleNpcs() {
        if(this._planets.get(this._player.getPlanetId()).getNpcIds().length < 0) {
            this._dashboard.print("There is no NPCs to talk to at this location!");
            return;
        }
        
        this._dashboard.print("These are the NPCs you can talk to here: ");
        for(UUID npcUuid : this._planets.get(this._player.getPlanetId()).getNpcIds()) {
            NPC npc = this._npcs.get(npcUuid);
            this._dashboard.print(npc.getReferenceNumber() + ": " + npc.getName() + " is described as " + npc.getDescription());
        }
        this._dashboard.print("Use the command \"greet [id]\" to start a conversation with the NPC.");
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
        UUID currentPlanetId = this._player.getPlanetId();
        this._dashboard.print("Current planet name:  " + this._planets.get(currentPlanetId).getName());
        this._dashboard.print("This is your current position: " + "(" + this._planets.get(currentPlanetId).getXCoor() + ";" + this._planets.get(currentPlanetId).getYCoor() + ")");
    }

    /**
     * Prints information about the inventory, if it is empty, it does not tell the player how to drop an item
     */
    public void printInventory() {
        for(UUID uuid : this._player.getInventoryUuids()) {
            Items curItems = this._items.get(uuid);
            this._dashboard.print(curItems.getReferenceNumber() + ": " + curItems.getDescription() + " weighting " + curItems.getWeight());
            Planet deliveryPlanet = this._planets.get(this._npcs.get(curItems.getNpcId()).getPlanetId());
            this._dashboard.print(" - and it has to be delivered at: [" + deliveryPlanet.getXCoor() + ";" + deliveryPlanet.getYCoor() + "] " + deliveryPlanet.getName());
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
        Planet nextPlanet = this._planets.get(planetId), curPlanet = this._planets.get(characterToTravel.getPlanetId());
        if(this._movementCalculator.isReachable(curPlanet.getXCoor(), curPlanet.getYCoor(), nextPlanet.getXCoor(), nextPlanet.getYCoor(), characterToTravel.getFuel())) {
            this._dashboard.print("Now traveling to " + this._planets.get(planetId).getName());
            characterToTravel.setCurrentPlanet(planetId);

            this._dashboard.print("Refilled fuel tank!");
            this._player.setFuel(this._player.getMaxFuel());
            
            this._dashboard.print("Use the \"greet [id]\" to start a conversation with an NPC. Use \"scan npcs\" to show which NPCs are on this planet.");
            
            //this.startConversation(this._planets.get(planetId).getNpcIds()[0]);
        } else {
            this._dashboard.print("Sorry, you're unable to reach the planet you were trying to travel to, try moving to a closer planet and try again.");
        }
    }
    
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
        
        if(secondWordNumber != -1) {
            for(UUID npcUuid : this._planets.get(this._player.getPlanetId()).getNpcIds()) {
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
        
        //UUID npcId = this._planets.get(this._player.getPlanetId()).getNpcId();
        if(!this._planets.get(this._player.getPlanetId()).hasNpcId(this._currentConversation.getNpcId())) {
            this._dashboard.print("Sorry, you're no longer at the same position as the NPC and can therefore not talk with him!");
            this._currentConversation = null;
            return;
        }
        
        this._currentConversation.processAnswer(answer.toLowerCase());
        if(this._currentConversation.hasCurrentAnswer()) {
            this._dashboard.print(this._npcs.get(this._currentConversation.getNpcId()).getName() + ": " + this._currentConversation.getReactText());
            if(!this.processExecution(this._currentConversation.getExecutionLine(), this._currentConversation.getNpcId())) {
                if(this._currentConversation.getNextLineNumber() == -1) {
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
                this.deliverPackage(npcId);
            } else if(executionSplit[0].equals("pickupPackage")) {
                //Where should the conversation go if you do not have space?
                if(!this.pickupPackage(npcId)) {
                    //You were unable to pick up all the items the NPC has, so what should happen now? Terminate conversation? Head to another question?
                    //"checkPickup" will only check for one item, should this too?
                }
            } else if(executionSplit[0].equals("nextConvoId")) {
                try {
                    int convoId = Integer.parseInt(executionSplit[1]);
                    this._npcs.get(npcId).setNextConversationId(convoId);
                } catch(NumberFormatException e) {
                    
                }
            } else if(executionSplit[0].equals("checkPackage")) {
                this.checkPackage(npcId, executionSplit[1]);
                changedQuestion = true;
            } else if(executionSplit[0].equals("checkPickup")) {
                this.checkPickup(npcId, executionSplit[1]);
                changedQuestion = true;
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
    
    public void deliverPackage(UUID npcId) {
        Items item = this._items.get(this._npcs.get(npcId).getPackageId());
        this._player.setReputation(this._player.getReputation() + item.getReputationWorth());
        this._player.removeItem(item.getId(), item.getWeight());
    }
    
    public boolean pickupPackage(UUID npcId) {
        for(UUID itemUuid : this._npcs.get(npcId).getInventoryUuids()) {
            if(this._player.addItem(itemUuid, this._items.get(itemUuid).getWeight())) {
                this._dashboard.print("You picked up " + this._items.get(itemUuid).getDescription());
                this._npcs.get(npcId).removeItem(itemUuid, this._items.get(itemUuid).getWeight());
            } else {
                this._dashboard.print("You were unable to pick up " + this._items.get(itemUuid).getDescription() + ", since you don't have space in your inventory!");
                return false;
            }
        }
        return true;
    }
    
    public void checkPackage(UUID npcId, String executionSplit) {
        String[] whichQuestion = executionSplit.split("|");
        //System.out.println(whichQuestion[0] + " " + whichQuestion[1] + " " + whichQuestion[2]);
        int[] questionNumbers = new int[2];
        try {
            questionNumbers[0] = Integer.parseInt(whichQuestion[0]);
            questionNumbers[1] = Integer.parseInt(whichQuestion[2]);
        } catch(NumberFormatException e) {
            System.out.println("Runtime error?");
        }
        
        for(UUID itemUuid : this._player.getInventoryUuids()) {
            if(this._npcs.get(npcId).getPackageId() == itemUuid) {
                this._currentConversation.setNextQuestion(questionNumbers[0]);
                return;
            }
        }
        
        //System.out.println("Setting question to second option! which is: " + questionNumbers[1]);
        this._currentConversation.setNextQuestion(questionNumbers[1]);
    }
    
    public void checkPickup(UUID npcId, String executionSplit) {
        String[] whichQuestion = executionSplit.split("|");
        int[] questionNumbers = new int[2];
        try {
            questionNumbers[0] = Integer.parseInt(whichQuestion[0]);
            questionNumbers[1] = Integer.parseInt(whichQuestion[2]);
        } catch(NumberFormatException e) {

        }
        
        if(this._npcs.get(npcId).getInventoryUuids().length > 0) { //What? Can the NPC have no items? Then why check for it?
            Items curItem = this._items.get(this._npcs.get(npcId).getInventoryUuids()[0]);
            if(this._player.hasInventorySpaceFor(curItem.getWeight())) {
                this._currentConversation.setNextQuestion(questionNumbers[0]);
                return;
            }
        } 
        
        this._currentConversation.setNextQuestion(questionNumbers[1]);
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
            return null;
        }
        
        for(Planet planet : this._planets.values()) {
            if(planetNumber == planet.getReferenceNum()) {
                return planet.getId();
            }
        }
        
        //Print the valid planet names!
        this.printAllPlanets();
        
        return null;
    }
    
    /**
     * Changes a item reference number to the item's UUID.
     * Can catch an exception
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
        
        for(Items item : this._items.values()) {
            if(itemNumber == item.getReferenceNumber()) {
                return item.getId();
            }
        }
        
        
        //Print the valid item names!
        //this.printInventory();
        
        return null;
    }
    
    /**
     * Drops an item according to it's id, if the item id is not recognized, it will print so
     * @param itemName the second word that the user typed in
     */
    public void dropItem(String itemReferenceNumber) {
        UUID itemUuid = this.getItemIdFromReferenceNumber(itemReferenceNumber);
        
        for(UUID itemId : this._player.getInventoryUuids()) {
            if(itemId == itemUuid) {
                this._player.removeItem(itemId, this._items.get(itemId).getWeight());
                this._player.setReputation(this._player.getReputation()-this._items.get(itemId).getReputationWorth());
                return;
            }
        }
        this._dashboard.print("Sorry, you do not hold such item id, please use \"print inventory\" to show your items and their ids.");
    }

    /**
     * Creates the planets!
     * @return what UUID the player should be starting on
     */
    public UUID createPlanets() {
        this.createMoons();
        
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
        
        
        Planet newPlanet = new Planet("hej", "wow!", 1, 1, new Moon("wow1 moon!"), 0);
        this._planets.put(newPlanet.getId(), newPlanet);
        
        newPlanet = new Planet("Starter!", "starterdesc!", 20, 20, new Moon("wowmoon2!"), 1);
        this._planets.put(newPlanet.getId(), newPlanet);
        return newPlanet.getId();
        
    }
    
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
    }
    
    /**
     * Creates the NPCs
     */
    public void createNpcs() {
        ArrayList<Planet> hasNoNpc = new ArrayList<>();
        HashMap<Integer, Planet> planetPids = new HashMap<>();
        ArrayList<NPC> hasNoPid = new ArrayList<>();
        int index;
        for(Planet planet : this._planets.values()) {
            hasNoNpc.add(planet);
            planetPids.put(planet.getPid(), planet);
        }
        
        //A method for creating NPCs
        UUID curId = UUID.randomUUID();
        this._npcs.put(curId, new NPC("Planet1NPC", "He be wow!", -1, 0, 1, curId));
        
        curId = UUID.randomUUID();
        this._npcs.put(curId, new NPC("Planet2NPC", "He be not wow!!", 1, 1, 1, curId));
        
        curId = UUID.randomUUID();
        this._npcs.put(curId, new NPC("Planet2NPC2", "He be not wow!!", 1, 1, 1, curId));
        
        //After creating NPCs using JSON
        for(NPC npc : this._npcs.values()) {
            if(npc.getPid() == -1) {
                hasNoPid.add(npc);
            } else {
                planetPids.get(npc.getPid()).addNpcId(npc.getId());
                hasNoNpc.remove(planetPids.get(npc.getPid()));
            }
        }
        
        int i = 0;
        for(NPC npc : hasNoPid) {
            if(hasNoNpc.isEmpty()) {
                break;
            }
            
            if(i > hasNoNpc.size()) {
                i = 0;
            }
            
            hasNoNpc.get(i).addNpcId(npc.getId());
            hasNoNpc.remove(i);
            i++;
        }
        
        Planet[] planets = new Planet[this._planets.size()];
        this._planets.values().toArray(planets);
        i = (int)(Math.random()*this._planets.size());
        for(NPC npc : hasNoPid) {
            if(i > this._planets.size()) {
                i = 0;
            }
            
            planets[i].addNpcId(npc.getId());
            i++;
        }
        
        //createRebels();
        
    }
    
    private void createRebels() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void createItems() {
        ArrayList<Items> itemsUsed = new ArrayList<>();
        ArrayList<Items> itemsHaveNoDelivery = new ArrayList<>();
        ArrayList<Items> itemsHaveNoPickup = new ArrayList<>();
        ArrayList<NPC> npcsHaveNoDelivery = new ArrayList<>();
        ArrayList<NPC> npcsHaveNoPickup = new ArrayList<>();
        
        HashMap<Integer, Items> itemsWithRid = new HashMap<>(); //Could just a be a list, as the key is never used
        HashMap<Integer, Items> itemsWithPid = new HashMap<>(); //Could just a be a list, as the key is never used
        HashMap<Integer, NPC> npcsWithRid = new HashMap<>();
        HashMap<Integer, NPC> npcsWithPid = new HashMap<>();
        
        //Filling the lists for NPC, because this will be used in the "create items" block
        for(NPC npc : this._npcs.values()) {
            npcsHaveNoDelivery.add(npc);
            npcsHaveNoPickup.add(npc);
            if(npc.getRid() != -1) {
                npcsWithRid.put(npc.getRid(), npc);
            } else if(npc.getPid() != -1) {
                npcsWithPid.put(npc.getPid(), npc);
            }
        }
        
        //Creating the items list
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
            }
        }
        
        //Fill up itemsUsed, so that it has as many items as there are NPCs
        ArrayList<Items> allItems = new ArrayList<>(this._items.values());
        while(itemsUsed.size() < this._npcs.size()) {
            while(true) {
                int randomIndex = (int)(Math.random()*this._items.size());
                if(itemsUsed.contains(allItems.get(randomIndex))) {
                    continue;
                }
                itemsUsed.add(allItems.get(randomIndex));
                break;
            }
        }
        
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
        
        //START: Adding receivers to both items and npcs
        for(Items item : itemsWithRid.values()) {
            if(npcsWithRid.containsKey(item.getRid())) {
                NPC npc = npcsWithRid.get(item.getRid());
                npc.setPackageId(item.getId());
                item.setNpcId(npc.getId());
                
                itemsHaveNoDelivery.remove(item);
                npcsHaveNoDelivery.remove(npc);
            }
        }
        
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
        
        //START: Adding where the items are going to be picked up
        for(Items item : itemsWithPid.values()) {
            if(npcsWithPid.containsKey(item.getPid())) {
                NPC npc = npcsWithPid.get(item.getPid());
                npc.addItem(item.getId(), item.getWeight());
                
                itemsHaveNoPickup.remove(item);
                npcsHaveNoPickup.remove(npc);
            }
        }
        
        for(Items item : itemsHaveNoPickup) {
            if(npcsHaveNoPickup.size() > 0) {
                while(true) {
                    int randomNpcIndex = (int)(Math.random()*npcsHaveNoPickup.size());
                    NPC npc = npcsHaveNoPickup.get(randomNpcIndex);
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
}
