package com.itmo.service;

import com.itmo.model.Competition;
import com.itmo.model.User;

import java.util.List;

public interface CompetitionService {
    void saveCompetition(Competition competition);

    List<Competition> findAllCompetitions();

    List<Competition> findAllByUser(User user);
}
