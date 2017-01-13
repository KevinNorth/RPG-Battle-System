package com.kevinnorth.rpg_console_game;

import com.kevinnorth.rpg_battle_system.Director;

public class Main {

    public static void main(String[] args) {
        long lastTime = System.currentTimeMillis();
        Director director = null;
        
        while(true) {
            long newTime = System.currentTimeMillis();
            director.onFrame((newTime - lastTime) / 1000f);
            newTime = lastTime;
        }
    }
    
}
