package com.kevinnorth.rpg_console_game.configuration;

import com.kevinnorth.rpg_console_game.reducers.attack_reducers.AttackReducer;

/**
 *
 */
public final class EnemyAttackConfiguration extends AttackConfiguration {
    private final AttackReducer reducer;

    public EnemyAttackConfiguration(String displayName, String asciiArt,
            AttackReducer reducer) {
        this.reducer = reducer;
    }

    @Override
    public AttackReducer getReducer() {
        return reducer;
    }
}
