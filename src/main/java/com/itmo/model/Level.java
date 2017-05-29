package com.itmo.model;

import javax.persistence.*;

@Entity
@Table(name = "level")
public class Level {

    private long id;
    private String level;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "level_id", unique = true, nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "level")
    public String getLevel() {
        return level;
    }

    public void setLevel(String role) {
        this.level = role;
    }

}

