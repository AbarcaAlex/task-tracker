package com.java.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Task {

    private long id;
    private String description;
    private TaskStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public Task(TaskDTO taskDTO){
        this.id = System.currentTimeMillis();
        this.description = taskDTO.description();
        this.status = taskDTO.status();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = null;
    }

    public void updateTask(TaskDTO taskDTO){
        this.description = taskDTO.description();
        this.status = taskDTO.status();
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "\t[ " + status.getSpanish() + " ]\t" + description;
    }

}
