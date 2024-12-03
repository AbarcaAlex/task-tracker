package com.java.util;

import java.util.Scanner;

public class CheckScannerEntry {

    public static int recibirNumero(){
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        int op;
        while (true) {
            try {
                op = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Opción no valida");
            }
        }
        
        return op;
    }
}
