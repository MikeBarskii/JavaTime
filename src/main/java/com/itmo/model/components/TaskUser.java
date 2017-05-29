//package com.itmo.model.components;
//
//import com.itmo.model.Task;
//import com.itmo.model.User;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "task_user")
//public class TaskUser {
//    private long id;
//    private User user;
//    private Task task;
//
//    private boolean complete;
//
//    public TaskUser() {
//
//    }
//
//    public TaskUser(User user, Task task) {
//        this.user = user;
//        this.task = task;
//    }
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "task_user_id", unique = true, nullable = false)
//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "user_id")
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "task_id")
//    public Task getTask() {
//        return task;
//    }
//
//    public void setTask(Task task) {
//        this.task = task;
//    }
//
//    @Column(name = "complete")
//    public boolean isComplete() {
//        return complete;
//    }
//
//    public void setComplete(boolean complete) {
//        this.complete = complete;
//    }
//
//}
