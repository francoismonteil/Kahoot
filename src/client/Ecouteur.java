/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.BufferedInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import metier.Question;

/**
 * Ecouteur : classe permettant d'écouter les messages en tant que processus parallèle 
 * @author francois.monteil
 */
public class Ecouteur extends Thread{
    
    private Socket client;
    private JTextArea dialog;
    private ObjectInputStream  streamIn;
    private Question question;
    
    /**
     * Ecouteur : On définit les attribut de la classe
     * @param client
     * @param dialog 
     */
    public Ecouteur(Socket client, JTextArea dialog)
    {
        try {
            this.client = client;
            streamIn = new ObjectInputStream(new BufferedInputStream(client.getInputStream()));
            this.dialog = dialog;
        } catch (IOException ex) {
            Logger.getLogger(Ecouteur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * run : On démarre l'écoute permanente.
     */
    @Override
    public void run()
    {
        //Tant que le processus n'a pas été intérrompu : on écoute
        while (this.isInterrupted() == false)
        {
           
            dialog.append("Client : Connexion au jeu Kahoot\n");
            try {
                question = (Question) this.streamIn.readObject();
                System.out.println("SERVEUR - CLIENT : "+question.getTexteQuestion());
                //On écrit le message reçu dans la zone de dialogue
                dialog.append("Kahoot : "+question.getTexteQuestion()+"\n");
                
            } catch (IOException ex) {
               try {
                   this.streamIn.close();
               } catch (IOException ex1) {
                   Logger.getLogger(Ecouteur.class.getName()).log(Level.SEVERE, null, ex1);
               }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Ecouteur.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    
}
