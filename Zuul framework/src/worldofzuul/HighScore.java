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
public class HighScore {
//    //Attributter 
    private String name;   // the name from the player
    private int  rep;   //the reputation from this current game, when the game is over
    private int time; // the time taken to for the player to finish the game

    
    
    
    //Constructor 
    public HighScore() {
        
    }
    public HighScore(int rep, int time, String name){
        this.name = name; 
        this.rep = rep;
        this.time = time;   
    }
    
    //Method
    @Override
    public String toString(){
        String stringTime = "" + this.time;
        String stringRep  = "" + this.rep;
        String theHighScore = this.name + ":" + stringRep +":" + stringTime;
        return theHighScore;
        
    }
    
    public String toJsonString(){
        String jSonString = "{\"name\":\"" + this.name + "\",\"rep\":" + this.rep + ",\"time\"" + this.time +"}";
        return jSonString;
        }
    
    //Getters
    public int getRep(){
        return this.rep; 
    }
    public int getTime(){
        return this.time;
    }
    
    public String getName(){
        return this.name;
    }
    
               
        
    
    
    

   
    
}
