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
/**
 *
 * @author isabelle
 */
public class Connexion extends Thread{
    
    private final Socket socketService;
    private final EcouteurConnexion monServeur;
    private int ID  = -1; 
    private ObjectInputStream  streamIn  =  null;
    private ObjectOutputStream streamOut = null;
    
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
        streamIn = new ObjectInputStream(new 
                        BufferedInputStream(socketService.getInputStream()));
        
    }
    public void close() throws IOException
    {
        if (socketService != null) socketService.close();
        if (streamIn != null) streamIn.close();
        if (streamOut != null) streamOut.close();
    }
    
    /*public synchronized void envoyerMessage(String msg)
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
     
    }*/
    @Override
    public void run() {
        
        while (this.isInterrupted() == false)
        {
            try {
                streamIn = new ObjectInputStream(new BufferedInputStream(socketService.getInputStream()));
            } catch (IOException ex) {
                Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
            }
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
