package com.java;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class Main {
    public static void main(String[] args) {
        
        int salir = 1;
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);

        while (salir !=0) {
            
            System.out.printf("""
                -----------------------------------
                Bienvenido!

                Seleccione una opción:

                1 - Agregar una nueva tarea
                2 - Mostrar la lista de tareas
                3 - Salir 
                -----------------------------------
            """);
            
            int opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1:
                    crearTarea();
                    break;
                case 2:

                    break;
                case 3:
                    System.out.println("\tAdios...");
                    salir = 0;
                    break;
                default:
                    System.out.println("Opción no valida");
                    break;
            }
        }
        

    }

    public static void crearTarea(){
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);

        System.out.printf("""
            ---------------------------------------------
            Escriba una descripción de la nueva tarea:
        """);

        String des = scanner.nextLine();

        TaskDTO taskDTO = new TaskDTO(des, Status.todo);

        Task task = new Task(taskDTO);

        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        
        try {
            String json = om.writeValueAsString(task);
            
            try {
                String path = System.getProperty("user.dir");
                FileWriter fw = new FileWriter(new File(path+"\\tasks","task.json"));
                fw.write(json);
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("\tTarea creada!\t");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        
    }
}