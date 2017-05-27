package com.itmo.model;

import javax.persistence.*;

@Entity
@Table(name = "level")
public class Level {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "level_id")
    private int level_id;

    @Column(name = "level")
    private String level;

    public int getId() {
        return level_id;
    }

    public void setId(int id) {
        this.level_id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String role) {
        this.level = role;
    }

}

