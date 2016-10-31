/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuul;

import java.util.UUID;

/**
 *
 * @author DanielToft
 */
public class Planet {
    private String _name;
    private String _description;
    private int _x;
    private int _y;
    private UUID _UUID;
    
    public Planet(String name, String description, int x, int y, UUID UUID) {
        this._name = name;
        this._description = description;
        this._x = x;
        this._y = y;
        this._UUID = UUID;
    }
    
    public String getName() {
        return this._name;
    }
    
    public String getDescription() {
        return this._description;
    }
    
    public int getXCoor() {
        return this._x;
    }
    
    public int getYCoor() {
        return this._y;
    }
    
    public UUID getId() {
        return this._UUID;
    }
}
