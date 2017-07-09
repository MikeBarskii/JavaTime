package com.itmo.model;

import javax.persistence.*;

@Entity
@Table(name = "department")
public class Department {

    private long id;
    private String department;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "department_id", unique = true, nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "department")
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String role) {
        this.department = department;
    }

}

