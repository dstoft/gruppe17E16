/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuul;

import java.util.ArrayList;

/**
 *
 * @author DanielToft
 */
public class MovementCalculator {
    
    public int calculateDistance(int startX, int startY, int toX, int toY) {
        int distance = 0;
        distance = (int)Math.sqrt(
            Math.pow(Math.abs(startX-toX),2) 
            + Math.pow(Math.abs(startY-toY), 2)
        );
        return distance;
    }
    
    public boolean isReachable(int startX, int startY, int toX, int toY, int currentFuel) {
        if(currentFuel >= this.calculateDistance(startX, startY, toX, toY)) {
            return true;
        } else {
            return false;
        }
    }
    
    public ArrayList<Planet> getPossiblePlanets(int startX, int startY, int currentFuel) {
        ArrayList<Planet> reachablePlanets = new ArrayList<>();
        for(Planet planet : Planet.allPlanets) {
            if(this.isReachable(startX, startY, planet.getX(), planet.getY(), currentFuel)) {
                reachablePlanets.add(planet);
            }
        }
        return reachablePlanets;
    }
    
    
    //calculateFuelUsage
}
