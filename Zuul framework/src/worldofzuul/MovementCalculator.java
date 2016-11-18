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
    
    public ArrayList<Planet> getPossiblePlanets(int startX, int startY, int currentFuel, ArrayList<Planet> allPlanets) {
        ArrayList<Planet> reachablePlanets = new ArrayList<>();
        for(Planet planet : allPlanets) {
            if(this.isReachable(startX, startY, planet.getXCoor(), planet.getYCoor(), currentFuel)) {
                reachablePlanets.add(planet);
            }
        }
        return reachablePlanets;
    }
    
    public boolean isWarpReachable(int startX, int startY, int toX, int toY, int currentFuel) {
        if(currentFuel >= (this.calculateDistance(startX, startY, toX, toY)/10)) {
            return true;
        } else {
            return false;
        }
    }
    
    public int calculateWarpFuelUsage(int startX, int startY, int toX, int toY) {
        return this.calculateDistance(startX, startY, toX, toY);
    }
}
