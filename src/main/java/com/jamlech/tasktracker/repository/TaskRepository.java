package com.jamlech.tasktracker.repository;

import aj.org.objectweb.asm.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jamlech.tasktracker.entity.Task;
import com.jamlech.tasktracker.entity.TaskStatus;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
;
import java.util.stream.Collectors;

@Repository
public class TaskRepository {
    private static final String TASKS_FILE = "tasks.json";
    private final ObjectMapper objectMapper;

    public TaskRepository(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<Task> findAll() {
        return loadTasks();
    }

    public List<Task> findByStatus(TaskStatus status) {
        return loadTasks().stream()
                .filter(task -> task.getStatus() == status)
                .collect(Collectors.toList());
    }

    public Optional<Task> findById(String id) {
        return loadTasks().stream()
                .filter(task -> task.getId().equals(id))
                .findFirst();
    }

    public Task save(Task task) {
        List<Task> tasks = loadTasks();

        // Check if task already exists for update
        Optional<Task> existingTask = tasks.stream()
                .filter(t -> t.getId().equals(task.getId()))
                .findFirst();

        existingTask.ifPresent(tasks::remove);

        tasks.add(task);
        saveTasks(tasks);
        return task;
    }

    public boolean deleteById(String id) {
        List<Task> tasks = loadTasks();
        boolean removed = tasks.removeIf(task -> task.getId().equals(id));
        if (removed) {
            saveTasks(tasks);
        }
        return removed;
    }

    private List<Task> loadTasks() {
        File file = new File(TASKS_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try {
            return objectMapper.readValue(file, new TypeReference<List<Task>>() {
            });
        } catch (IOException e) {
            System.err.println("Error reading tasks file: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private void saveTasks(List<Task> tasks) {
        try {
            objectMapper.writeValue(new File(TASKS_FILE), tasks);
        } catch (IOException e) {
            System.err.println("Error writing tasks file: " + e.getMessage());
        }
    }
}
