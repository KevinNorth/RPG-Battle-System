package com.kevinnorth.rpg_battle_system.renderer;

/**
 * The interface responsible for drawing to the screen, playing sounds, etc.
 * This interface is very open-ended in order to allow you to use whatever
 * tools for drawing to the screen and playing sounds you wish.
 * @param <StoreStateType> The State type used by the Store.
 */
public interface Renderer<StoreStateType> {
    /**
     * Called once per frame to allow the game to draw itself.
     * @param currentState The State in the Store as of when this function is
     * called.
     * @param deltaTime The amount of time, in seconds, that has passed since
     * the previous frame.
     */
    public abstract void render(StoreStateType currentState, float deltaTime);
}
