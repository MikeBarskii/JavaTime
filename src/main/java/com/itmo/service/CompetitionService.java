package com.itmo.service;

import com.itmo.model.Competition;

import java.util.List;

public interface CompetitionService {
    void saveCompetition(Competition competition);

    List<Competition> findAllCompetitions();

    Competition findCompetitionById(long id);

    void removeCompetition(long id);
}
