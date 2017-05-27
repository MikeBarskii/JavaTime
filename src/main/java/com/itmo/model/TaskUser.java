package com.itmo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "task_user")
public class TaskUser {

    @Id
    @Column(name = "task_id")
    private int task_id;

    @Column(name = "user_id")
    private int user_id;

    @Column(name = "tests")
    private int tests;

    @Column(name = "complete")
    private boolean complete;

    @Column(name = "time")
    private Date name;

    public int getId() {
        return task_id;
    }

    public void setId(int task_id) {
        this.task_id = task_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getTests() {
        return tests;
    }

    public void setTests(int tests) {
        this.tests = tests;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public Date getName() {
        return name;
    }

    public void setName(Date name) {
        this.name = name;
    }

}
