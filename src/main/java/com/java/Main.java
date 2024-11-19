package com.java;


import java.util.ArrayList;
import java.util.Scanner;


import com.java.model.TaskStatus;
import com.java.model.Task;
import com.java.model.TaskDTO;
import com.java.util.JsonTools;

public class Main {
    private static ArrayList<Task> tasks = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static JsonTools jTools = new JsonTools();

    public static void main(String[] args) {
        int opcion = 0;
        int salir = 1;

        if (jTools.loadJson() != null) {
            tasks = jTools.loadJson();
        }
        
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
            
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                e.printStackTrace();
            }

            switch (opcion) {
                case 1:
                    crearTarea();
                    break;
                case 2:
                    mostrarTareas();
                    break;
                case 3:
                    System.out.println("\tAdios...");
                    salir = 0;
                    break;
                default:
                    System.out.println("\tOpción no valida");
                    break;
            }
        }
        jTools.saveJson(tasks);
    }

    public static void crearTarea(){

        System.out.printf("""
            ---------------------------------------------
            Escriba una descripción de la nueva tarea:
        """);

        String des = scanner.nextLine();

        TaskDTO taskDTO = new TaskDTO(des, TaskStatus.TODO);

        Task task = new Task(taskDTO);

        tasks.add(task);

        System.out.println("\tSe creo la nueva tarea!");
        
    }

    public static void mostrarTareas(){
        if (!tasks.isEmpty()) {
            tasks.stream().forEach(System.out::println);
        }else{
            System.out.println("\tNo hay tareas que mostrar..");
        }
        
    }
}