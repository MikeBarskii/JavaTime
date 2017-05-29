package com.itmo.model;

import com.itmo.model.components.CompetitionUser;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "competitions")
public class Competition {

    private long id;
    private String name;
    private long participants;
    private Date due_date;
    private String winner;
    private String[] users;

    private Set<CompetitionUser> competitionUsers = new HashSet<>();
    private Set<Task> tasks = new HashSet<>();

    public Competition() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "competition_id", unique = true, nullable = false)
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

    @Column(name = "participants")
    public long getParticipants() {
        return participants;
    }

    public void setParticipants(long participants) {
        this.participants = participants;
    }

    @Column(name = "due_date")
    public Date getDue_date() {
        return due_date;
    }

    public void setDue_date(Date due_date) {
        this.due_date = due_date;
    }

    @Column(name = "winner")
    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "competition")
    public Set<CompetitionUser> getCompetitionUsers() {
        return competitionUsers;
    }

    public void setCompetitionUsers(Set<CompetitionUser> competitionUsers) {
        this.competitionUsers = competitionUsers;
    }

    public void addCompetitionUser(CompetitionUser competitionUser) {
        this.competitionUsers.add(competitionUser);
    }

    @ManyToMany()
    @JoinTable(name = "competition_task", joinColumns = @JoinColumn(name = "competition_id"), inverseJoinColumns = @JoinColumn(name = "task_id"))
    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public String[] getUsers() {
        return users;
    }

    public void setUsers(String[] users) {
        this.users = users;
    }
}
