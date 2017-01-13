package com.kevinnorth.rpg_console_game.configuration;

import com.kevinnorth.rpg_console_game.reducers.attack_reducers.AttackReducer;

public abstract class AttackConfiguration {
    public abstract AttackReducer getReducer();
}
