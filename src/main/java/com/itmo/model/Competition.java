package com.itmo.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "competition")
public class Competition {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "competition_id")
    private int competition_id;

    @Column(name = "name")
    private String name;

    @Column(name = "participants")
    private int participants;

    @Column(name = "due_date")
    private Date due_date;

    @Column(name = "winner")
    private String winner;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "competition_user", joinColumns = @JoinColumn(name = "competition_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "competition_task", joinColumns = @JoinColumn(name = "competition_id"), inverseJoinColumns = @JoinColumn(name = "task_id"))
    private Set<User> tasks;

    public int getCompetition_id() {
        return competition_id;
    }

    public void setCompetition_id(int competition_id) {
        this.competition_id = competition_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParticipants() {
        return participants;
    }

    public void setParticipants(int participants) {
        this.participants = participants;
    }

    public Date getDue_date() {
        return due_date;
    }

    public void setDue_date(Date due_date) {
        this.due_date = due_date;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<User> getTasks() {
        return tasks;
    }

    public void setTasks(Set<User> tasks) {
        this.tasks = tasks;
    }

    public Competition() {

    }

}
