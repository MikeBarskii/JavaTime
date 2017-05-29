package com.itmo.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "task")
public class Task {

    private long id;
    private String name;
    private String description;
    private String input;
    private String output;

    private Set<Level> levels = new HashSet<>();
    //    private Set<User> users = new HashSet<>();
    private Set<Competition> competitions = new HashSet<>();

    public Task() {

    }

    public Task(String name, String description, String input, String output) {
        this.name = name;
        this.description = description;
        this.input = input;
        this.output = output;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "task_id", unique = true, nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "input")
    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    @Column(name = "output")
    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

//    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "task")
//    public Set<User> getUsers() {
//        return users;
//    }
//
//    public void setUsers(Set<User> users) {
//        this.users = users;
//    }

    @ManyToMany()
    @JoinTable(name = "task_level", joinColumns = @JoinColumn(name = "task_id"), inverseJoinColumns = @JoinColumn(name = "level_id"))
    public Set<Level> getLevels() {
        return levels;
    }

    public void setLevels(Set<Level> levels) {
        this.levels = levels;
    }

    @ManyToMany()
    @JoinTable(name = "competition_task", joinColumns = @JoinColumn(name = "competition_id"), inverseJoinColumns = @JoinColumn(name = "task_id"))
    public Set<Competition> getCompetitions() {
        return competitions;
    }

    public void setCompetitions(Set<Competition> competitions) {
        this.competitions = competitions;
    }


}
