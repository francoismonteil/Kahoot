/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 * Ecouteur : classe permettant d'écouter les messages en tant que processus parallèle 
 * @author francois.monteil
 */
public class Ecouteur extends Thread{
    
    private Socket client;
    private JTextArea dialog;
    private DataInputStream  streamIn;
    
    /**
     * Ecouteur : On définit les attribut de la classe
     * @param client
     * @param dialog 
     */
    public Ecouteur(Socket client, JTextArea dialog)
    {
        try {
            this.client = client;
            streamIn = new DataInputStream(new BufferedInputStream(client.getInputStream()));
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
           String line;
           dialog.append("Client : Connexion au jeu Kahoot\n");
            try {
                while((line=this.streamIn.readUTF()) != null)
                {
                    System.out.println("SERVEUR - CLIENT : "+line);
                    //On écrit le message reçu dans la zone de dialogue
                    dialog.append("Kahoot : "+line+"\n");
                }
            } catch (IOException ex) {
               try {
                   this.streamIn.close();
               } catch (IOException ex1) {
                   Logger.getLogger(Ecouteur.class.getName()).log(Level.SEVERE, null, ex1);
               }
            }
        }
    }
    
    
    
}
