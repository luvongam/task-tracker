package com.jamlech.tasktracker.controller;

import com.jamlech.tasktracker.entity.Task;
import com.jamlech.tasktracker.entity.TaskStatus;
import com.jamlech.tasktracker.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TaskController {
    private final TaskService taskService;
//    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    public void listAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
            return;
        }

        System.out.println("All Tasks:");
        printTasks(tasks);
    }

    public void listTasksByStatus(TaskStatus status) {
        List<Task> tasks = taskService.getTasksByStatus(status);
        if (tasks.isEmpty()) {
            System.out.println("No tasks with status " + status + " found.");
            return;
        }

        System.out.println("Tasks with status " + status + ":");
        printTasks(tasks);
    }

    public void addTask(String title, String description) {
        Task task = taskService.createTask(title, description);
        System.out.println("Task added successfully:");
        System.out.println(task);
    }

    public void updateTask(String id, String title, String description) {
        Optional<Task> updatedTask = taskService.updateTask(id, title, description, null);
        if (updatedTask.isPresent()) {
            System.out.println("Task updated successfully:");
            System.out.println(updatedTask.get());
        } else {
            System.out.println("Task with ID " + id + " not found.");
        }
    }

    public void updateTaskStatus(String id, TaskStatus status) {
        Optional<Task> updatedTask = taskService.updateTaskStatus(id, status);
        if (updatedTask.isPresent()) {
            System.out.println("Task status updated successfully:");
            System.out.println(updatedTask.get());
        } else {
            System.out.println("Task with ID " + id + " not found.");
        }
    }

    public void deleteTask(String id) {
        boolean deleted = taskService.deleteTask(id);
        if (deleted) {
            System.out.println("Task with ID " + id + " deleted successfully.");
        } else {
            System.out.println("Task with ID " + id + " not found.");
        }
    }

    private void printTasks(List<Task> tasks) {
        for (Task task : tasks) {
            System.out.println("ID: " + task.getId());
            System.out.println("Title: " + task.getTitle());
            System.out.println("Description: " + task.getDescription());
            System.out.println("Status: " + task.getStatus());
            System.out.println("-----------------------");
        }
    }
}
