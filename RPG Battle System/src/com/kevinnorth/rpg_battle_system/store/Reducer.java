/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kevinnorth.rpg_battle_system.store;

/**
 * <p>Indirectly modifies the Store state. A reducer is a pure function that takes
 * an immutable Action describing an event that updates the state of the battle
 * and an immutable State that represents the previous state of the battle. It
 * returns a new State that represents the next state of the battle.</p>
 * 
 * <p>This system is inspired by Redux, a state container library for JS web
 * development. It is certainly more complex than allowing each Actor to
 * maintain its own mutable state, but there are several advantages:</p>
 * 
 * <ul>
 * <li>The battle system can keep track of a history of every state the battle
 * was ever in, allowing for more powerful debugging tools.</li>
 * <li>Any arbitrary object in the battle system can use a reducer with
 * appropriate actions to send any arbitrary message to any other object. For
 * example, with little boilerplate, an enemy character can set a special flag
 * upon being attacked that tells a special implementation of a background
 * entity to change its animation -- all without adding any additional code to
 * the core of the battle system. In a more naive system, it would be difficult
 * to pass information between objects that weren't "logically" close to each
 * other.</li>
 * </ul>
 * @param <StateType> The class that the Store uses to keep track of state.
 * Using a generic type allows you to put whatever arbitrary data you wish into
 * the state as well as use different, specialized state classes in different
 * battles, but still gives you compile-time type checking to ensure that all of
 * your code is interacting with the state object correctly.
 * @param <ActionType> The class that the Store uses to describe actions
 * when changing the state using a reducer. A generic type is used for the same
 * reasons the StoreStateType is generic.
 */
public abstract class Reducer<StateType extends State,
        ActionType extends Action> {
    public abstract StateType
        reduce(ActionType action, StateType previousState);
}
