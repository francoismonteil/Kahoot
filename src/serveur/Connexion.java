/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serveur;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author isabelle
 */
public class Connexion extends Thread{
    
    
    private Socket socketService;
    private EcouteurConnexion monServeur;
    private int ID  = -1; 
    private DataInputStream  streamIn  =  null;
    private DataOutputStream streamOut = null;
    
    public Connexion(Socket laSocket,EcouteurConnexion leServeur)
    {
        super();
        socketService= laSocket;
        monServeur = leServeur;
        ID = socketService.getPort();
        
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
        
        while (!Thread.currentThread().isInterrupted())
        {
            try 
            {
                envoyerMessage(streamIn.readUTF());
            
            } catch (IOException ex) 
            {
                Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
                monServeur.remove(ID);
                //interrupt();
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
