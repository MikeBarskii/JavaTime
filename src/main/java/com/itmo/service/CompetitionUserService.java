package com.itmo.service;

import com.itmo.model.Competition;
import com.itmo.model.User;
import com.itmo.model.components.CompetitionUser;

import java.util.List;

public interface CompetitionUserService {
    void saveUserCompetition(CompetitionUser competitionUser);

    List<CompetitionUser> findCompetitionUsersByUser(User user);

    List<CompetitionUser> findCompetitionUsersByCompetition(Competition competition);

    void removeCompetitionUserById(long id);
}