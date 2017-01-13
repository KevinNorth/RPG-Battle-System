package com.kevinnorth.rpg_console_game.logic;

import com.kevinnorth.rpg_battle_system.logic.LogicMachine;
import com.kevinnorth.rpg_battle_system.logic.LogicState;
import com.kevinnorth.rpg_battle_system.reciever.InputEvent;
import com.kevinnorth.rpg_console_game.actions.AttackAction;
import com.kevinnorth.rpg_console_game.configuration.AttackConfiguration;
import com.kevinnorth.rpg_console_game.configuration.BattleConfiguration;
import com.kevinnorth.rpg_console_game.reducers.attack_reducers.AttackReducer;
import com.kevinnorth.rpg_console_game.store.BattleAction;
import com.kevinnorth.rpg_console_game.store.BattleState;
import com.kevinnorth.rpg_console_game.store.CharacterState;

public class AttackLogicState
        extends LogicState<BattleState, BattleAction,
        BattleLogicMachineTransitionAction, BattleConfiguration> {
    private final CharacterState target;
    private final CharacterState attacker;
    private final AttackConfiguration attack;

    public AttackLogicState(LogicMachine<BattleState, BattleAction, BattleLogicMachineTransitionAction, BattleConfiguration> stateMachine,
            CharacterState target, CharacterState attacker, AttackConfiguration attack) {
        super(stateMachine);
        this.target = target;
        this.attacker = attacker;
        this.attack = attack;
    }

    @Override
    public boolean recieveNewState(BattleState newState) {
        return false;
    }

    @Override
    public void handleInput(InputEvent inputEvent) { }

    @Override
    public void handleFrame(float deltaTime) {
        AttackReducer attackReducer = attack.getReducer();
        AttackAction attackAction = new AttackAction(target, attacker);
        
        changeStoreState(attackReducer, attackAction);
        
        
    }
}

