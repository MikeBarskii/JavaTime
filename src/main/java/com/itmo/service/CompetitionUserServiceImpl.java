package com.itmo.service;

import com.itmo.model.Competition;
import com.itmo.model.User;
import com.itmo.model.components.CompetitionUser;
import com.itmo.repository.CompetitionUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service("competitionUserService")
public class CompetitionUserServiceImpl implements CompetitionUserService {

    @Autowired
    private CompetitionUserRepository competitionUserRepository;

    @Override
    public void saveUserCompetition(CompetitionUser competitionUser) {
        competitionUserRepository.save(competitionUser);
    }

    @Override
    public List<CompetitionUser> findCompetitionUsersByUser(User user) {
        return competitionUserRepository.findCompetitionUserByUser(user);
    }

    @Override
    public List<CompetitionUser> findCompetitionUsersByCompetition(Competition competition) {
        return competitionUserRepository.findCompetitionUsersByCompetition(competition);
    }

    @Override
    public void removeCompetitionUserById(long id) {
        competitionUserRepository.removeCompetitionUsersById(id);
    }
}
