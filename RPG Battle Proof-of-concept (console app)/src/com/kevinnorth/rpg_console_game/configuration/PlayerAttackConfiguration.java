package com.kevinnorth.rpg_console_game.configuration;

import com.kevinnorth.rpg_console_game.reducers.attack_reducers.AttackReducer;

/**
 *
 */
public final class PlayerAttackConfiguration extends AttackConfiguration {
    private final String displayName;
    
    private final AttackReducer reducer;

    public PlayerAttackConfiguration(String displayName, String asciiArt,
            AttackReducer reducer) {
        this.displayName = displayName;
        this.reducer = reducer;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public AttackReducer getReducer() {
        return reducer;
    }
}
