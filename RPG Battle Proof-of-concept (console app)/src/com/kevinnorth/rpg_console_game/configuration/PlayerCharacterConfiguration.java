package com.kevinnorth.rpg_console_game.configuration;

import java.util.ArrayList;

public final class PlayerCharacterConfiguration extends CharacterConfiguration {
    private final int maxMana;
    private final int startingMana;
    
    private final String name;
    
    private final ArrayList<PlayerAttackConfiguration> attacks;

    public PlayerCharacterConfiguration(int maxHealth, int startingHealth,
            int maxMana, int startingMana, String name, String asciiArt,
            ArrayList<PlayerAttackConfiguration> attacks) {
        super(maxHealth, startingHealth, asciiArt);
        this.maxMana = maxMana;
        this.startingMana = startingMana;
        this.name = name;
        this.attacks = attacks;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getStartingHealth() {
        return startingHealth;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public int getStartingMana() {
        return startingMana;
    }

    public String getName() {
        return name;
    }

    public String getAsciiArt() {
        return asciiArt;
    }
    
    public ArrayList<PlayerAttackConfiguration> getAttacks() {
        return attacks;
    }

    @Override
    public boolean isPlayerCharacter() {
        return true;
    }
}
