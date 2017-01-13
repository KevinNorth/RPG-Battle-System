package com.kevinnorth.rpg_console_game.reducers;

import com.kevinnorth.rpg_battle_system.store.Reducer;
import com.kevinnorth.rpg_console_game.actions.GoToNextTurnAction;
import com.kevinnorth.rpg_console_game.configuration.BattleConfiguration;
import com.kevinnorth.rpg_console_game.store.BattleState;
import com.kevinnorth.rpg_console_game.store.CharacterState;
import com.kevinnorth.rpg_console_game.store.EnemyCharacterState;
import com.kevinnorth.rpg_console_game.store.PlayerCharacterState;
import java.util.ArrayList;

public class TurnOrderReducer extends Reducer<BattleState, GoToNextTurnAction> {
    private final BattleConfiguration battleConfiguration;

    public TurnOrderReducer(BattleConfiguration battleConfiguration, CharacterState currentCharacter) {
        this.battleConfiguration = battleConfiguration;
    }
    
    public BattleState reduce(GoToNextTurnAction action, BattleState previousState) {
        CharacterState currentCharacter = action.getCharacterOfPreviousTurn();
        CharacterState nextCharacter;
        
        if(currentCharacter.getConfiguration().isPlayerCharacter()) {
            ArrayList<PlayerCharacterState> playerCharacters = previousState.getPlayerCharacters();
            int position = playerCharacters.indexOf(currentCharacter);
            int nextPosition = position + 1;
            
            if(nextPosition == playerCharacters.size()) {
                nextCharacter = previousState.getEnemyCharacters().get(0);
            } else {
                nextCharacter = playerCharacters.get(nextPosition);
            }
        } else {
            ArrayList<EnemyCharacterState> enemyCharacters = previousState.getEnemyCharacters();
            int position = enemyCharacters.indexOf(currentCharacter);
            int nextPosition = position + 1;
            
            if(nextPosition == enemyCharacters.size()) {
                nextCharacter = previousState.getPlayerCharacters().get(0);
            } else {
                nextCharacter = enemyCharacters.get(nextPosition);
            }
        }
        
        previousState.setCurrentCharacter(nextCharacter);
        return previousState;
    }
}
