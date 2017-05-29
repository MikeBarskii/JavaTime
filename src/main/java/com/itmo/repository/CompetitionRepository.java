package com.itmo.repository;

import com.itmo.model.Competition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("competitionRepository")
public interface CompetitionRepository extends JpaRepository<Competition, Long> {
    @Override
    List<Competition> findAll();

    Competition findCompetitionById(long id);

    void removeCompetitionById(long id);
}
