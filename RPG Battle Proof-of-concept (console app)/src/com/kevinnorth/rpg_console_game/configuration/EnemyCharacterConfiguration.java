/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kevinnorth.rpg_console_game.configuration;

import java.util.ArrayList;

/**
 *
 */
public class EnemyCharacterConfiguration extends CharacterConfiguration {
    private final int maxMana;
    private final int startingMana;
    
    private final ArrayList<EnemyAttackConfiguration> attacks;

    public EnemyCharacterConfiguration(int maxHealth, int startingHealth,
            int maxMana, int startingMana, String asciiArt,
            ArrayList<EnemyAttackConfiguration> attacks) {
        super(maxHealth, startingHealth, asciiArt);
        this.maxMana = maxMana;
        this.startingMana = startingMana;
        this.attacks = attacks;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public int getStartingMana() {
        return startingMana;
    }
    
    public ArrayList<EnemyAttackConfiguration> getAttacks() {
        return attacks;
    }
    
    @Override
    public boolean isPlayerCharacter() {
        return false;
    }
}
