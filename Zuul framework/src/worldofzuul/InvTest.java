/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuul;

import java.util.Scanner;

/**
 *
 * @author emil
 */
public class InvTest {

    private static int getChoose;

    public void invTest() {

        int choosed = getChoose();
        System.out.println("Du har valgt "+choosed);
        invSwitch(choosed);

    }

    public void itemAdder() {

        Scanner sc = new Scanner(System.in); // Constructing new scanner object

        System.out.println("Set parameters for new item: ");
        System.out.println("    The weight of the item (int): ");
        int weight = sc.nextInt(); // Var for weight
        System.out.println("    The name (string): ");
        String name = sc.next(); // Var for description
        System.out.println("    The description (string): ");
        String destript = sc.next(); // Var for description
        System.out.println("    Are there papers with the item (boolean)? ");
        boolean papers = sc.nextBoolean();// Var for wether there are papers or not

        Inventory inventory = new Inventory();

        inventory.addItem(weight, name, destript, papers);
    }

    private int getChoose() {

        System.out.println("Test af inventory(). Tast:");
        System.out.println("1 for Show inventory");
        System.out.println("2 for add item");
        System.out.println("3 for remove item");

        Scanner sc = new Scanner(System.in); // Constructing new scanner object
        int choosed = sc.nextInt(); // Var for choosed action

        return choosed;
    }

    public void invSwitch(int x) {

        switch (x) {
            case 1:
                invShower();
                break;
            case 2:
                itemAdder();
                break;
            case 3:
                System.out.println("3");
                break;
            default:
                break;
        }
    }

    private void invShower() {
        

        //String showInventory = inventory.showInventory();

        System.out.println("to be added");
    }

}