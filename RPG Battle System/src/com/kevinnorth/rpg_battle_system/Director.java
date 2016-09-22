package com.kevinnorth.rpg_battle_system;

import com.kevinnorth.rpg_battle_system.machine.StateMachine;
import com.kevinnorth.rpg_battle_system.store.State;
import com.kevinnorth.rpg_battle_system.store.Store;
import com.kevinnorth.rpg_battle_system.store.StoreSubscriber;
import java.util.List;

public class Director {
    private final Store store;
    private final StateMachine stateMachine;

    public Director(Store store, StateMachine stateMachine) {
        this.store = store;
        this.stateMachine = stateMachine;
        
        store.addSubscriber(stateMachine);
    }    
    
    public State getStoreState() {
        return store.getCurrentState();
    }

    public List<State> getStoreHistory() {
        return store.getStateHistory();
    }
    
    public void addStoreSubscriber(StoreSubscriber subscriber) {
        store.addSubscriber(subscriber);
    }
    
    public void removeStoreSubscriber(StoreSubscriber subscriber) {
        store.addSubscriber(subscriber);
    }
}