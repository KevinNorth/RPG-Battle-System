package com.kevinnorth.rpg_console_game.configuration;

public abstract class CharacterConfiguration {
    protected final int maxHealth;
    protected final int startingHealth;
    protected final String asciiArt;

    public CharacterConfiguration(int maxHealth, int startingHealth, String asciiArt) {
        this.maxHealth = maxHealth;
        this.startingHealth = startingHealth;
        this.asciiArt = asciiArt;
    }
    
    public int getMaxHealth() {
        return maxHealth;
    }

    public int getStartingHealth() {
        return startingHealth;
    }

    public String getAsciiArt() {
        return asciiArt;
    }
    
    public abstract boolean isPlayerCharacter();
}
