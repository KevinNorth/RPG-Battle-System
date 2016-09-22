package com.kevinnorth.rpg_battle_system.machine;

import com.kevinnorth.rpg_battle_system.reciever.InputEvent;
import com.kevinnorth.rpg_battle_system.store.State;

public abstract class MachineState {
    private final StateMachine stateMachine;
    
    public MachineState(StateMachine stateMachine) {
        this.stateMachine = stateMachine;
    }
    
    public abstract boolean recieveNewState(State newState);
    
    public abstract void handleInput(InputEvent inputEvent);
    
    public abstract void handleFrame(float deltaTime);

    protected StateMachine getStateMachine() {
        return stateMachine;
    }
}
