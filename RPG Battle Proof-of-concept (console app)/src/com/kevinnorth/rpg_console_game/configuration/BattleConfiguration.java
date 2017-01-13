/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kevinnorth.rpg_console_game.configuration;

import com.kevinnorth.rpg_battle_system.configuration.Configuration;
import com.kevinnorth.rpg_console_game.reducers.TurnOrderReducer;
import java.util.ArrayList;

/**
 *
 */
public class BattleConfiguration extends Configuration {
    private final ArrayList<PlayerCharacterConfiguration> playerCharacters;
    private final ArrayList<EnemyCharacterConfiguration> enemyCharacters;
    
    private final PlayerCharacterConfiguration mainCharacter;
    private final PlayerCharacterConfiguration initialFrontPlayerCharacter;
    private final PlayerCharacterConfiguration initialBackPlayerCharacter;
    
    private final TurnOrderReducer turnOrderReducer;
    
    private final int initialMaxMana;
    private final int initialCurrentMana;

    public BattleConfiguration(ArrayList<PlayerCharacterConfiguration> playerCharacters,
            ArrayList<EnemyCharacterConfiguration> enemyCharacters,
            PlayerCharacterConfiguration mainCharacter,
            PlayerCharacterConfiguration initialFrontPlayerCharacter,
            PlayerCharacterConfiguration initialBackPlayerCharacter,
            TurnOrderReducer turnOrderReducer,
            int initialMaxMana, int initialCurrentMana) {
        this.playerCharacters = playerCharacters;
        this.enemyCharacters = enemyCharacters;
        this.mainCharacter = mainCharacter;
        this.initialFrontPlayerCharacter = initialFrontPlayerCharacter;
        this.initialBackPlayerCharacter = initialBackPlayerCharacter;
        this.turnOrderReducer = turnOrderReducer;
        this.initialMaxMana = initialMaxMana;
        this.initialCurrentMana = initialCurrentMana;
    }

    public ArrayList<PlayerCharacterConfiguration> getPlayerCharacters() {
        return playerCharacters;
    }

    public ArrayList<EnemyCharacterConfiguration> getEnemyCharacters() {
        return enemyCharacters;
    }

    public PlayerCharacterConfiguration getMainCharacter() {
        return mainCharacter;
    }

    public PlayerCharacterConfiguration getInitialFrontPlayerCharacter() {
        return initialFrontPlayerCharacter;
    }

    public PlayerCharacterConfiguration getInitialBackPlayerCharacter() {
        return initialBackPlayerCharacter;
    }

    public int getInitialMaxMana() {
        return initialMaxMana;
    }

    public int getInitialCurrentMana() {
        return initialCurrentMana;
    }
}
