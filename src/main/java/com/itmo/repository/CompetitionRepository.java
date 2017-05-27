package com.itmo.repository;

import com.itmo.model.Competition;
import com.itmo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("competitionRepository")
public interface CompetitionRepository extends JpaRepository<Competition, Integer> {

    @Override
    List<Competition> findAll();

    List<Competition> findAllByUsers(User user);

}
