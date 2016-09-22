package com.kevinnorth.rpg_battle_system.machine;

import com.kevinnorth.rpg_battle_system.Director;
import com.kevinnorth.rpg_battle_system.reciever.InputEvent;
import com.kevinnorth.rpg_battle_system.store.Action;
import com.kevinnorth.rpg_battle_system.store.State;
import com.kevinnorth.rpg_battle_system.store.StoreSubscriber;
import java.util.Map;

/**
 * Implements the rules of the battle using a finite state machine. Each state
 * in the FSM represents a different set of rules and control flow that the
 * battle goes through. By using a state machine to go through those
 * implementations of the rules, the battle system 
 */
public class StateMachine implements StoreSubscriber {
    private final Director director;
    private MachineState currentState;
    private final Map<String, MachineState> availableStates;

    /**
     * @param director The battle's Director
     * @param currentState The MachineState to start with
     * @param availableStates A collection of all possible MachineStates that
     * the battle can enter, each associated with a short descriptive string.
     */
    public StateMachine(Director director, MachineState currentState,
            Map<String, MachineState> availableStates) {
        this.director = director;
        this.currentState = currentState;
        this.availableStates = availableStates;
    }
    
    /**
     * Gives the current MachineState a chance to respond whenever the
     * Store's state changes.
     * @param newState The new Store State.
     * @return <code>true</code> if you call <code>store.changeState()</code>
     * before returning. <code>false</code> otherwise. <i>Returning the
     * wrong value will lead to unpredictable bugs!</i>     */
    @Override
    public boolean recieveNewState(State newState) {
        return currentState.recieveNewState(newState);
    }
    
    /**
     * Forwards input events to the current MachineState.
     * @param inputEvent 
     */
    public void handleInput(InputEvent inputEvent) {
        currentState.handleInput(inputEvent);
    }
    
    /**
     * Called once per frame, allowing the current MachineState to control the
     * battle over time.
     * @param deltaTime The amount of time, in seconds, that has passed since
     * the last time handleFrame() was called.
     */
    public void handleFrame(float deltaTime) {
        currentState.handleFrame(deltaTime);
    }
    
    /**
     * <p>Changes the current MachineState. This function uses an approach
     * similar to the one used by the Store State: 
     * @param reducer
     * @param action 
     */
    void changeState(StateMachineReducer reducer, Action action) {
        State oldStoreState = director.getStoreState();
        
        String newStateName = reducer.reduce(oldStoreState, action,
                availableStates.keySet());
        
        currentState = availableStates.get(newStateName);
        
        if(currentState == null) {
            throw new MissingMachineStateException(
                    "There is no state associated with the string \""
                            + newStateName + "\".");
        }
    }
}
