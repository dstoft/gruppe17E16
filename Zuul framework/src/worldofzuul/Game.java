package worldofzuul;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

/**
 * This class controls the flow of the game, it contains the while loop that
 * keeps the game running even after a command has been issued, this recognizes
 * the function each command has, and processes both words.
 *
 * To use it, simply create an object of the type Game, and call the method
 * .play(). Written by Emil Bøgh Harder, Kasper H. Christensen, Malte Engelsted
 * Rasmussen, Matias Marek, Daniel Anton Jørgensen & Daniel Skjold Toft. Note:
 * Commented by Gruppe 17, E16, Software/IT 1. semester
 *
 *
 * @author Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */
public class Game {

    //Defines instance variables
    private Parser parser;
    private Dashboard dashboard;
    private Player player;
    private HashMap<UUID, Planet> planets;
    private HashMap<UUID, Moon> moons;

    /**
     * Three maps of NPCs, the first one, npcs, holds all of the npcs, which is
     * used for starting a conversation, figuring out which npc is adressed,
     * etc. The difference of civilians and rebels are simply their movement
     * pattern. Rebels only move to and from moons, where civilians only move to
     * and from planets. The creation of a list allows methods of handling both
     * civilians and rebels, when there is no difference, f.ex. creation of a
     * new conversation. The only thing needed by the NPC is the conversation
     * id, which is common for both NPC types. The only reason for the
     * separation into two maps, is purely due to the movement pattern, as that
     * is the only thing that separates them.
     */
    private HashMap<UUID, NPC> npcs;
    private HashMap<UUID, NPC> civilians;
    private HashMap<UUID, NPC> rebels;
    private HashMap<UUID, Items> items;
    private MovementCalculator movementCalculator;
    private FileHandler fileHandler;
    private Conversation currentConversation;
    private int time;
    private UUID startingPlanet;

    /**
     * Constructor for the class Game, using the method createRooms() it creates
     * the rooms, sets current room and creates a new parser object
     */
    public Game() {
        this.planets = new HashMap<>();
        this.moons = new HashMap<>();
        this.npcs = new HashMap<>();
        this.civilians = new HashMap<>();
        this.rebels = new HashMap<>();
        this.items = new HashMap<>();
        this.movementCalculator = new MovementCalculator();
        this.fileHandler = new FileHandler();

        this.startingPlanet = this.createPlanets();
        this.createNpcs();
        this.createItems();
        this.time = 0;

        parser = new Parser(); //Creates a new object of the type Parser
        this.player = new Player(this.startingPlanet, 10000, 10);
        this.dashboard = new Dashboard(); // Creates a new object of the type Dashboard. 

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

        this.printHighScore();

        this.player.setCurrentPlanet(this.startingPlanet);

        //Start conversation or use the greet command for first encounter?
        this.startConversation(this.planets.get(this.player.getPlanetId()).getNpcIds()[0]);

        //Note, the while-loop below, is basically a do..while loop, because the value to check is set to false right before the loop itself
        //meaning, no matter what, the loop will run through at least once
        boolean finished = false;
        while (!finished) { //While it is not finished
            this.dashboard.print();
            Command command = parser.getCommand(); //Returns a new object, holding the information, regarding the line typed by the user
            finished = processCommand(command); //Saves the boolean, whether the player wants to quit, in finished,
        }
        this.dashboard.print("Thank you for playing.  Good bye."); //Print an end statement, this only happens when the game stops
    }

    /**
     * A simple method to print a small welcome message and the description of
     * the starting room
     */
    private void printWelcome() {
        this.dashboard.print();
        this.dashboard.print("Welcome to the World of Zuul!");
        this.dashboard.print("World of Zuul is a new, incredibly boring adventure game.");
        this.dashboard.print("Type '" + CommandWord.HELP + "' if you need help."); //Command.HELP is found in the enum CommandWord, this returns the string corresponding to it
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
            this.dashboard.print("I don't know what you mean..."); //Print a simple String
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
            this.travelToPlanet(this.player, planetId);

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
        } else if (commandWord == CommandWord.GREET) {
            this.processGreet(command.getSecondWord());
        } else if (commandWord == CommandWord.WARP) {
            UUID planetId = this.getPlanetIdFromReferenceNumber(command.getSecondWord());
            if (planetId == null) {
                return false;
            }
            this.processWarp(this.player, planetId);
            incrementTime(1); // Adds 1 to the time
        }

