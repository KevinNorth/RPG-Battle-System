package com.kevinnorth.rpg_console_game.actions;


import com.kevinnorth.rpg_console_game.store.BattleAction;
import com.kevinnorth.rpg_console_game.store.CharacterState;

public class AttackAction extends BattleAction {
    private final CharacterState target;
    private final CharacterState attacker;

    public AttackAction(CharacterState target, CharacterState attacker) {
        this.target = target;
        this.attacker = attacker;
    }

    public CharacterState getTarget() {
        return target;
    }

    public CharacterState getAttacker() {
        return attacker;
    }
}

