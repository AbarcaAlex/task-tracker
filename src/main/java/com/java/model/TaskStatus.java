package com.java.model;

public enum TaskStatus {
    TODO("POR HACER"),
    INPROGRESS("EN PROGRESO"),
    DONE("FINALIZADO");
    
    private String spanish;

    TaskStatus(String spanish) {
        this.spanish = spanish;
    }

    public String getSpanish(){
        return spanish;
    }
}
