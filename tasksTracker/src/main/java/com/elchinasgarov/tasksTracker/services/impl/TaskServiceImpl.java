package com.elchinasgarov.tasksTracker.services.impl;

import com.elchinasgarov.tasksTracker.domain.entities.Task;
import com.elchinasgarov.tasksTracker.domain.entities.TaskList;
import com.elchinasgarov.tasksTracker.domain.entities.TaskPriority;
import com.elchinasgarov.tasksTracker.domain.entities.TaskStatus;
import com.elchinasgarov.tasksTracker.repositories.TaskListRepository;
import com.elchinasgarov.tasksTracker.repositories.TaskRepository;
import com.elchinasgarov.tasksTracker.services.TaskService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskListRepository taskListRepository;

    public TaskServiceImpl(TaskRepository taskRepository, TaskListRepository taskListRepository) {
        this.taskRepository = taskRepository;
        this.taskListRepository = taskListRepository;
    }

    @Override
    public List<Task> listTasks(UUID taskListId) {
        return taskRepository.findByTaskListId(taskListId);
    }

    @Transactional
    @Override
    public Task createTask(UUID taskListId, Task task) {
        if(null != task.getId()){
            throw new IllegalArgumentException("Task id is already set");
        }
        if(null == task.getTitle() || task.getTitle().isBlank()){
            throw new IllegalArgumentException("Task title is empty");
        }

        TaskPriority taskPriority = Optional.ofNullable(task.getPriority()).orElse(TaskPriority.MEDIUM);

        TaskStatus taskStatus = TaskStatus.OPEN;

        TaskList taskList = taskListRepository.findById(taskListId).orElseThrow(()-> new IllegalArgumentException("Task list id not found"));

        LocalDateTime now = LocalDateTime.now();
        Task taskToSave = new Task(
                null,
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                taskStatus,
                taskPriority,
                taskList,
                now,
                now

        );

        return taskRepository.save(taskToSave);
    }

    @Override
    public Optional<Task> getTask(UUID taskListId, UUID taskId) {
        return taskRepository.findByTaskListIdAndId(taskListId,taskId);
    }

    @Transactional
    @Override
    public Task updateTask(UUID taskListId, UUID taskId, Task task) {
        if(null == task.getId()){
            throw new IllegalArgumentException("Task must have an ID!");
        }
        if(!Objects.equals(taskId, task.getId())){
            throw new IllegalArgumentException("Task id does not match!");
        }
        if(null == task.getPriority()){
            throw new IllegalArgumentException("Task  must have an priority!");
        }
        if(null == task.getStatus()){
            throw new IllegalArgumentException("Task  must have an status!");
        }
        Task existingTask = taskRepository.findByTaskListIdAndId(taskListId,taskId)
                .orElseThrow(()-> new IllegalArgumentException("Task not found"));
        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setDueDate(task.getDueDate());
        existingTask.setPriority(task.getPriority());
        existingTask.setStatus(task.getStatus());
        existingTask.setUpdated(LocalDateTime.now());

        return taskRepository.save(existingTask);

    }

    @Transactional
    @Override
    public void deleteTask(UUID taskListId, UUID taskId) {
        taskRepository.deleteByTaskListIdAndId(taskListId,taskId);
    }
}
