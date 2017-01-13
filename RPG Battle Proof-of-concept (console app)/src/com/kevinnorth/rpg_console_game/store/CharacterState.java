package com.kevinnorth.rpg_console_game.store;

import com.kevinnorth.rpg_console_game.configuration.CharacterConfiguration;

public abstract class CharacterState<Configuration extends CharacterConfiguration> {
    protected final Configuration configuration;
    protected int maxHealth;
    protected int currentHealth;
    
    public CharacterState(Configuration configuration, int maxHealth, int currentHealth) {
        this.configuration = configuration;
        this.maxHealth = maxHealth;
        this.currentHealth = currentHealth;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }
    
    public void setCurrentHealth(int newCurrentHealth) {
        this.currentHealth = Math.min(newCurrentHealth, maxHealth);
    }

    public void changeCurrentHealth(int amountToAddToCurrentHealth) {
        this.currentHealth = Math.min(this.currentHealth + amountToAddToCurrentHealth, maxHealth);
    }
}
