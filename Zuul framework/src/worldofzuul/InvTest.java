/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuul;

//import java.util.Scanner;

import java.util.Scanner;


/**
 *
 * @author emil
 */
public class InvTest {
    
    
    
    public String invSwitch(int x){
            String choosedAction;
            switch (x) {
                case 1: choosedAction = showInv();
                    return "Du trykkede 1";
                case 2: choosedAction = "Du trykkede 2";
                    return "Du trykkede 2";
                case 3: choosedAction = "Du trykkede 3";
                    return "Du trykkede 3";
                case 4: choosedAction = "Du trykkede 4";
                    return "Du trykkede 4";
                default: choosedAction = "Fejl! Tast 1, 2, 3 eller 4.";
                    return "Fejl! Tast 1, 2, 3 eller 4.";
                
            }
    }
        
    /**
     *
     * 
     */
    public void invTest(){
        
        int choosed = getChoose();
        System.out.println("Du har valgt: "+choosed);
        boolean running = true;
        while (running){
            invSwitch(choosed);
            System.out.println(invSwitch(choosed));
        }
        
    }

    /**
     *
     */
    private int getChoose() {

        System.out.println("Test af inventory(). Tast:");
        System.out.println("1 for Show inventory");
        System.out.println("2 for add item");
        System.out.println("3 for remove item");
        System.out.println("4 for Noget andet.");
        
        Scanner sc = new Scanner(System.in); // Constructing new scanner object
        int choosed = sc.nextInt(); // Var for choosed action
        
        return choosed;
        }
        
}