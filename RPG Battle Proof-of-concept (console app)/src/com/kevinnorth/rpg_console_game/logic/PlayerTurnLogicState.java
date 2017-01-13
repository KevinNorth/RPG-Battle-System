package com.kevinnorth.rpg_console_game.logic;

import com.kevinnorth.rpg_battle_system.logic.LogicMachine;
import com.kevinnorth.rpg_battle_system.logic.LogicState;
import com.kevinnorth.rpg_battle_system.reciever.InputEvent;
import com.kevinnorth.rpg_console_game.configuration.BattleConfiguration;
import com.kevinnorth.rpg_console_game.store.BattleAction;
import com.kevinnorth.rpg_console_game.store.BattleState;

public class PlayerTurnLogicState
        extends LogicState<BattleState, BattleAction,
        BattleLogicMachineTransitionAction, BattleConfiguration> {

    public PlayerTurnLogicState(LogicMachine<BattleState, BattleAction, BattleLogicMachineTransitionAction, BattleConfiguration> stateMachine) {
        super(stateMachine);
    }

    @Override
    public boolean recieveNewState(BattleState newState) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void handleInput(InputEvent inputEvent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void handleFrame(float deltaTime) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
