package com.kevinnorth.rpg_battle_system.logic;

import com.kevinnorth.rpg_battle_system.store.State;

/**
 * Used to transition to another MachineState in the StateMachine. This class
 * uses the same pattern as the Reducers in the Store: A StaetMachineReducer
 * takes information about the current state and an action describing why the
 * StateMachine is transitioning states and outputs the next MachineState the
 * StateMachine should use. This is a roundabout way of deciding which
 * MachineState to transition to, but it allows the same MachineStates to change
 * their transitioning behaviors simply by using different StateMachineReducers
 * at runtime.
 * @param <StoreStateType> The class that the Store uses to keep track of state.
 * Using a generic type allows you to put whatever arbitrary data you wish into
 * the state as well as use different, specialized state classes in different
 * battles, but still gives you compile-time type checking to ensure that all of
 * your code is interacting with the state object correctly.
 * @param <TransitionActionType> The class that the StateMachine uses to
 * describe actions when changing the MachineState using a reducer. A generic
 * type is used for the same reasons the StoreStateType is generic.
 */
public abstract class LogicStateReducer<StoreStateType extends State,
        TransitionActionType extends LogicMachineTransitionAction> {
    /**
     * Determines which MachineState to transition to next.
     * @param oldMachineState The MachineState that the StateMachine is
     * transitioning from.
     * @param currentStoreState The current state in the Store.
     * @param action An object containing information about why the transition
     * is occurring and how the transition should happen.
     * @param availableMachineStates A collection of Strings that name each
     * MachineState that can be transitioned to. This function must return one
     * of the Strings in this collection.
     * @return The String corresponding to the MachineState you wish to
     * transition to. This must be one of the Strings in
     * <code>availableMachineStates</code>.
     */
    public abstract String reduce(String oldMachineState, 
            StoreStateType currentStoreState, TransitionActionType action,
            Iterable<String> availableMachineStates);
}
