package com.kevinnorth.rpg_console_game.actions;

import com.kevinnorth.rpg_console_game.store.BattleAction;
import com.kevinnorth.rpg_console_game.store.CharacterState;

public class GoToNextTurnAction extends BattleAction {
    private final CharacterState characterOfPreviousTurn;

    public GoToNextTurnAction(CharacterState characterOfPreviousTurn) {
        this.characterOfPreviousTurn = characterOfPreviousTurn;
    }

    public CharacterState getCharacterOfPreviousTurn() {
        return characterOfPreviousTurn;
    }
}
