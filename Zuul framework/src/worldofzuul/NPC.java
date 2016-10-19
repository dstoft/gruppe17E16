package worldofzuul;

import java.util.Scanner;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NPC { 

    //Initializing variables
    private int NPCid;      //Unique id for each NPC
    private char input;     //Input from user in the form of y/n
    private String output;  //Output from NPC to user
    private String line = null; //Holds the line, the NPC will say, right after it has been read
                                //by the getOutput() method
    
    private Scanner reader = new Scanner(System.in);    //Create new Scanner object

    //Constructor
    public NPC (int NPCid) {   
        this.NPCid = NPCid;
	}

    /** This method reads a specific line from a text file.
    * It then returns that line as a String object.
    * husk at kommentere på det der exception...
    */
    private String getOutput(int i) {
        try {
            line = Files.readAllLines(Paths.get("NPC" + NPCid + ".txt")).get(i);
        } catch (IOException ex) {
            Logger.getLogger(NPC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return line;
    }

    private char getInput() {
        do {
            System.out.println("Type 'y' for yes or 'n' for no");           
            input = Character.toLowerCase(reader.next().charAt(0));
        }
        while (input != 'y' && input != 'n');

        return input;
    }

    /** This method starts the chat sequence in the y/n form.
    * NPCs get categorized into three groups by their id based
    * on how much interaction they have. Meaning that a NPC with
    * a low id-value will only say one thing and not take input,
    * while a NPC in the highest id-value group will say 4 things
    * and take 3 inputs from the user inbetween each.
    * Find ud af om den skal sout selv, eller return strengen der skal outputtes...
    */
    public String chatYN() {
    
        //If id-value is less than 5, NPC will only say one thing, and
        //not take input from user    
    	if (NPCid < 5) {
            output = getOutput(0);
            System.out.println(output);       

    	}

        //Here NPC will output one line, then take an input from user
        //and based on that input, it will output one of two options
    	else if (NPCid > 4 && NPCid < 8 ) {
            output = getOutput(0);
            System.out.println(output);
            
            input = Character.toLowerCase(reader.next().charAt(0));
                
            if (input == 'y') {
                output = getOutput(1);
            }
            else if (input == 'n') {
                output = getOutput(2);
            }
            System.out.println(output);       
            return line;

    	}

        //Here NPC will output one line, then take an input from user
        //and based on that input, it will output one of two options
        //+ a new question and then take another input from user, and
        //lastly output one of two options based on that input
    	else if (NPCid > 7){
            output = getOutput(0);
            System.out.println(output);

            input = getInput();

            if (input == 'y') {
                output = getOutput(1);
                System.out.println(output);

                output = getOutput(4);
                System.out.println(output);

                input = getInput();

                if (input == 'y') {
                    output = getOutput(5);
                }
                else if (input == 'n') {
                    output = getOutput(6);
                }

            }

            else if (input == 'n') {
                output = getOutput(2);
                System.out.println(output);

                output = getOutput(8);
                System.out.println(output);

                input = getInput();

                if (input == 'y') {
                    output = getOutput(9);
                }
                else if (input == 'n') {
                    output = getOutput(10);
                }

            }
            System.out.println(output);
        }
        
        return "hey";
    }
}

