        return wantToQuit; //Return the boolean, whether the player wants to quit or not
    }

    /**
     * Prints a small message regarding the game, and prints all available
     * commands
     */
    private void printHelp() {
        //Prints a few statements regarding the state of the game
        this.dashboard.print("You are lost. You are alone. You wander");
        this.dashboard.print("around at the university.");
        this.dashboard.print();
        this.dashboard.print("Your command words are:");

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
            this.dashboard.print("Quit what?"); //meaning the game won't quit!
            return false; //Returns false, meaning the system will not quit
        } else { //If there is no second word,
            saveHighScore();
            return true; //Return true, meaning the game will quit!
        }
    }

    /**
     * Calculates the planets that a certain position can travel to, based on
     * the amount of fuel. Uses Game's list of all planets, and
     * movementcalculator
     *
     * @param startX starting position
     * @param startY starting position
     * @param currentFuel the amount of fuel that can be expended
     * @return a list of planets that are possible to travel to
     */
    public ArrayList<Planet> getPossiblePlanets(int startX, int startY, int currentFuel) {
        ArrayList<Planet> reachablePlanets = new ArrayList<>();
        for (Planet planet : this.planets.values()) {
            if (this.movementCalculator.isReachable(startX, startY, planet.getx(), planet.gety(), currentFuel)) {
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
            this.dashboard.print("The second word in the command was not recognized, please use one of the following second words (like \"scan all\"):");
            this.dashboard.print("\"all\", for printing all planets\n\"possible\", for printing all planets you can reach\n[planet id], for getting the description of a specific planet");
            return;
        }

        if (secondWord.equals("all")) {
            this.printAllPlanets();
        } else if (secondWord.equals("possible")) {
            this.printPossiblePlanets();
        } else if (secondWord.equals("npcs")) {
            this.printPossibleNpcs();
        } else if (this.printSpecPlanet(secondWord)) {

        } else {
            this.dashboard.print("\"" + secondWord + "\" was not recognized, please use: "
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
        this.dashboard.print("This is a list of all planets and their ids:");
        String toPrint = "";
        for (Planet planet : this.planets.values()) {
            toPrint += planet.getReferenceNum() + ": " + planet.getName() + ", ";
        }
        this.dashboard.print(toPrint);

    }

    /**
     * Print the possible planets that the player can travel to
     */
    public void printPossiblePlanets() {
        String toPrint = "";
        UUID currentPlanetId = this.player.getPlanetId();
        int[] currentPosition = getPositionCoordinates(currentPlanetId);

        if (this.planets.containsKey(currentPlanetId)) {
            if (this.planets.get(currentPlanetId).hasMoon()) {
                Moon moon = this.moons.get(this.planets.get(currentPlanetId).getMoonUuid());
                toPrint += "0: " + moon.getName() + ", ";
            }
        }

        ArrayList<Planet> planetList = this.getPossiblePlanets(currentPosition[0], currentPosition[0], this.player.getFuel());
        for (Planet planet : planetList) {
            if (this.player.getPlanetId() == planet.getId()) {
                continue;
            }
            toPrint += planet.getReferenceNum() + ": " + planet.getName() + ", ";
        }
        this.dashboard.print(toPrint);
    }

    /**
     * Prints the NPCs that the user can currently talk to, f.ex. when arriving
     * a planet or moon. This method can be called during runtime using the
     * command "scan npcs".
     */
    public void printPossibleNpcs() {
        NPCHolder npcHolder = getNPCHolderFromUuid(this.player.getPlanetId());

        if (npcHolder.getNpcIds().length < 0) {
            this.dashboard.print("There is no NPCs to talk to at this location!");
            return;
        }

        this.dashboard.print("These are the NPCs you can talk to here: ");
        for (UUID npcUuid : npcHolder.getNpcIds()) {
            NPC npc = this.npcs.get(npcUuid);
            this.dashboard.print(npc.getReferenceNumber() + ": " + npc.getName() + " is described as " + npc.getDescription());
        }
        this.dashboard.print("Use the command \"greet [id]\" to start a conversation with the NPC.");
    }

    /**
     * A method to figuring out what is to happen based on the second word
     *
     * @param secondWord the second word that the user typed in
     */
    public void whichPrint(String secondWord) {
        if (secondWord == null) {
            this.dashboard.print("The second word in the command was not recognized, please use one of the following second words (like \"print stats\"):");
            this.dashboard.print("\"stats\", for viewing your stats\n\"position\", for viewing your position\n\"inventory\", for getting information about your inventory");
            return;
        }

        if (secondWord.equals("stats")) {
            this.printPlayerStats();
        } else if (secondWord.equals("position")) {
            this.printPlayerPosition();
        } else if (secondWord.equals("inventory")) {
            this.printInventory();
        } else {
            this.dashboard.print("The second word you wrote is not recognized, please only use: stats, position or invetory!");
        }
    }

    /**
     * A method for printing the player's stats
     */
    public void printPlayerStats() {
        this.dashboard.print("Current fuel: " + this.player.getFuel());
        this.dashboard.print("Current reputation: " + this.player.getReputation());
        this.dashboard.print("You have used " + this.checkTimers() + " time");
        this.dashboard.print("You have " + this.player.getWarpfuel() + " warp fuel");
    }

    /**
     * Prints the player's current planet's position and name
     */
    public void printPlayerPosition() {
        UUID currentPlanetId = this.player.getPlanetId();
        NPCHolder npcHolder = getNPCHolderFromUuid(currentPlanetId);
        int[] currentPosition = getPositionCoordinates(currentPlanetId);
        this.dashboard.print("Current planet name:  " + npcHolder.getName());
        this.dashboard.print("This is your current position: " + "(" + currentPosition[0] + ";" + currentPosition[1] + ")");
    }

    /**
     * Prints information about the inventory, if it is empty, it does not tell
     * the player how to drop an item
     */
    public void printInventory() {
        for (UUID uuid : this.player.getInventoryUuids()) {
            Items curItems = this.items.get(uuid);
            this.dashboard.print(curItems.getReferenceNumber() + ": " + curItems.getDescription() + " weighting " + curItems.getWeight());

            if (this.planets.containsKey(this.npcs.get(curItems.getNpcId()).getPlanetId())) {
                Planet deliveryPlanet = this.planets.get(this.npcs.get(curItems.getNpcId()).getPlanetId());
                this.dashboard.print(" - and it has to be delivered at: [" + deliveryPlanet.getx() + ";" + deliveryPlanet.gety() + "] " + deliveryPlanet.getName());
            } else {
                Moon deliveryMoon = this.moons.get(this.npcs.get(curItems.getNpcId()).getPlanetId());
                Planet parentPlanet = this.planets.get(deliveryMoon.getParentPlanetUuid());
                this.dashboard.print(" - and it has to be delivered at the moon called " + deliveryMoon.getName() + " of the planet: [" + parentPlanet.getx() + ";" + parentPlanet.gety() + "] " + parentPlanet.getName());
            }
            
            this.dashboard.print("- and it has to be delivered before the time " + curItems.getDeliveryTime() + " is reached");
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
            this.planets.get(id).getDescription();
            return true;
        }
    }

    /**
     * Changes the position (planet) of the character refered in the parameter
     *
     * @param characterToTravel which character to move
     * @param planetId which planet to move to
     */
    public void travelToPlanet(Player characterToTravel, UUID nextPositionUuid) {
        int[] currentPosition = getPositionCoordinates(this.player.getPlanetId());
        int[] nextPosition = getPositionCoordinates(nextPositionUuid);
        NPCHolder nextNpcHolder = getNPCHolderFromUuid(nextPositionUuid);

        if (this.movementCalculator.isReachable(currentPosition[0], currentPosition[1], nextPosition[0], nextPosition[1], characterToTravel.getFuel())) {
            this.dashboard.print("Now traveling to " + nextNpcHolder.getName());
            characterToTravel.setCurrentPlanet(nextPositionUuid);

            tryNpcMovement();

            this.dashboard.print("Refilled fuel tank!");
            this.player.setFuel(this.player.getMaxFuel());

            int travelTime = 10;
            incrementTime(this.movementCalculator.calculateDistance(currentPosition[0], currentPosition[1], nextPosition[0], nextPosition[1]) / travelTime);

            this.dashboard.print("Use the \"greet [id]\" to start a conversation with an NPC. Use \"scan npcs\" to show which NPCs are on this planet.");
        } else {
            this.dashboard.print("Sorry, you're unable to reach the planet you were trying to travel to, try moving to a closer planet and try again.");
        }
    }

    /**
     * A method used for processing the "warp" command during runtime. Looks
     * very much like the travelToPlanet method. However this uses the Warp fuel
     * as a limiting factor. As Warp fuel is different from regular fuel it also
     * uses different movement calculations. There is a possibility of not
     * traveling, if you do not have enough warp fuel.
     *
     * @param characterToTravel which character that should be moved
     * @param nextPositionUuid which planet or moon that is the intended target.
     */
    public void processWarp(Player characterToTravel, UUID nextPositionUuid) {
        int[] currentPosition = getPositionCoordinates(this.player.getPlanetId());
        int[] nextPosition = getPositionCoordinates(nextPositionUuid);
        NPCHolder nextNpcHolder = getNPCHolderFromUuid(nextPositionUuid);

        if (this.movementCalculator.isWarpReachable(currentPosition[0], currentPosition[1], nextPosition[0], nextPosition[1], characterToTravel.getWarpfuel())) {
            this.dashboard.print("Now warping to " + nextNpcHolder.getName());
            characterToTravel.setCurrentPlanet(nextPositionUuid);
            characterToTravel.setWarpfuel(characterToTravel.getWarpfuel() - this.movementCalculator.calculateWarpFuelUsage(currentPosition[0], currentPosition[1], nextPosition[0], nextPosition[1]));

            this.dashboard.print("Use the \"greet [id]\" to start a conversation with an NPC. Use \"scan npcs\" to show which NPCs are on this planet.");
        } else {
            this.dashboard.print("Sorry, you're unable to reach the planet you were trying to warp to, try moving to a closer planet and try again.");
        }
    }

    /**
     * A method used for processing the "greet" command during runtime.
     *
     * @param secondWord
     */
    public void processGreet(String secondWord) {
        if (secondWord == null) {
            this.dashboard.print("Use the greet command by writting \"greet [id]\". Write \"scan npcs\" to show possible NPCs and their ids.");
            return;
        }

        int secondWordNumber = -1;
        try {
            secondWordNumber = Integer.parseInt(secondWord);
        } catch (NumberFormatException e) {

        }

        NPCHolder npcHolder = getNPCHolderFromUuid(this.player.getPlanetId());

        if (secondWordNumber != -1) {
            for (UUID npcUuid : npcHolder.getNpcIds()) {
                if (secondWordNumber == this.npcs.get(npcUuid).getReferenceNumber()) {
                    this.startConversation(this.npcs.get(npcUuid).getId());
                    return;
                }
            }
        } else {
            this.dashboard.print("NPCid was not recognized, please only use the id numbers to refer to NPCs. Write \"scan npcs\" to show possible NPCs and their ids.");
        }
    }

    /**
     * A method for starting a conversation with the NPC on the planet, that the
     * player is currently at
     */
    public void startConversation(UUID npcId) {
        //IF the NPC has a nextConversationId (if it is not null) use that!
        // Starting conversation!
        //UUID npcId = this.planets.get(this.player.getPlanetId()).getNpcIds()[0];
        NPC npc = this.npcs.get(npcId);
        if (npc.hasNextConversationId()) {
            npc.setConversationId(npc.getNextConversationId());
            npc.setNextConversationId(-1);
        }
        this.currentConversation = new Conversation(npc.getConversationId());
        this.currentConversation.setNpcId(npcId);
        this.currentConversation.createWholeConversation(this.fileHandler.getText("data/alpha_centauri/conversations/" + npc.getConversationId() + ".txt"));
        this.dashboard.print("A connection with " + npc.getName() + " has been established...");
        this.dashboard.print(npc.getName() + " looks like " + npc.getDescription());
        this.dashboard.print(npc.getName() + ": " + this.currentConversation.getQText());
        this.dashboard.print("You can answer using the \"say\" command: " + this.currentConversation.getPossibleAnswers());
    }

    /**
     * Takes in what the user has answered using the say command, and figures
     * out whether it is recognized It also calls for the execution of the
     * execution line
     *
     * @param answer is the second word that the user typed in along with say
     */
    public void processAnswer(String answer) {
        if (answer == null) {
            this.dashboard.print("You have to say something!");
            return;
        }

        if (this.currentConversation == null) {
            this.dashboard.print("Sorry, you can't use say when you have no ongoing conversation!");
            return;
        }

        NPCHolder npcHolder = getNPCHolderFromUuid(this.player.getPlanetId());

        //UUID npcId = this.planets.get(this.player.getPlanetId()).getNpcId();
        if (!npcHolder.hasNpcId(this.currentConversation.getNpcId())) {
            this.dashboard.print("Sorry, you're no longer at the same position as the NPC and can therefore not talk with him!");
            this.currentConversation = null;
            return;
        }

        this.currentConversation.processAnswer(answer.toLowerCase());
        if (this.currentConversation.hasCurrentAnswer()) {
            this.dashboard.print(this.npcs.get(this.currentConversation.getNpcId()).getName() + ": " + this.currentConversation.getReactText());
            if (!this.processExecution(this.currentConversation.getExecutionLine(), this.currentConversation.getNpcId())) {
                if (this.currentConversation.getNextLineNumber() == -1) {
                    this.currentConversation = null;
                    this.dashboard.print("Conversation has been terminated");
                    return;
                }
                this.currentConversation.setNextQuestion(this.currentConversation.getNextLineNumber());
            }
            this.dashboard.print(this.npcs.get(this.currentConversation.getNpcId()).getName() + ": " + this.currentConversation.getQText());
            this.dashboard.print("You can answer: " + this.currentConversation.getPossibleAnswers());
        } else {
            this.dashboard.print(this.npcs.get(this.currentConversation.getNpcId()).getName() + ": Sorry, I don't know how to respond to that answer.");
            this.dashboard.print("The only answers I seek: " + this.currentConversation.getPossibleAnswers());
        }
    }

    /**
     * Figures out what should happen according to the parameter executionLine,
     * and calls the relevant methods
     *
     * @param executionLine which commands that are to be executed
     * @param npcId which npc that the conversation is with
     * @return whether or not the conversation's question was changed during the
     * execution commands
     */
    public boolean processExecution(String executionLine, UUID npcId) {
        boolean changedQuestion = false;
        String[] allExecutions;
        allExecutions = executionLine.split(",");
        for (String eachExecution : allExecutions) {
            String[] executionSplit = eachExecution.split(":");
            switch (executionSplit[0]) {
                case "deliverPackage":
                    this.deliverPackage(npcId);
                    break;
                case "pickupPackage":
                    //Where should the conversation go if you do not have space?
                    if (!this.pickupPackage(npcId)) {
                        //You were unable to pick up all the items the NPC has, so what should happen now? Terminate conversation? Head to another question?
                        //"checkPickup" will only check for one item, should this too?
                    }   
                    break;
                    
                case "nextConvoId":
                    try {
                        int convoId = Integer.parseInt(executionSplit[1]);
                        this.npcs.get(npcId).setNextConversationId(convoId);
                    } catch (NumberFormatException e) {
                        
                    }   
                    break;
                    
                case "checkPackage":
                    this.checkPackage(npcId, executionSplit[1]);
                    changedQuestion = true;
                    break;
                    
                case "checkPickup":
                    this.checkPickup(npcId, executionSplit[1]);
                    changedQuestion = true;
                    break;
                    
                case "removeReputation":
                    try {
                        int reputationAmount = Integer.parseInt(executionSplit[1]);
                        this.player.setReputation((this.player.getReputation() - reputationAmount));
                    } catch (NumberFormatException e) {
                        
                    }   
                    break;
                    
                case "getPapers":
                    this.getPapers();
                    break;
                    
                default:
                    break;
            }
        }

        return changedQuestion;
    }

    /**
     * A method used for execution commands from the Conversation. The method
     * delivers a package according to the package id an NPC has. This method
     * does not include if the player has the package or not, and should
     * therefore only be executed after the "checkPackage".
     *
     * @param npcId the npc which has to receive the package
     */
    public void deliverPackage(UUID npcId) {
        Items item = this.items.get(this.npcs.get(npcId).getPackageId());
        this.player.setReputation(this.player.getReputation() + item.getReputationWorth());
        this.player.removeItem(item.getId(), item.getWeight());
        if (this.time <= item.getDeliveryTime()) {
            this.dashboard.print("Congratulations, you delivered " + item.getDescription() + " on time!");
            this.player.setReputation(this.player.getReputation() + item.getReputationWorth());
        } else {
            this.dashboard.print("Unfortunately, you did not deliver  " + item.getDescription() + " on time!");
            this.player.setReputation(this.player.getReputation() - item.getReputationWorth());
        }
        if (!item.getPapers()) {
            this.dashboard.print("Since you did not have the papers for " + item.getDescription() + " you lost some reputation. Go see the Headquarter for papers on your packages!");
            this.player.setReputation(this.player.getReputation() - (item.getReputationWorth()*3));
        }
    }

    /**
     * This method is executed from the execution commands from Conversation.
     * This method picks up every package the NPC has, if you don't have space
     * for the package you're trying to pick up, it will print that it failed.
     *
     * @param npcId the npc that the user picks up packages from
     * @return whether you succeeded or not to pick up all the packages
     */
    public boolean pickupPackage(UUID npcId) {
        incrementTime(1);
        for (UUID itemUuid : this.npcs.get(npcId).getInventoryUuids()) {
            if (this.player.addItem(itemUuid, this.items.get(itemUuid).getWeight())) {
                this.dashboard.print("You picked up " + this.items.get(itemUuid).getDescription());
                this.npcs.get(npcId).removeItem(itemUuid, this.items.get(itemUuid).getWeight());
                this.items.get(itemUuid).setDeliveryTime(this.time + 200);
            } else {
                this.dashboard.print("You were unable to pick up " + this.items.get(itemUuid).getDescription() + ", since you don't have space in your inventory!");
                return false;
            }
        }
        return true;
    }

    /**
     * A method used to execute executionlines from Conversation. It will set
     * two different next questions, according to whether the player has a
     * package UUID that is equal to what the NPC wants delivered.
     *
     * @param npcId the npc that the player wants to deliver to
     * @param executionSplit used to get the two different question numbers that
     * you have to proceed to
     */
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

        for (UUID itemUuid : this.player.getInventoryUuids()) {
            if (this.npcs.get(npcId).getPackageId() == itemUuid) {
                this.currentConversation.setNextQuestion(questionNumbers[0]);
                return;
            }
        }

        //System.out.println("Setting question to second option! which is: " + questionNumbers[1]);
        this.currentConversation.setNextQuestion(questionNumbers[1]);
    }

    /**
     * This method is used to execute executionlines from Conversation. This
     * sets two different question numbers according to whether the npc has any
     * packages to pickup or not.
     *
     * @param npcId the npc to check whether it has items to pickup or not
     * @param executionSplit used to extract which question to head to next
     */
    public void checkPickup(UUID npcId, String executionSplit) {
        String[] whichQuestion = executionSplit.split("|");
        int[] questionNumbers = new int[2];
        try {
            questionNumbers[0] = Integer.parseInt(whichQuestion[0]);
            questionNumbers[1] = Integer.parseInt(whichQuestion[2]);
        } catch (NumberFormatException e) {

        }

        if (this.npcs.get(npcId).getInventoryUuids().length > 0) { //The NPC can have 0 items
            Items curItem = this.items.get(this.npcs.get(npcId).getInventoryUuids()[0]);
            if (this.player.hasInventorySpaceFor(curItem.getWeight())) {
                this.currentConversation.setNextQuestion(questionNumbers[0]);
                return;
            }
        }

        this.currentConversation.setNextQuestion(questionNumbers[1]);
    }
    
    /**
     * This method is used to execute executionlines from Conversation.
     * This takes all of the players items and adds papers to them.
     */
    public void getPapers() {
        for(UUID uuid : this.player.getInventoryUuids()) {
            this.items.get(uuid).setPapersTrue();
        }
    }
    
    public void checkBuyWarpFuel(UUID npcId, String executionSplit) {
        
    }
    
    /**
     * Changes a planet reference number to the planet's UUID. Can catch an
     * exception
     *
     * @param secondWord the second word that the user typed in
     * @return the UUID of the corresponding planet
     */
    public UUID getPlanetIdFromReferenceNumber(String secondWord) {
        int planetNumber = -1;
        try {
            planetNumber = Integer.parseInt(secondWord);
        } catch (Exception e) {
            this.dashboard.print("Please only use id numbers to refer to which planet you want to travel to!");
            //this.dashboard.print(e.toString());
            return null;
        }

        if (planetNumber == 0) {
            UUID curUuid = this.player.getPlanetId();
            Planet curPlanet = this.planets.get(curUuid);
            if (curPlanet.hasMoon()) {
                return curPlanet.getMoonUuid();
            } else {
                this.dashboard.print("Sorry, there is no moon to travel to at this planet!");
                return null;
            }
        }

        for (Planet planet : this.planets.values()) {
            if (planetNumber == planet.getReferenceNum()) {
                return planet.getId();
            }
        }

        //Print the valid planet names!
        this.printAllPlanets();

        return null;
    }

    /**
     * Changes a item reference number to the item's UUID. Can catch an
     * exception
     *
     * @param secondWord the second word that the user typed in
     * @return the UUID of the corresponding item
     */
    public UUID getItemIdFromReferenceNumber(String secondWord) {
        int itemNumber = -1;
        try {
            itemNumber = Integer.parseInt(secondWord);
        } catch (Exception e) {
            this.dashboard.print("Invalid item id, \"" + secondWord + "\" was not recognized, use \"print inventory\" to show your items and their ids!");
            //this.dashboard.print(e.toString());
            return null;
        }

        for (Items item : this.items.values()) {
            if (itemNumber == item.getReferenceNumber()) {
                return item.getId();
            }
        }

        //Print the valid item names!
        //this.printInventory();
        return null;
    }

    /**
     * Drops an item according to it's id, if the item id is not recognized, it
     * will print so
     *
     * @param itemName the second word that the user typed in
     */
    public void dropItem(String itemReferenceNumber) {
        UUID itemUuid = this.getItemIdFromReferenceNumber(itemReferenceNumber);

        for (UUID itemId : this.player.getInventoryUuids()) {
            if (itemId == itemUuid) {
                this.player.removeItem(itemId, this.items.get(itemId).getWeight());
                this.player.setReputation(this.player.getReputation() - this.items.get(itemId).getReputationWorth());
                return;
            }
        }
        this.dashboard.print("Sorry, you do not hold such item id, please use \"print inventory\" to show your items and their ids.");
    }

    /**
     * Creates the planets!
     *
     * @return what UUID the player should be starting on
     */
    public UUID createPlanets() {
        
        UUID returnUuid = null;
        //Creating the items list
        int i = 0;
        while(true) {
            if(!this.fileHandler.doesFileExist("data/alpha_centauri/planets/" + i + ".json")) {
                break;
            }
            Planet newPlanet = this.fileHandler.getJSON("data/alpha_centauri/planets/" + i + ".json", Planet.class);
            this.planets.put(newPlanet.getId(), newPlanet);
            i++;
            
            if(newPlanet.getPid() == 0) {
                returnUuid = newPlanet.getId();
            }
        }
        
        createMoons();
        
        return returnUuid;
        
        
        /*
        Planet newPlanet = new Planet("hej", "wow!", 1, 1, 0);
        this.planets.put(newPlanet.getId(), newPlanet);

        newPlanet = new Planet("Starter!", "starterdesc!", 20, 20, 1);
        this.planets.put(newPlanet.getId(), newPlanet);

        createMoons();

        return newPlanet.getId();
        */
    }

    /**
     * This creates the moons from JSON files, and places them according to
     * their PID. This method assumes that every Moon has a PID that will match
     * a planet's PID.
     */
    public void createMoons() {
        
        int i = 0;
        while(true) {
            if(!this.fileHandler.doesFileExist("data/alpha_centauri/moons/" + i + ".json")) {
                break;
            }
            Moon newMoon = this.fileHandler.getJSON("data/alpha_centauri/moons/" + i + ".json", Moon.class);
            this.moons.put(newMoon.getId(), newMoon);
            i++;
        }
        
        /*
        Moon newMoon = new Moon("navn", "hej!", 0);
        this.moons.put(newMoon.getId(), newMoon);

        newMoon = new Moon("navn", "hej2!", 1);
        this.moons.put(newMoon.getId(), newMoon);
        */

        HashMap<Integer, Planet> planetPids = new HashMap<>();
        for (Planet planet : this.planets.values()) {
            planetPids.put(planet.getPid(), planet);
        }

        for (Moon moon : this.moons.values()) {
            if (planetPids.containsKey(moon.getPid())) {
                planetPids.get(moon.getPid()).setMoonUuid(moon.getId());
                moon.setParentPlanetUuid(planetPids.get(moon.getPid()).getId());
            }
        }
    }

    /**
     * Creates the NPCs
     */
    public void createNpcs() {
        
        int i = 0;
        while(true) {
            if(!this.fileHandler.doesFileExist("data/alpha_centauri/civilians/" + i + ".json")) {
                break;
            }
            NPC newNpc = this.fileHandler.getJSON("data/alpha_centauri/civilians/" + i + ".json", NPC.class);
            this.npcs.put(newNpc.getId(), newNpc);
            this.civilians.put(newNpc.getId(), newNpc);
            i++;
        }

        /*
        //A method for creating NPCs
        NPC newNpc = new NPC("Planet1NPC", "He be wow!", -1, 0, 1, 0);
        this.npcs.put(newNpc.getId(), newNpc);
        this.civilians.put(newNpc.getId(), newNpc);

        newNpc = new NPC("Planet2NPC", "He be not wow!!", 1, 1, 1, 0);
        this.npcs.put(newNpc.getId(), newNpc);
        this.civilians.put(newNpc.getId(), newNpc);

        newNpc = new NPC("Planet2NPC2", "He be not wow!!", 1, 1, 1, 0);
        this.npcs.put(newNpc.getId(), newNpc);
        this.civilians.put(newNpc.getId(), newNpc);
        */

        ArrayList<NPCHolder> npcHolders = new ArrayList<>();
        for (Planet planet : this.planets.values()) {
            npcHolders.add(planet);
        }
        
        placeNpcs(this.civilians.values(), npcHolders);

        createRebels();

    }

    /**
     * A method used to create the rebels from JSON files. The method calls the
     * method placeNPCs with the list of rebels and moons.
     */
    private void createRebels() {
        
        int i = 0;
        while(true) {
            if(!this.fileHandler.doesFileExist("data/alpha_centauri/rebels/" + i + ".json")) {
                break;
            }
            NPC newNpc = this.fileHandler.getJSON("data/alpha_centauri/rebels/" + i + ".json", NPC.class);
            this.npcs.put(newNpc.getId(), newNpc);
            this.rebels.put(newNpc.getId(), newNpc);
            i++;
        }
        
        /*
        //A method for creating NPCs
        NPC newNpc = new NPC("Rebel1", "He be wow!", -1, 0, 1, 0);
        this.npcs.put(newNpc.getId(), newNpc);
        this.rebels.put(newNpc.getId(), newNpc);

        newNpc = new NPC("Rebel2", "He be not wow!!", -1, 1, 1, 0);
        this.npcs.put(newNpc.getId(), newNpc);
        this.rebels.put(newNpc.getId(), newNpc);

        newNpc = new NPC("Rebel3", "He be not wow!!", -1, 1, 1, 10);
        this.npcs.put(newNpc.getId(), newNpc);
        this.rebels.put(newNpc.getId(), newNpc);
        */

        ArrayList<NPCHolder> npcHolders = new ArrayList<>();
        for (Moon moon : this.moons.values()) {
            npcHolders.add(moon);
        }
        placeNpcs(this.rebels.values(), npcHolders);
    }

    /**
     * A method for placing NPCs according to the two parameters. The method
     * goes through 3 steps when placing NPCs: 1. it tries to match NPCs with
     * PIDs and planets/moons with PIDs. Which can mean several NPCs at the same
     * planet. 2. it then adds NPCs (who has no PID) to planets/moons without
     * NPCs. 3. it then adds NPCs (who has no PID) to random planets/moons.
     * These steps makes sure, that if there is NPCs without PIDs, these NPCs
     * will be placed on empty planets/moons, and when there is no more empty
     * planets/moons, NPCs without PIDs will be placed "randomly".
     *
     * @param npcList the list of NPCs to place (this "list" comes from the
     * .values() from a HashMap)
     * @param holdersList of the type NPCHolder, which is the superclass for
     * Planets and Moons. NPCHolder holds the information and behaviour that
     * handles NPCs at planets/moons.
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
        for (NPCHolder npcHolder : holdersList) {
            hasNoNpc.add(npcHolder);
            planetPids.put(npcHolder.getPid(), npcHolder);
        }

        //Goes through the whole list of NPCs that has to be placed, and places them if they have an PID.
        //If they don't have a PID, the NPC will be added to the list "hasNoPid".
        for (NPC npc : npcList) {
            if (npc.getPid() == -1 || !planetPids.containsKey(npc.getPid())) {
                hasNoPid.add(npc);
            } else {
                planetPids.get(npc.getPid()).addNpcId(npc.getId());
                npc.setPlanetId(planetPids.get(npc.getPid()).getId());
                if(hasNoNpc.contains(planetPids.get(npc.getPid()))) {
                    hasNoNpc.remove(planetPids.get(npc.getPid()));
                }
            }
        }

        //Goes through the NPC list that has no PID and places them on empty planets/moons,
        // stops when there are not empty planets/moons left.
        int i = 0;
        for (NPC npc : hasNoPid) {
            //If the planet/moon list that has no NPC is empty, break this loop
            if (hasNoNpc.isEmpty()) {
                break;
            }

            if (i >= hasNoNpc.size()) {
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
        for (NPC npc : hasNoPid) {
            //If the NPC already has a planet, skip placing them.
            if (npc.getPlanetId() != null) {
                continue;
            }

            //Random which planet/moon that should get the next NPC
            i = (int) (Math.random() * holdersList.size());

            planets[i].addNpcId(npc.getId());
            npc.setPlanetId(planets[i].getId());
        }
    }

    /**
     * This method is used for creating items from JSON files, determining
     * receivers and placing the items. This method follows the algorithm (it is
     * simplified here): 1. Creating the items from JSON files, and filling
     * necessary lists 2. Making sure that there is as many items used run time
     * as there is NPCs (and more filling of lists) 3a. Finding and adding
     * receivers according to the RIDs of both NPCs and items. 3b. Finding and
     * adding receivers that have no RIDs 4a. Finding and placing items at the
     * right NPCs based on PIDs 4b. Finding and placing items without PIDs
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
        HashMap<Integer, Items> itemsWithIid = new HashMap<>(); //Could just a be a list, as the key is never used
        HashMap<Integer, NPC> npcsWithRid = new HashMap<>();
        HashMap<Integer, NPC> npcsWithIid = new HashMap<>();

        //Filling the lists with NPCs
        for (NPC npc : this.npcs.values()) {
            npcsHaveNoDelivery.add(npc);
            npcsHaveNoPickup.add(npc);
            if (npc.getRid() != -1) {
                npcsWithRid.put(npc.getRid(), npc);
            }
            if (npc.getIid() != -1) {
                npcsWithIid.put(npc.getIid(), npc);
            }
        }

        //1. Creating the items from JSON
        int i = 0;
        while (true) {
            if (!this.fileHandler.doesFileExist("data/alpha_centauri/items/" + i + ".json")) {
                break;
            }
            Items newItem = this.fileHandler.getJSON("data/alpha_centauri/items/" + i + ".json", Items.class);
            this.items.put(newItem.getId(), newItem);
            i++;
            if (npcsWithIid.containsKey(newItem.getIid()) || npcsWithRid.containsKey(newItem.getRid())) {
                itemsUsed.add(newItem);
            }

        }

        //2. Fill up itemsUsed, so that it has as many items as there are NPCs
        ArrayList<Items> allItems = new ArrayList<>(this.items.values());
        //As long as the size of items used is smaller than the list of NPCs
        while (itemsUsed.size() < this.npcs.size()) {
            while (true) {
                int randomIndex = (int) (Math.random() * this.items.size());

                //If the random picked item is already stated as being used,
                // it will skip the rest of the while(true) and generate a new random index.
                if (itemsUsed.contains(allItems.get(randomIndex))) {
                    continue;
                }

                itemsUsed.add(allItems.get(randomIndex));
                break;
            }
        }

        //START: Filling the lists and hashmaps for items
        for (Items item : itemsUsed) {
            itemsHaveNoDelivery.add(item);
            itemsHaveNoPickup.add(item);
            if (item.getRid() != -1) {
                itemsWithRid.put(item.getRid(), item);
                item.setPapersTrue();
            }
            if (item.getIid() != -1) {
                itemsWithIid.put(item.getIid(), item);
                item.setPapersTrue();
            } else {
                item.setPapersFalse();
            }
        }
        //END: Filling the lists and hashmaps for items

        //START: 2a. Adding receivers to both items and npcs
        for (Items item : itemsWithRid.values()) {
            //Uses the NPC HashMaps
            if (npcsWithRid.containsKey(item.getRid())) {
                NPC npc = npcsWithRid.get(item.getRid());
                npc.setPackageId(item.getId());
                item.setNpcId(npc.getId());

                itemsHaveNoDelivery.remove(item);
                npcsHaveNoDelivery.remove(npc);
            }
        }

        //2b. Adding receivers for items and NPCs without an RID
        for (Items item : itemsHaveNoDelivery) {
            if (npcsHaveNoDelivery.size() > 0) {
                int randomNpcIndex = (int) (Math.random() * npcsHaveNoDelivery.size());
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
        for (Items item : itemsWithIid.values()) {
            if (npcsWithIid.containsKey(item.getIid())) {
                NPC npc = npcsWithIid.get(item.getIid());
                npc.addItem(item.getId(), item.getWeight());

                itemsHaveNoPickup.remove(item);
                npcsHaveNoPickup.remove(npc);
            }
        }

        //3b. Adding where items without and PID are going to be picked up
        for(NPC npc : npcsHaveNoPickup) {
            if(itemsHaveNoPickup.size() <= 0) {
                System.out.println("Something went wrong");
                break;
            }
            if(itemsHaveNoPickup.size() == 2) {
                if(itemsHaveNoPickup.get(0).getId() == npc.getPackageId()) {
                    itemsHaveNoPickup.get(1).setNpcId(npc.getId());
                    npc.addItem(itemsHaveNoPickup.get(1).getId(), itemsHaveNoPickup.get(1).getWeight());
                    itemsHaveNoPickup.remove(1);
                } else {
                    itemsHaveNoPickup.get(0).setNpcId(npc.getId());
                    npc.addItem(itemsHaveNoPickup.get(0).getId(), itemsHaveNoPickup.get(0).getWeight());
                    itemsHaveNoPickup.remove(0);
                }
                continue;
            }
            while(true) {
                int randomItemIndex = (int) (Math.random() * itemsHaveNoPickup.size());
                Items item = itemsHaveNoPickup.get(randomItemIndex);
                
                if(npc.getPackageId() == item.getId()) {
                    
                    //System.out.println("infinte?");
                    continue;
                }
                
                item.setNpcId(npc.getId());
                npc.addItem(item.getId(), item.getWeight());
                itemsHaveNoPickup.remove(item);
                break;
            }
        }
        //END: Adding where the items are going to be picked up
    }

    /**
     * Gets a NPCHolder object from an UUID, this can either be a planet or moon
     * UUID.
     *
     * @param positionUuid a planet or moon UUID
     * @return the NPCHolder object
     */
    public NPCHolder getNPCHolderFromUuid(UUID positionUuid) {
        if (this.planets.containsKey(positionUuid)) {
            return this.planets.get(positionUuid);
        } else {
            return this.moons.get(positionUuid);
        }
    }

    /**
     * Gets the coordinates of a position based on a UUID
     *
     * @param positionUuid a planet or moons UUID
     * @return an integer array of the size 2, with the x on index 0 and y on
     * index 1
     */
    public int[] getPositionCoordinates(UUID positionUuid) {
        Planet planet;
        if (this.planets.containsKey(positionUuid)) {
            planet = this.planets.get(positionUuid);
        } else {
            planet = this.planets.get(this.moons.get(positionUuid).getParentPlanetUuid());
        }
        int[] returnArray = new int[2];
        returnArray[0] = planet.getx();
        returnArray[1] = planet.gety();
        return returnArray;

    }

    /**
     * This method prepares and calls the method that does the actual
     * calculation for whether the NPC should move or not.
     */
    public void tryNpcMovement() {
        ArrayList<NPCHolder> npcHolders = new ArrayList<>();
        for (Moon moon : this.moons.values()) {
            npcHolders.add(moon);
        }
        tryNpcMovementCalculations(this.rebels.values(), npcHolders);

        npcHolders = new ArrayList<>();
        for (Planet planet : this.planets.values()) {
            npcHolders.add(planet);
        }
        tryNpcMovementCalculations(this.civilians.values(), npcHolders);
    }

    /**
     * A method that goes through all of the NPCs passed in the parameter and
     * places them at the planets/moons passed in the parameter.
     *
     * @param npcList The NPCs that has to be placed
     * @param holdersList The places the NPCs can be placed
     */
    public void tryNpcMovementCalculations(Collection<NPC> npcList, ArrayList<NPCHolder> holdersList) {
        for (NPC npc : npcList) {
            NPCHolder[] npcHolders = new NPCHolder[holdersList.size()];
            holdersList.toArray(npcHolders);

            if (npc.getChanceToMove() > 0) {
                int randomNumber = (int) (Math.random() * 10);
                if (npc.getChanceToMove() > randomNumber) {
                    int randomPlanet = (int) (Math.random() * npcHolders.length);

                    //Make sure that the random generated new position, 
                    // is not already the NPC's position.
                    while (npcHolders[randomPlanet].getId() == npc.getPlanetId()) {
                        randomPlanet = (int) (Math.random() * npcHolders.length);
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
     * Prints both the highscore fetched from the highscore file and the current
     * player's highscore
     */
    public void printHighScore() {
        HighScore currentHighScore = this.fileHandler.getJSON("highscore.json", HighScore.class);
        HighScore playerScore = new HighScore(this.player.getReputation(), 2, "matias");
        this.dashboard.print(currentHighScore.toString());
        this.dashboard.print(playerScore.toString());
    }

    /**
     * Checks the current player's highscore, and if that highscore is better
     * than the one fetched from the JSON file, save the current player's
     * highscore as the highest.
     */
    public void saveHighScore() {
        //Creates a new highsore object based on the current player's stats
        HighScore playerScore = new HighScore(this.player.getReputation(), 2, "matias");  // tid : 2 og name :  matias er blot place holders. 

        //Read the highscore JSON file, if it exists!
        if (!this.fileHandler.doesFileExist("highscore.json")) {
            this.fileHandler.writeToFile("highscore.json", playerScore.toJsonString());
        } else {
            HighScore currentHighScore = this.fileHandler.getJSON("highscore.json", HighScore.class);
            if (playerScore.getRep() == currentHighScore.getRep()) {
                if (playerScore.getTime() > currentHighScore.getTime()) {
                    //Save the highscore!
                    this.fileHandler.writeToFile("highscore.json", playerScore.toJsonString());
                } else if (playerScore.getTime() < currentHighScore.getTime()) {
                    this.dashboard.print("Sorry, the current highscore managed to get the same score, with a better time");
                    this.dashboard.print("Your score was: " + playerScore.getRep());
                } else if (playerScore.getTime() == playerScore.getTime()) { //Trolling, should possibly be removed.
                    this.dashboard.print("You managed to get exactly the same score and time, as the previous highscore player");
                    this.dashboard.print("As programmers we didnt think this was possible, therefor we have no other option, to declare Matias Marek as the ruler and all time HighScore champion");

                }

            } else if (playerScore.getRep() > currentHighScore.getRep()) {
                //Save high score!
                this.fileHandler.writeToFile("highscore.json", playerScore.toJsonString());
            } else {
                this.dashboard.print("Sorry, you didn't beat the highscore! Cunt");
                this.dashboard.print("Your score was: " + playerScore.getRep());
            }

        }
    }

    /**
     * Methods for incrementing the time ingame
     *
     * @param i the amount to increment the time with
     */
    public void incrementTime(int i) { // This method + to the time
        time += i;
    }

    /**
     * Method for decrementing the time ingame
     * @param i 
     */
    public void decrementTime(int i) { // This method  -  to the time
        time -= i;
    }

    /**
     * Purpose of this?
     * @return 
     */
    public int checkTimers() { // This method checks the times and returns it        
        return this.time;
    }
}
