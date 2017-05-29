package com.itmo.model.components;

import com.itmo.model.Competition;
import com.itmo.model.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "competition_user")
public class CompetitionUser {
    private long id;
    private User user;
    private Competition competition;

    private boolean complete;
    private Date time;

    public CompetitionUser() {

    }

    public CompetitionUser(User user, Competition competition) {
        this.user = user;
        this.competition = competition;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "competition_user_id", unique = true, nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "competition_id")
    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    @Column(name = "complete")
    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    @Column(name = "user_time")
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
