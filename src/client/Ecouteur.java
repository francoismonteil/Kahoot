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
import metier.Question;
import metier.Reponse;

/**
 * Ecouteur : classe permettant d'écouter les messages en tant que processus parallèle 
 * @author francois.monteil
 */
public class Ecouteur extends Thread{
    
    private final Socket client;
    private ObjectInputStream  streamIn;
    private Question question;
    private final Client formClient;
    private Reponse rep1;
    private Reponse rep2;
    private Reponse rep3;
    private Reponse rep4;
    private Reponse repExacte;
    
    
    /**
     * Ecouteur : On définit les attribut de la classe
     * @param client
     * @param formClient
     */
    public Ecouteur(Socket client, Client formClient)
    {
            this.client = client;
            this.formClient = formClient;
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
                //On réceptionne la question du serveur Kahoot
                streamIn = new ObjectInputStream(new  BufferedInputStream(client.getInputStream()));
                question = (Question) this.streamIn.readObject();
                System.out.println("SERVEUR - CLIENT : "+question.getTexteQuestion());
                
                //On écrit le message reçu dans la zone de dialogue
                this.formClient.setQuestion("Kahoot : "+question.getTexteQuestion()+"\n");
                this.formClient.resetResultat();
                
                //On affiche les différentes réponse possible sur les boutons clickable
                if (question.getRepsPossibles() != null)
                {
                    String reponse[] = {question.getRepsPossibles().get(0).getTexteReponse(),
                        question.getRepsPossibles().get(1).getTexteReponse(),
                        question.getRepsPossibles().get(2).getTexteReponse(),
                        question.getRepsPossibles().get(3).getTexteReponse()};
                    
                    this.formClient.setButtons(reponse);
                    
                     this.rep1 = question.getRepsPossibles().get(0);
                     this.rep2 = question.getRepsPossibles().get(1);
                     this.rep3 = question.getRepsPossibles().get(2);
                     this.rep4 = question.getRepsPossibles().get(3);
                    
                }
                               
                //Réccupération du score
                streamIn = new ObjectInputStream(new  BufferedInputStream(client.getInputStream()));
                int score = (int) this.streamIn.readObject();
                this.formClient.setScore(score);
                
                //Réccupération de la réponse exacte
                this.formClient.setResultat(question.getRepExacte());
                
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
