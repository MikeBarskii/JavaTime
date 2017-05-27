package com.itmo.service;

import com.itmo.model.Level;
import com.itmo.repository.LevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("levelService")
public class LevelServiceImpl implements LevelService {

    @Autowired
    private LevelRepository levelRepository;

    @Override
    public Level findLevelByName(String level) {
        return levelRepository.findByLevel(level);
    }

    @Override
    public List<Level> findAllLevels() {
        return levelRepository.findAll();
    }

}
