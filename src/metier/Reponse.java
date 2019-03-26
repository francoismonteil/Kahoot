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
    private String texteReponse;
    private int idReponse;
    
    public Reponse(int id, String texte, int idQuestion)
    {
        this.noQuestion = id;
        this.texteReponse = texte;
        this.idReponse = idQuestion;
    }

    public int getNoQuestion() {
        return noQuestion;
    }

    public void setNoQuestion(int noQuestion) {
        this.noQuestion = noQuestion;
    }

    public String getTexteReponse() {
        return texteReponse;
    }

    public void setTexteReponse(String texteReponse) {
        this.texteReponse = texteReponse;
    }

    public int getIdReponse() {
        return idReponse;
    }

    public void setIdReponse(int idReponse) {
        this.idReponse = idReponse;
    }

    
    
}
