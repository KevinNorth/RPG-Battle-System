package com.kevinnorth.rpg_console_game.store;

import com.kevinnorth.rpg_battle_system.store.State;
import java.util.ArrayList;

public class BattleState extends State {
    private final ArrayList<PlayerCharacterState> playerCharacters;
    private final ArrayList<EnemyCharacterState> enemyCharacters;
    
    private PlayerCharacterState frontPlayer;
    private PlayerCharacterState backPlayer;
    private PlayerCharacterState mainCharacter;
    
    private CharacterState currentCharacter;
    
    private int maxMana;
    private int currentMana;

    public BattleState(ArrayList<PlayerCharacterState> playerCharacters,
            ArrayList<EnemyCharacterState> enemyCharacters,
            CharacterState startingCharacter,
            int maxMana, int currentMana) {
        this.playerCharacters = playerCharacters;
        this.enemyCharacters = enemyCharacters;
        this.currentCharacter = startingCharacter;
        this.maxMana = maxMana;
        this.currentMana = currentMana;
    }

    public ArrayList<PlayerCharacterState> getPlayerCharacters() {
        return playerCharacters;
    }

    public ArrayList<EnemyCharacterState> getEnemyCharacters() {
        return enemyCharacters;
    }

    public CharacterState getCurrentCharacter() {
        return currentCharacter;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public int getCurrentMana() {
        return currentMana;
    }

    public void setCurrentCharacter(CharacterState currentCharacter) {
        this.currentCharacter = currentCharacter;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    public void setCurrentMana(int currentMana) {
        this.currentMana = currentMana;
    }
}
