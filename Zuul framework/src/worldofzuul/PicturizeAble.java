/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuul;

/**
 * Ensures that the classes that have an image, knows a source path to this image.
 * @author DanielToft
 */
public interface PicturizeAble {
    
    public abstract String getImagePath();
}
