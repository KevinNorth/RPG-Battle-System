package com.kevinnorth.rpg_console_game;

import com.kevinnorth.rpg_battle_system.Director;

public class Battle {
    private final Director director;
    private long previousFrameTime;
    
    public Battle(DirectorFactory directorFactory) {
        previousFrameTime = System.currentTimeMillis();
        director = directorFactory.createDirector();
    }
    
    public void gameLoop() {
        long currentTime = System.currentTimeMillis();
        float detlaTime = (currentTime - previousFrameTime) / 1000f;

        director.onFrame(detlaTime);

        previousFrameTime = currentTime;
    }
}
