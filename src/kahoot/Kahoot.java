/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kahoot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import metier.Json2Question;
import metier.Question;

/**
 *
 * @author Black
 */
public class Kahoot {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Json2Question json = new Json2Question();
        
        try {
            ArrayList<Question> listeQuestion = json.getQuestion(9);
            System.out.println(listeQuestion);
        } catch (IOException ex) {
            Logger.getLogger(Kahoot.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("STOP");
    }
    
}
