package com.itmo.repository;

import com.itmo.model.Competition;
import com.itmo.model.User;
import com.itmo.model.components.CompetitionUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("competitionUserRepository")
public interface CompetitionUserRepository extends CrudRepository<CompetitionUser, Long> {
    List<CompetitionUser> findCompetitionUserByCompetitionAndUser(Competition competition, User user);

    List<CompetitionUser> findCompetitionUserByUser(User user);

    List<CompetitionUser> findCompetitionUsersByCompetition(Competition competition);

    void removeCompetitionUsersById(long id);
}
