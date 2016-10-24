/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuul;

/**
 *
 * @author MatiasMarek
 */
public class Dashboard extends MovementCalculator {
    //Defines variables
    private Player _player ;  
    private Inventory  _inventory _; 
    
    
    public Dashboard(Player player) {
        this._player = player ; 
        this._inventory = player.getInventory(); 
      // skal jeg her indsætte this._currentPlanet.getY(); og getX () ? hvis jeg skal lave print player position.  this._currentPlanet.getY(); = this._player.getY()          ??? 
    
    }
    public void printAllPlanets( ) { // Dette skulle gerne være print possible planets  ?  jeg har ændret navnet printAllPlanets til printPossiblePlanets
    ArrayList<Planet> possiblePlanets = Planet.planets
        for(Planet planet : possiblePlanets) {
            
            System.out.println(planet.getName());
        }
        
       
            
        }
    // her printer jeg player stat, jeg forestiller mig her at jeg bare skal printe, da der ikke er nogen liste, og vi har nogle ***Getter*** der henter disse oplysninger, det er int som datatype
 public void printPlayerStats() {
     System.out.println("this your remaining fuel level" + player.getFuel());

     System.out.println(" This is your current Reputation " + player.getReputation());
          
   
    
    
    
    
}
 // her printer jeg navnet på den nuværende planet, samt det koordinat sæt planeten har. 
 public void printPlayerPosition () { // vi har allerede en metode fra Player der fortæller os current (x,y). Så forestiller mig at vi udelukkende skal printe d, print alt.  
     System.out.println("This is your current position"+ this._currentPlanet.getX(); +  this._currentPlanet.getY(););  
     
     
     
 }
 // Her vil jeg printe ALLE planeters navne, vi har i spillet ! Forestiller mig at vi har en Arraylist med planet, for hver planet giver jeg dens navn til allPlanets, og printer navnet ud på planetens navn
 public void printAllPlanets() { // if statement 
     ArrayList<Planet>  allPlanets = Planet.planet ; // kan jeg godt kalde den Planet.planet.
             for ( Planet planet : allPlanets) {
                 System.out.println(planet.getName());
             }
 } 
 // forestiller mig at der er en arrayliste over Inventory, her vil jeg gerne printe hvad der er
 public void printInventory () {
     ArrayList<Inventory> myInventory = Inventory.inventory 
             for ( Inventory inventory : myInventory) {
                 System.out.println(inventory,getName()); // hvad er metoden til at finde navnet på de ting vi har i inventory 
                
             }
                 
 }
}