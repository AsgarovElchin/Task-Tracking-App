package com.elchinasgarov.tasksTracker.mappers;


import com.elchinasgarov.tasksTracker.domain.dto.TaskDto;
import com.elchinasgarov.tasksTracker.domain.entities.Task;

public interface TaskMapper {

    Task fromDto(TaskDto taskDto);

    TaskDto toDto(Task task);


}
