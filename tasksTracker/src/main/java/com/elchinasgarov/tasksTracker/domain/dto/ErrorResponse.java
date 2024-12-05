package com.elchinasgarov.tasksTracker.domain.dto;

public record ErrorResponse(
        int status,
        String message,
        String details
) {
}
