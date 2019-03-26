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
    private Reponse resExacte;
    private Reponse repUtilisateur;
    
    public Question()
    {
        this.noQuestion = 1;
        this.texteQuestion = "Quel est le vrai nom de M.Bean ?";
        this.repsPossibles = new ArrayList<>();
        
        Reponse rep1 = new Reponse(1, "Rowan Atkinson", this.noQuestion);
        this.repsPossibles.add(rep1);
        Reponse rep2 = new Reponse(2, "John McCain", this.noQuestion);
        this.repsPossibles.add(rep2);
        this.resExacte = rep1;
    }
}
