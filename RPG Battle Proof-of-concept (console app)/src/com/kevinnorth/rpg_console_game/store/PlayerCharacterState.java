/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kevinnorth.rpg_console_game.store;

import com.kevinnorth.rpg_console_game.configuration.PlayerCharacterConfiguration;

/**
 *
 */
public class PlayerCharacterState extends CharacterState {
    private int defense;

    public PlayerCharacterState(PlayerCharacterConfiguration configuration,
            int maxHealth, int currentHealth, int defense) {
        super(configuration, maxHealth, currentHealth);
        this.defense = defense;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }
}
