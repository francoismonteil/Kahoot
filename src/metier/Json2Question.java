/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

/**
 *
 * @author francois.monteil
 */
public class Json2Question {
    
    
    public static Question[] generateQuestion()
    {
        ObjectMapper mapper;
        mapper = new ObjectMapper();
        Map<String,Object> map = mapper.readValue(json, Map.class);
        
        return null;
    }
}
