package com.itmo.service;

import com.itmo.model.Competition;
import com.itmo.repository.CompetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service("competitionService")
public class CompetitionServiceImpl implements CompetitionService {

    @Autowired
    private CompetitionRepository competitionRepository;

    @Override
    public void saveCompetition(Competition competition) {
        competitionRepository.save(competition);
    }

    @Override
    public List<Competition> findAllCompetitions() {
        return competitionRepository.findAll();
    }

    @Override
    public Competition findCompetitionById(long id) {
        return competitionRepository.findCompetitionById(id);
    }

    @Override
    public void removeCompetition(long id) {
        competitionRepository.removeCompetitionById(id);
    }

//    @Override
//    public List<Competition> findAllByUser(User user) {
//        return competitionRepository.findAllByUsers(user);
//    }

}
