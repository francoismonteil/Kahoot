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
import javax.swing.JButton;
import javax.swing.JTextArea;
import metier.Question;
import metier.Reponse;

/**
 * Ecouteur : classe permettant d'écouter les messages en tant que processus parallèle 
 * @author francois.monteil
 */
public class Ecouteur extends Thread{
    
    private Socket client;
    private JTextArea dialog;
    private ObjectInputStream  streamIn;
    private Question question;
    private JButton button_rep1;
    private JButton button_rep2;
    private JButton button_rep3;
    private JButton button_rep4;
    private Reponse rep1;
    private Reponse rep2;
    private Reponse rep3;
    private Reponse rep4;
    
    
    /**
     * Ecouteur : On définit les attribut de la classe
     * @param client
     * @param dialog 
     * @param rep1 
     * @param rep2 
     * @param rep3 
     * @param rep4 
     */
    public Ecouteur(Socket client, JTextArea dialog, JButton rep1, JButton rep2, JButton rep3, JButton rep4)
    {
            this.client = client;
            this.dialog = dialog;
            dialog.append("Client : Connexion au jeu Kahoot\n");
            this.button_rep1 = rep1;
            this.button_rep2 = rep2;
            this.button_rep3 = rep3;
            this.button_rep4 = rep4;
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
          
            try {
                streamIn = new ObjectInputStream(new  BufferedInputStream(client.getInputStream()));
                question = (Question) this.streamIn.readObject();
                System.out.println("SERVEUR - CLIENT : "+question.getTexteQuestion());
                //On écrit le message reçu dans la zone de dialogue
                dialog.append("Kahoot : "+question.getTexteQuestion()+"\n");
                
                if (question.getRepsPossibles() != null)
                {
                    if (question.getRepsPossibles().get(0) != null)
                    {
                        this.button_rep1.setEnabled(true);
                        this.button_rep1.setText(question.getRepsPossibles().get(0).getTexteReponse());
                        this.rep1 = question.getRepsPossibles().get(0);
                    }
                    else
                        this.button_rep1.setEnabled(false);
                    if (question.getRepsPossibles().get(1) != null)
                    {
                        this.button_rep2.setEnabled(true);
                        this.button_rep2.setText(question.getRepsPossibles().get(1).getTexteReponse());
                        this.rep2 = question.getRepsPossibles().get(1);
                    }
                    else
                        this.button_rep2.setEnabled(false);
                    if (question.getRepsPossibles().get(2) != null)
                    {
                        this.button_rep3.setEnabled(true);
                        this.button_rep3.setText(question.getRepsPossibles().get(2).getTexteReponse());
                        this.rep3 = question.getRepsPossibles().get(2);
                    }
                    else
                        this.button_rep3.setEnabled(false);
                    if (question.getRepsPossibles().get(3) != null)
                    {
                        this.button_rep4.setEnabled(true);
                        this.button_rep4.setText(question.getRepsPossibles().get(3).getTexteReponse());
                        this.rep4 = question.getRepsPossibles().get(3);
                    }
                    else
                        this.button_rep4.setEnabled(false);
                }
               
                
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
    
    public Reponse getRep1()
    {
        return rep1;
    }
    
    public Reponse getRep2()
    {
        return rep2;
    }
        
    public Reponse getRep3()
    {
        return rep3;
    }
            
    public Reponse getRep4()
    {
        return rep4;
    }
    
    
}
