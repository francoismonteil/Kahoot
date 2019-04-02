/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author francois.monteil
 */
public class Json2Question {
    
   
    public Question getQuestionFromTexte(String Texte)
    {
        String[] st = Texte.split(";");
        Question QR = new Question(st[0],st[1],st[2],st[3],st[4],st[5]);
        
        return QR;
    }
    
    public ArrayList<String> getQuestionFromFile(String url) throws FileNotFoundException, IOException
    {
        File file = new File(url);
        try (BufferedReader br = new BufferedReader(new FileReader(file)))
        {
            String line;
            ArrayList<String> mesLignes = new ArrayList<>();
            while ((line = br.readLine()) != null)
            {
                mesLignes.add(line);
            }
            return mesLignes;
        }
    }
    
    public ArrayList<Question> getQuestion(Integer nbQuestion) throws IOException
    {
        ArrayList<String> questionFromFile = this.getQuestionFromFile("D:\\Dossiers\\Documents\\NetBeansProjects\\ProjetKahoot\\Question.txt");
        ArrayList<Question> listeQuestion = new ArrayList<>();
        
        if (nbQuestion > questionFromFile.size())
        {
            nbQuestion = questionFromFile.size();
        }
        
        for (int i = 0; i < nbQuestion; i++)
        {
            Question QR = new Question();
            QR = this.getQuestionFromTexte(questionFromFile.get(i));
            listeQuestion.add(QR);
            QR = null;
        }
        
        return listeQuestion;
    }
}
