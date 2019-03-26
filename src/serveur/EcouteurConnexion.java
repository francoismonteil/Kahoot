/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serveur;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import metier.Question;

/**
 *
 * @author alexandre
 */
public class EcouteurConnexion extends Thread{
    
    private JTextArea zoneAffichage;
    private ServerSocket socketEcoute = null;
    protected static List<Connexion> lesConnexionsClient = new ArrayList<Connexion>();
    protected int nbClients = 0;
   
    public EcouteurConnexion(JTextArea ta) throws IOException
    {
        System.out.println("Serveur - Ouverture d'un SocketServer sur le port 50000 ");
        this.zoneAffichage = ta;
        ta.append("["+System.currentTimeMillis()+"] Serveur - Ouverture d'un SocketServer sur le port 50000\n");
        socketEcoute = new ServerSocket(50000);
        System.out.println("Serveur démarré : " + socketEcoute);
        ta.append("["+System.currentTimeMillis()+"] Serveur démarré : \n");
        ta.append("["+System.currentTimeMillis()+"] "+socketEcoute + "\n");
        
    }
    
     @Override
    public void run() {
        Socket uneSocketClient;
        Connexion c;

         try {
                while (!Thread.currentThread().isInterrupted())
                {
                    System.out.println("En attente d'un client ");
                    uneSocketClient = socketEcoute.accept();
                    zoneAffichage.append("\n["+System.currentTimeMillis()+"] Joueur connecté ! " + uneSocketClient);
                    ajoutConnexion(uneSocketClient);
                    
                }            
            } catch (IOException ex) {
                //Logger.getLogger(Serveur.class.getName()).log(Level.SEVERE, null, ex);
                //Si tu gère le code erreur autant enlever le message d'exception qui vient polluer la console
                Thread.currentThread().interrupt();
            
            }
            finally {
                try {
                    System.out.println("Thread serveur interrompu");
                    socketEcoute.close();
                    Thread.currentThread().interrupt();
                } catch (IOException ex) {
                    Logger.getLogger(Serveur.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
       
        System.out.println("Thread serveur interrompu");
    }
     private void ajoutConnexion(Socket uneSocket)
    {
        nbClients++;
        System.out.println("Client accepté " + uneSocket + " Il y a désormais " + nbClients + " clients connectés");
        zoneAffichage.append("\n["+System.currentTimeMillis()+"] Il y a désormais " + nbClients + " clients connectés");
        Connexion c = new Connexion(uneSocket,this);
        //c.open();
        c.start();
        synchronized(lesConnexionsClient){
        lesConnexionsClient.add(c);
        }
        etatConnexions();
    }
     
    public synchronized void handle(int ID,String msg)
    {
        Connexion c;
        if (msg.equals("SALUT"))
        {
           c = findConnexion(ID);
           c.send("SALUT");
           remove(ID);
        }
        else
        {
            lesConnexionsClient.forEach((cc) -> {
                cc.send(ID + " : " + msg);
            });
        }
    }
    public Connexion findConnexion(int ID)
    {
        Connexion uneConnexion = null;
        for (Connexion c : lesConnexionsClient)
        {
            if (c.getID() == ID)
            {
                uneConnexion=c;
            }
        }
        return uneConnexion;
    }
   
    
    @Override
    public void interrupt() {
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }
   
     
    public synchronized void remove(int ID)
    {      
        Connexion uneConnexion = findConnexion(ID);
        if (uneConnexion != null)
        {
            lesConnexionsClient.remove(uneConnexion);
            nbClients--;
        }
        try 
        {
            System.out.println("Fermeture du thread d'écoute client " + ID);
            uneConnexion.close();
            uneConnexion.interrupt();
        } catch (IOException ex) 
        {
            Logger.getLogger(Serveur.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Serveur.remove : Il reste " + nbClients + " clients conectés");
        etatConnexions();

   }
    public List<Connexion> getConnexions()
    {
        synchronized(lesConnexionsClient) {
            return lesConnexionsClient;
        }
    }
    public void etatConnexions()
    {
        System.out.println("Clients connectés : ");
        lesConnexionsClient.forEach((c) -> {
            System.out.println("Connexion : "+ c.getName() + " - " + c.getID() + " Status : " + c.getState());
        });
    }
    
    public void envoieQuestion()
    {
        Question q = new Question();
        for (Connexion c : lesConnexionsClient)
        {
            c.send(q);
        }        
    }
    public static void main(String[] args) /*throws InterruptedException*/ {
        
        EcouteurConnexion monThreadServeur;
        /*try {
            monThreadServeur = new EcouteurConnexion();
            monThreadServeur.start();
            
        } catch (IOException ex) 
        {
            Logger.getLogger(Serveur.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
                
    }
    
}
