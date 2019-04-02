/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Black
 */
public class Question implements Serializable{
    private int noQuestion;
    public String texteQuestion;
    private List<Reponse> repsPossibles;
    private Reponse repExacte;
    private List<Reponse> repUtilisateur;
    
    
    
    public Question()
    {
        this.noQuestion = 1;
        this.texteQuestion = "Quel est le vrai nom de M.Bean ?";
        this.repsPossibles = new ArrayList<>();
        this.repUtilisateur = new ArrayList<>();
        
        Reponse rep1 = new Reponse(1, "Rowan Atkinson", this.noQuestion);
        this.repsPossibles.add(rep1);
        Reponse rep2 = new Reponse(2, "John McCain", this.noQuestion);
        this.repsPossibles.add(rep2);
        Reponse rep3 = new Reponse(3, "Ronald McDonald", this.noQuestion);
        this.repsPossibles.add(rep3);
        Reponse rep4 = new Reponse(4, "Colonel Sanders", this.noQuestion);
        this.repsPossibles.add(rep4);
        this.repExacte = rep1;
    }

    Question(String question, String rep1T, String rep2T, String rep3T, String rep4T, String repExacteT) {
        this.texteQuestion = question;
        this.repsPossibles = new ArrayList<>();
        this.repUtilisateur = new ArrayList<>();
        
        Reponse rep1 = new Reponse(1, rep1T);
        this.repsPossibles.add(rep1);
        Reponse rep2 = new Reponse(2, rep2T);
        this.repsPossibles.add(rep2);
        Reponse rep3 = new Reponse(3, rep3T);
        this.repsPossibles.add(rep3);
        Reponse rep4 = new Reponse(4, rep4T);
        this.repsPossibles.add(rep4);
        Reponse repExacte = new Reponse(5, repExacteT);
        this.repExacte = repExacte;
        
    }
    
    public int getNoQuestion() {
        return noQuestion;
    }

    public void setNoQuestion(int noQuestion) {
        this.noQuestion = noQuestion;
    }

    public String getTexteQuestion() {
        return texteQuestion;
    }

    public void setTexteQuestion(String texteQuestion) {
        this.texteQuestion = texteQuestion;
    }

    public List<Reponse> getRepsPossibles() {
        return repsPossibles;
    }

    public void setRepsPossibles(List<Reponse> repsPossibles) {
        this.repsPossibles = repsPossibles;
    }

    public Reponse getRepExacte() {
        return repExacte;
    }

    public void setRepExacte(Reponse repExacte) {
        this.repExacte = repExacte;
    }

    public List<Reponse> getRepUtilisateur() {
        return repUtilisateur;
    }

    public void setRepUtilisateur(List<Reponse> repUtilisateur) {
        this.repUtilisateur = repUtilisateur;
    }
    
}
