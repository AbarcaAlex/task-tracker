package com.java.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.java.model.Task;

public class JsonTools {
    ObjectMapper om = new ObjectMapper();
    
    public void saveJson(List<Task> tasks){
        
        om.registerModule(new JavaTimeModule());
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        
        try {
            String json = om.writeValueAsString(tasks);
            
            try {
                String path = System.getProperty("user.dir");
                FileWriter fw = new FileWriter(new File(path+"\\tasks","tasks.json"));
                fw.write(json);
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Task> loadJson(){

        om.registerModule(new JavaTimeModule());
        TypeReference<ArrayList<Task>> jacksonTypeReference = new TypeReference<ArrayList<Task>>() {};
        String path = System.getProperty("user.dir");
        ArrayList<Task> tasks = null;
       
        try {
            tasks = om.readValue(new File(path+"\\tasks","tasks.json"), jacksonTypeReference);
        } catch (StreamReadException e) {
            e.printStackTrace();
        } catch (DatabindException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }  
        
        return tasks;
    }


}
