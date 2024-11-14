package com.java;

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
    private Enum<Status> status;
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
        return "Task [id=" + id + ", description=" + description + ", status=" + status + ", createdAt=" + createdAt
                + ", updatedAt=" + updatedAt + "]";
    }

}
