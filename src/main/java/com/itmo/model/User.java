package com.itmo.model;

import com.itmo.model.components.CompetitionUser;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Transient;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    private long id;
    private String email;
    private String password;
    private String confirmPassword;
    private String company;
    private String name;
    private String lastName;
    private long wins;
    private boolean active;

    private Set<Role> roles;
    private Set<Department> departments;
    //    private Set<Task> tasks = new HashSet<>();
    private Set<CompetitionUser> competitionUsers = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", unique = true, nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "password")
    @NotEmpty(message = "*Please provide your password")
    @Transient
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Transient
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Column(name = "name")
    @NotEmpty(message = "*Please provide your name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "last_name")
    @NotEmpty(message = "*Please provide your last name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "email")
    @Email(message = "*Please provide a valid Email")
    @NotEmpty(message = "*Please provide an email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "active")
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Column(name = "company")
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Column(name = "wins")
    public long getWins() {
        return wins;
    }

    public void setWins(long wins) {
        this.wins = wins;
    }

//    @OneToMany(cascade=CascadeType.REMOVE, mappedBy = "user")
//    public Set<Task> getTasks() {
//        return tasks;
//    }
//
//    public void setTasks(Set<Task> tasks) {
//        this.tasks = tasks;
//    }

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "user")
    public Set<CompetitionUser> getCompetitionUsers() {
        return competitionUsers;
    }

    public void setCompetitionUsers(Set<CompetitionUser> competitionUsers) {
        this.competitionUsers = competitionUsers;
    }

    public void addCompetitionUser(CompetitionUser competitionUser) {
        this.competitionUsers.add(competitionUser);
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @ManyToMany()
    @JoinTable(name = "user_department", joinColumns = @JoinColumn(name = "task_id"), inverseJoinColumns = @JoinColumn(name = "department_id"))
    public Set<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(Set<Department> department) {
        this.departments = departments;
    }

}
