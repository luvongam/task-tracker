package com.jamlech.tasktracker;

import com.jamlech.tasktracker.controller.TaskController;
import com.jamlech.tasktracker.entity.TaskStatus;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TaskCommandlineRunner implements CommandLineRunner {
    private final TaskController taskController;

    public TaskCommandlineRunner(TaskController taskController) {
        this.taskController = taskController;
    }

    @Override
    public void run(String... args) {
        if (args.length == 0) {
            printUsage();
            return;
        }

        String command = args[0].toLowerCase();

        try {
            switch (command) {
                case "add":
                    if (args.length < 3) {
                        System.out.println("Error: Title and description required for adding a task.");
                        printUsage();
                        return;
                    }
                    taskController.addTask(args[1], args[2]);
                    break;

                case "update":
                    if (args.length < 4) {
                        System.out.println("Error: ID, title, and description required for updating a task.");
                        printUsage();
                        return;
                    }
                    taskController.updateTask(args[1], args[2], args[3]);
                    break;

                case "delete":
                    if (args.length < 2) {
                        System.out.println("Error: Task ID required for deletion.");
                        printUsage();
                        return;
                    }
                    taskController.deleteTask(args[1]);
                    break;

                case "start":
                    if (args.length < 2) {
                        System.out.println("Error: Task ID required to mark as in progress.");
                        printUsage();
                        return;
                    }
                    taskController.updateTaskStatus(args[1], TaskStatus.IN_PROGRESS);
                    break;

                case "complete":
                    if (args.length < 2) {
                        System.out.println("Error: Task ID required to mark as done.");
                        printUsage();
                        return;
                    }
                    taskController.updateTaskStatus(args[1], TaskStatus.COMPLETED);
                    break;

                case "list":
                    if (args.length > 1) {
                        String filter = args[1].toLowerCase();
                        switch (filter) {
                            case "all":
                                taskController.listAllTasks();
                                break;
                            case "todo":
                                taskController.listTasksByStatus(TaskStatus.TODO);
                                break;
                            case "inprogress":
                                taskController.listTasksByStatus(TaskStatus.IN_PROGRESS);
                                break;
                            case "done":
                                taskController.listTasksByStatus(TaskStatus.COMPLETED);
                                break;
                            default:
                                System.out.println("Error: Unknown list filter: " + filter);
                                printUsage();
                        }
                    } else {
                        taskController.listAllTasks();
                    }
                    break;

                default:
                    System.out.println("Error: Unknown command: " + command);
                    printUsage();
            }
        } catch (Exception e) {
            System.err.println("Error executing command: " + e.getMessage());
        }
    }

    private void printUsage() {
        System.out.println("Task Tracker CLI - Usage:");
        System.out.println("  add <title> <description> - Add a new task");
        System.out.println("  update <id> <title> <description> - Update an existing task");
        System.out.println("  delete <id> - Delete a task");
        System.out.println("  start <id> - Mark a task as in progress");
        System.out.println("  complete <id> - Mark a task as done");
        System.out.println("  list [all|todo|inprogress|done] - List tasks (default: all)");
    }
}

