/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kevinnorth.rpg_console_game.store;

import com.kevinnorth.rpg_console_game.configuration.EnemyCharacterConfiguration;

public class EnemyCharacterState extends CharacterState<EnemyCharacterConfiguration> {
    private SpikePosition spikePosition;

    public EnemyCharacterState(EnemyCharacterConfiguration configuration,
            int maxHealth, int currentHealth, SpikePosition spikePosition) {
        super(configuration, maxHealth, currentHealth);
        this.spikePosition = spikePosition;
    }

    public SpikePosition getSpikePosition() {
        return spikePosition;
    }

    public void setSpikePosition(SpikePosition spikePosition) {
        this.spikePosition = spikePosition;
    }
}
