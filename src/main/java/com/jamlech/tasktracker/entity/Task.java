package com.jamlech.tasktracker.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Entity
@Getter
@Setter
public class Task {
    @Id
    private String id;

    private String title;
    private String description;
    private TaskStatus status;

    public Task() {
        this.id = UUID.randomUUID().toString();
        this.status=TaskStatus.TODO;
    }
    public Task(String id, String name, String description, TaskStatus status) {
        this.id = UUID.randomUUID().toString();
        this.title = name;
        this.description = description;
        this.status = TaskStatus.TODO;

    }

    public Task(String title, String description) {

    }

    @Override
    public String toString() {
        return  "Task{" + "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
}



}
