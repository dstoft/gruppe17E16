/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuul;

/**
 * An interface that makes sure that classes that needs to have their names 
 * and/or description printed, is capable of doing so. Used by the GUI.
 * @author DanielToft
 */
public interface PrintAble {
    
    public abstract String getName();
    
    public abstract String getDescription();
    
}
