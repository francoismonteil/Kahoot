/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kahoot;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static metier.Json2Question.generateQuestion;

/**
 *
 * @author Black
 */
public class Kahoot {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here

            generateQuestion();
        } catch (IOException ex) {
            Logger.getLogger(Kahoot.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("STOP");
    }
    
}
