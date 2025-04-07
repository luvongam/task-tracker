package com.jamlech.tasktracker.service;

import com.jamlech.tasktracker.entity.Task;
import com.jamlech.tasktracker.entity.TaskStatus;
import com.jamlech.tasktracker.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

public class TaskService {
    private final TaskRepository taskRepository;
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public List<Task> getTasksByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status);
    }

    public Optional<Task> getTaskById(String id) {
        return taskRepository.findById(id);
    }

    public Task createTask(String title, String description) {
        Task task = new Task(title, description);
        return taskRepository.save(task);
    }
    public Task createTask(String title, String description) {
        Task task = new Task(title, description);
        return taskRepository.save(task);
    }

    public Optional<Task> updateTask(String id, String title, String description, TaskStatus status) {
        Optional<Task> existingTask = taskRepository.findById(id);
        if (existingTask.isPresent()) {
            Task task = existingTask.get();
            if (title != null) {
                task.setTitle(title);
            }
            if (description != null) {
                task.setDescription(description);
            }
            if (status != null) {
                task.setStatus(status);
            }
            return Optional.of(taskRepository.save(task));
        }
        return Optional.empty();
    }

    public Optional<Task> updateTaskStatus(String id, TaskStatus status) {
        return updateTask(id, null, null, status);
    }

    public boolean deleteTask(String id) {
        return taskRepository.deleteById(id);
    }

}
