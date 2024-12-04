package com.java;


import java.util.ArrayList;

import com.java.controller.TaskController;
import com.java.model.Task;
import com.java.util.CheckScannerEntry;
import com.java.util.JsonTools;

public class Main {
    private static JsonTools jTools = new JsonTools();
    private static ArrayList<Task> tasks = new ArrayList<>();
    private static TaskController taskController = new TaskController();

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
            
            opcion = CheckScannerEntry.recibirNumero();

            switch (opcion) {
                case 1:
                    taskController.crearTarea(tasks);
                    break;
                case 2:
                    taskController.mostrarTareas(tasks);
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

    
}