/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serveur;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import metier.Reponse;
/**
 *
 * @author isabelle
 */
public class Connexion extends Thread{
    
    private final Socket socketService;
    private Reponse reponse;
    private final EcouteurConnexion monServeur;
    private int ID  = -1; 
    private ObjectInputStream  streamIn  =  null;
    private ObjectOutputStream streamOut = null;
    private String pseudo;
    private int score = 0;
    
    public Connexion(Socket laSocket,EcouteurConnexion leServeur)
    {
        super();
        socketService= laSocket;
        monServeur = leServeur;
        ID = socketService.getPort();
    }
    public void send(Object msg)
    {
        try
        {
            streamOut = new ObjectOutputStream(new BufferedOutputStream(socketService.getOutputStream()));
            streamOut.writeObject(msg);
            streamOut.flush();
        }
        catch (IOException ex)
        {
            System.out.println(ID + "Erreur à l'envoi du message - " +ex.getMessage());
            monServeur.remove(ID);
            interrupt();
        }
    }
   
    public void open() throws IOException
    {  
        streamIn = new ObjectInputStream(new BufferedInputStream(socketService.getInputStream()));
    }
     
    public void close() throws IOException
    {
        if (socketService != null) socketService.close();
        if (streamIn != null) streamIn.close();
        if (streamOut != null) streamOut.close();
    }
    
    @Override
    public void run() {
        try {
            ObjectInputStream streamPseudo = new ObjectInputStream(new BufferedInputStream(socketService.getInputStream()));
            pseudo = streamPseudo.readObject().toString();
            monServeur.etatConnexions();
        } catch (IOException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (this.isInterrupted() == false)
        {
            try {
                streamIn = new ObjectInputStream(new BufferedInputStream(socketService.getInputStream()));
                try {
                    reponse = (Reponse) this.streamIn.readObject();
                    monServeur.recupReponse(reponse, this);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (IOException ex) {
                //On retire le client de la liste des connexions
                this.monServeur.remove(this.ID);
                //Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPseudo() {
        return pseudo;
    }
    
    public void augmenterScore(){
        score++;
    }
    
    public void envoyer_score()
    {
        this.send(score);
        System.out.println("Envoi du score : "+score);
    }
}
