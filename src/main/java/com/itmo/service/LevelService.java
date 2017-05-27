package com.itmo.service;

import com.itmo.model.Level;

import java.util.List;

public interface LevelService {
    Level findLevelByName(String level);

    List<Level> findAllLevels();
}
