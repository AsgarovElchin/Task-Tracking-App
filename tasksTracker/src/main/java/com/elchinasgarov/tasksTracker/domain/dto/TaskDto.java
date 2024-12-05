package com.elchinasgarov.tasksTracker.domain.dto;

import com.elchinasgarov.tasksTracker.domain.entities.TaskPriority;
import com.elchinasgarov.tasksTracker.domain.entities.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskDto(
        UUID id,
        String title,
        String description,
        LocalDateTime dueDate,
        TaskPriority priority,
        TaskStatus status
) {
}
