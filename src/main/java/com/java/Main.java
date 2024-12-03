package com.java;


import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import com.java.model.TaskStatus;
import com.java.model.Task;
import com.java.model.TaskDTO;
import com.java.util.CheckScannerEntry;
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
            
            opcion = CheckScannerEntry.recibirNumero();

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
            while (true) {
                AtomicInteger atom = new AtomicInteger(1);
                tasks.stream().forEach(t -> System.out.println("\n\t"+atom.getAndIncrement()+" - "+t+"\n"));
                System.out.printf("""
                    -----------------------------------------------------
                    |   ? - Numero de la tarea para ver más detalles    |
                    |   0 - Regresar                                    |
                    -----------------------------------------------------
                """);
                int index = CheckScannerEntry.recibirNumero();

                if (index != 0) {
                    index--;
                    detallarTarea(index);
                }else{
                    break;
                }
            }
            
        }else{
            System.out.println("\tNo hay tareas que mostrar..");
        }
        
    }

    public static void detallarTarea(int index){
        try {
            int salir = 1;
            while (salir != 0) {
                System.out.println(tasks.get(index).toStringFullDetails());
                System.out.printf("""
                    -------------------------
                    |   1 - Modificar tarea |
                    |   2 - Eliminar tarea  |
                    |   3 - Regresar        |
                    -------------------------
                """);
                int opt =  CheckScannerEntry.recibirNumero();
                switch (opt) {
                    case 1:
                        modificarTarea(index);
                        break;
                    case 2:
                        eliminarTarea(index);
                        break;
                    case 3:
                        salir = 0;
                        break;
                    default:
                        System.out.println("Opción no valida");
                        break;
                }
            }
            
        } catch (IndexOutOfBoundsException e) {
            System.out.println("No existe esa tarea");
        }
    }

    public static void modificarTarea(int index){
        
        System.out.printf("""
            ---------------------------------
            |   1 - Cambiar la descripción  |
            |   2 - Cambiar el estado       | 
            ---------------------------------
        """);

        int op = CheckScannerEntry.recibirNumero();

        switch (op) {
            case 1:
                System.out.printf("""
                    --------------------------------
                    Escriba la nueva descripción:
                """);
                String des = scanner.nextLine();
                tasks.get(index).updateDescription(des);
                break;
        
            case 2:
                System.out.printf("""
                    -----------------------------------------
                    |   Elija el nuevo estado de la tarea:  |
                    |                                       |
                    |       1 - [ POR HACER ]               |
                    |       2 - [ EN PROGRESO ]             |
                    |       3 - [ FINALIZADO ]              |
                    -----------------------------------------    
                """);
                int opt = CheckScannerEntry.recibirNumero();
                switch (opt) {
                    case 1:
                        tasks.get(index).updateStatus(TaskStatus.TODO);
                        break;
                    case 2:
                        tasks.get(index).updateStatus(TaskStatus.INPROGRESS);
                        break;
                    case 3:
                        tasks.get(index).updateStatus(TaskStatus.DONE);
                        break;
                    default:
                        System.out.println("Opción no valida");
                        break;
                }
                break;
            default:
                System.out.println("Opción no valida");
                break;
        }
        System.out.println("\n\tSe modifico la tarea con éxito!\n");
    }

    public static void eliminarTarea(int index){
        System.out.printf("""
            -----------------------------------------------------
            |   ¿ Esta seguro de querer eliminar la tarea ?     |
            |                                                   |
            |       SI (1)              NO (0)                  |
            -----------------------------------------------------
        """);
        int op = CheckScannerEntry.recibirNumero();
        if (op == 1) {
            tasks.remove(index);
        }
    }
}