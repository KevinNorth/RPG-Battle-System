package com.kevinnorth.rpg_battle_system.machine;

import com.kevinnorth.rpg_battle_system.store.Action;
import com.kevinnorth.rpg_battle_system.store.Reducer;
import com.kevinnorth.rpg_battle_system.store.State;
import java.util.List;

public abstract class StateMachineReducer {
    public String reduce(State oldStoreState, Action action,
            Iterable<String> availableMachineStates) {
        Reducer underlyingReducer = getUnderlyingReducer();

        State newStoreState = reduceStoreState(underlyingReducer, action,
                oldStoreState);

        return determineNextMachineState(oldStoreState, newStoreState, action,
                availableMachineStates);
    }
        
    protected abstract String determineNextMachineState(State oldStoreState,
            State newStoreState, Action action,
            Iterable<String> availableMachineStates);
    
    protected abstract State reduceStoreState(Reducer underlyingReducer,
            Action action, State oldStoreState);
            
    protected abstract Reducer getUnderlyingReducer();
}
