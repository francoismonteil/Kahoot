/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serveur;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author isabelle
 */
public class Connexion extends Thread{
    
    private final Socket socketService;
    private final EcouteurConnexion monServeur;
    private int ID  = -1; 
    private DataInputStream  streamIn  =  null;
    private DataOutputStream streamOut = null;
    
    public Connexion(Socket laSocket,EcouteurConnexion leServeur)
    {
        super();
        socketService= laSocket;
        monServeur = leServeur;
        ID = socketService.getPort();
        try {
            streamIn = new DataInputStream(new BufferedInputStream(socketService.getInputStream()));
        } catch (IOException ex) {
            System.out.println("Connexion : Problème d'instanciation du streamIn");
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void send(String msg)
    {
        try
        {
            streamOut.writeUTF(msg);
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
        streamIn = new DataInputStream(new 
                        BufferedInputStream(socketService.getInputStream()));
        streamOut = new DataOutputStream(new
                        BufferedOutputStream(socketService.getOutputStream()));
    }
    public void close() throws IOException
    {
        if (socketService != null) socketService.close();
        if (streamIn != null) streamIn.close();
        if (streamOut != null) streamOut.close();
    }
    
    public synchronized void envoyerMessage(String msg)
    {
        Connexion c;
        c = monServeur.findConnexion(ID);
        for (Connexion cc : monServeur.getConnexions())
        {
            
           cc.send(ID + " : " + msg);
            
        }
        if (msg.equals("SALUT"))
        {
            c.send(ID + " : "+ msg);
            monServeur.remove(ID);
        }
     
    }
    @Override
    public void run() {
        
        while (this.isInterrupted() == false)
        {
            String line;
            try {
                while((line=this.streamIn.readUTF()) != null)
                {
                    System.out.println("Connexion : "+line);
                }
            } catch (IOException ex) {
               try {
                   this.streamIn.close();
               } catch (IOException ex1) {
                   //Logger.getLogger(Ecouteur.class.getName()).log(Level.SEVERE, null, ex1);
               }
            }
        }
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
    
}
