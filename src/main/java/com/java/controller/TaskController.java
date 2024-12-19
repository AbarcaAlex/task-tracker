package com.java.controller;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.java.model.Task;
import com.java.model.TaskDTO;
import com.java.model.TaskStatus;
import com.java.util.CheckScannerEntry;

public class TaskController {
    private static Scanner scanner = new Scanner(System.in);

    public void crearTarea(ArrayList<Task> tasks){

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

    public void mostrarTareas(ArrayList<Task> tasks){
        
        if (!tasks.isEmpty()) {
            int filtro = 0;
            ArrayList<Task> filterTasks = new ArrayList<>();
            while (true) {
                AtomicInteger atom = new AtomicInteger(1);
                filterTasks.clear();
                switch (filtro) {
                    case 1:
                        System.out.printf("""
                            -------------------------------------------------------------
                            |   Filtro aplicado: Mostrando solo las tareas POR HACER    |
                            -------------------------------------------------------------
                        """);
                        tasks.stream()
                            .filter(t -> t.getStatus().equals(TaskStatus.TODO))
                            .forEach(t -> filterTasks.add(t));
                        if (filterTasks.isEmpty()) {
                            System.out.println("No hay tareas que mostrar..");
                        }else{
                            filterTasks.forEach(ft -> System.out.println("\n\t"+atom.getAndIncrement()+" - "+ft+"\n"));
                        }
                        break;
                    case 2:
                        System.out.printf("""
                            -------------------------------------------------------------
                            |   Filtro aplicado: Mostrando solo las tareas EN PROGRESO  |
                            -------------------------------------------------------------
                        """);
                        tasks.stream()
                            .filter(t -> t.getStatus().equals(TaskStatus.INPROGRESS))
                            .forEach(t -> filterTasks.add(t));
                        if (filterTasks.isEmpty()) {
                            System.out.println("No hay tareas que mostrar..");
                        }else{
                            filterTasks.forEach(ft -> System.out.println("\n\t"+atom.getAndIncrement()+" - "+ft+"\n"));
                        }
                        break;
                    case 3:
                        System.out.printf("""
                            -------------------------------------------------------------
                            |   Filtro aplicado: Mostrando solo las tareas FINALIZADAS  |
                            -------------------------------------------------------------
                        """);
                        tasks.stream()
                            .filter(t -> t.getStatus().equals(TaskStatus.DONE))
                            .forEach(t -> filterTasks.add(t));

                        if (filterTasks.isEmpty()) {
                            System.out.println("No hay tareas que mostrar..");
                        } else {
                            filterTasks.forEach(ft -> System.out.println("\n\t" + atom.getAndIncrement() + " - " + ft + "\n"));
                        }
                        break;
                    default:
                        System.out.printf("""
                            ---------------------------------
                            |   Mostrando TODAS las tareas  |
                            ---------------------------------
                        """);
                        tasks.forEach(t -> System.out.println("\n\t"+atom.getAndIncrement()+" - "+t+"\n"));
                        break;
                }
                System.out.printf("""
                    -----------------------------------------------------
                    |   ? - Numero de la tarea para ver más detalles    |
                    |  -1 - Filtrar tareas                              |
                    |   0 - Regresar                                    |
                    -----------------------------------------------------
                """);
                int index = CheckScannerEntry.recibirNumero(); 

                if (index != 0 && index != -1) {
                    index--;
                    if (filtro == 0) {
                        detallarTarea(index, tasks);
                    }else{
                        detallarTarea(index, filterTasks);
                    }
                    
                }else if(index == -1){
                    while (true) {
                        System.out.printf("""
                            ---------------------------------------------
                            |   1 - Mostrar solo tareas POR HACER       |    
                            |   2 - Mostrar solo tareas EN PROGRESO     |    
                            |   3 - Mostrar solo tareas FINALIZADAS     |    
                            |   4 - Mostrar TODAS las tareas            |
                            ---------------------------------------------
                        """);
                        filtro = CheckScannerEntry.recibirNumero();
                        if (filtro >= 1 && filtro <= 4) {
                            break;
                        }else{
                            System.out.println("Opción no valida");
                        }
                    }
                    
                    
                }else{
                    break;
                }
            }
            
        }else{
            System.out.println("\tNo hay tareas que mostrar..");
        }
        
    }

    public void detallarTarea(int index, ArrayList<Task> tasks){
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
                        modificarTarea(index, tasks);
                        break;
                    case 2:
                        eliminarTarea(index, tasks);
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

    public void modificarTarea(int index, ArrayList<Task> tasks){
        
        System.out.printf("""
            ---------------------------------
            |   1 - Cambiar la descripción  |
            |   2 - Cambiar el estado       | 
            |   3 - Regresar                |
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
            case 3:

                break;
            default:
                System.out.println("Opción no valida");
                break;
        }
        System.out.println("\n\tSe modifico la tarea con éxito!\n");
    }

    public void eliminarTarea(int index, ArrayList<Task> tasks){
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
