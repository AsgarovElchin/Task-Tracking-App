package com.elchinasgarov.tasksTracker.mappers;

import com.elchinasgarov.tasksTracker.domain.dto.TaskListDto;
import com.elchinasgarov.tasksTracker.domain.entities.TaskList;

public interface TaskListMapper {

    TaskList fromDto(TaskListDto taskListDto);

    TaskListDto toDto(TaskList taskList);
}
