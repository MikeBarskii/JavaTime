package com.itmo.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "task_id")
    private int task_id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "input")
    private String input;

    @Column(name = "output")
    private String output;

    @ManyToMany()
    @JoinTable(name = "task_level", joinColumns = @JoinColumn(name = "task_id"), inverseJoinColumns = @JoinColumn(name = "level_id"))
    private Set<Level> levels;

    @ManyToMany()
    @JoinTable(name = "task_user", joinColumns = @JoinColumn(name = "task_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users;

    public Set<Level> getLevels() {
        return levels;
    }

    public void setLevels(Set<Level> levels) {
        this.levels = levels;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int id) {
        this.task_id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public Task(String name, String description, String input, String output) {
        this.name = name;
        this.description = description;
        this.input = input;
        this.output = output;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Task() {

    }

}
