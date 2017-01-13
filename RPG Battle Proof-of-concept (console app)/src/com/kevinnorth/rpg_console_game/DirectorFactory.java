package com.kevinnorth.rpg_console_game;

import com.kevinnorth.rpg_battle_system.Director;
import com.kevinnorth.rpg_battle_system.logic.LogicMachine;
import com.kevinnorth.rpg_battle_system.renderer.Renderer;
import com.kevinnorth.rpg_battle_system.store.Store;

public abstract class DirectorFactory {
    public abstract Director createDirector();
    
    public static class IMPLEMENTATION extends DirectorFactory {
        private final Store store;
        private final LogicMachine stateMachine;
        private final Renderer renderer;

        public IMPLEMENTATION(Store store, LogicMachine stateMachine, Renderer renderer) {
            this.store = store;
            this.stateMachine = stateMachine;
            this.renderer = renderer;
        }
        
        @Override
        public Director createDirector() {
            return new Director(store, stateMachine, renderer);
        }
    }
}
