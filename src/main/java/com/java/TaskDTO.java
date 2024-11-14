package com.java;

public record TaskDTO(
    String description,
    Enum<Status> status
) {

}
