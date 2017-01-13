package com.kevinnorth.rpg_console_game.store;

import com.kevinnorth.rpg_battle_system.store.Reducer;

public abstract class BattleReducer<SpecificAction extends BattleAction>
        extends Reducer<BattleState, SpecificAction> {}
