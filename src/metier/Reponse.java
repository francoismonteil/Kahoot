/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

import java.io.Serializable;

/**
 *
 * @author Black
 */
public class Reponse implements Serializable{
    private int noQuestion;
    private String texteQuestion;
    private int idQuestion;
    
    public Reponse(int id, String texte, int idQuestion)
    {
        this.noQuestion = id;
        this.texteQuestion = texte;
        this.idQuestion = idQuestion;
    }
}
