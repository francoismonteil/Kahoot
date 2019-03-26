/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

public class Ecouteur extends Thread{
    
    private BufferedReader buffer;
    private InputStreamReader input;
    private Socket client;
    private JTextArea dialog;
    
    public Ecouteur(Socket client, JTextArea dialog)
    {
        try {
            this.client = client;
            input = new InputStreamReader(client.getInputStream());
            buffer = new BufferedReader(input);
            this.dialog = dialog;
        } catch (IOException ex) {
            Logger.getLogger(Ecouteur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run()
    {
        while (this.isInterrupted() == false)
        {
           String line;
           dialog.append("Client : Connexion au jeu Kahoot");
            try {
                while((line=buffer.readLine()) != null)
                {
                    System.out.println("SERVEUR - CLIENT : "+line);
                    dialog.append("<b>Kahoot : </b>"+line+"\n");
                }
            } catch (IOException ex) {
               try {
                   this.buffer.close();
               } catch (IOException ex1) {
                   Logger.getLogger(Ecouteur.class.getName()).log(Level.SEVERE, null, ex1);
               }
            }
        }
    }
    
    
    
}
