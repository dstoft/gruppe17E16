/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <mail@emilharder.dk> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Emil Harder
 * ----------------------------------------------------------------------------
 */
package worldofzuul;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class is ment for testing of the Inventory and Items classes in the
 * worldofzuul package.
 *
 * @author emil
 */
public class InvTest {

    private static int getChoose;
    boolean running = true;

    Inventory inventory = new Inventory();

    public void invTest() throws IOException, Exception {
        
        inventory.addItem(5, "Tung ting", 2, 3);
        inventory.addItem(2, "Knap så tung", 4, 1);

        while (running) {

            int choosed = getChoose();
            System.out.println("Du har valgt " + choosed);
            invSwitch(choosed);

        }

    }

    private int getChoose() {

        System.out.println("Test af inventory(). Tast:");
        System.out.println("1 for Show inventory");
        System.out.println("2 for add item");
        System.out.println("3 for remove item");
        System.out.println("0 to quit");

        Scanner sc = new Scanner(System.in); // Constructing new scanner object
        int choosed = sc.nextInt(); // Var for choosed action

        return choosed;
    }

    public void invSwitch(int x) throws IOException, Exception {

        switch (x) {
            case 1:
                invShower();
                break;
            case 2:
                itemAdder();
                break;
            case 3:
                itemRemover();
                break;
            case 0:
                running = false;
                break;
            default:
                break;
        }
    }

    private void invShower() throws IOException {
        
        Scanner sc = new Scanner(System.in); // Constructing new scanner object
        
        System.out.println("Default or choose item to show (int)");
        
        int i = sc.nextInt();
        
        System.out.println("What information(s) do you want?");
        
        char c = (char) System.in.read();
        
        System.out.println(inventory.showInventory(i, c));
    }
    
    public void itemAdder() throws Exception {

        Scanner sc = new Scanner(System.in); // Constructing new scanner object

        System.out.println("Set parameters for new item: ");
        System.out.println("    The description (string): ");
        String destript = sc.nextLine(); // Var for description
        System.out.println("    The weight of the item (int): ");
        int weight = sc.nextInt(); // Var for weight
        System.out.println("    The x coordinate for destination (int): ");
        int xCoor = sc.nextInt(); // Var for xCoor
        System.out.println("    The y coordinate for destination (int): ");
        int yCoor = sc.nextInt(); // Var for yCoor

        System.out.println(inventory.addItem(weight, destript, xCoor, yCoor));
        
    }

    private void itemRemover() {
        System.out.println("Give unique ID number for item to be removed. ");

        Scanner sc = new Scanner(System.in); // Constructing new scanner object
        String uniqueID = sc.nextLine(); // Var for choosed action

        inventory.remItem(uniqueID);
    }

}
