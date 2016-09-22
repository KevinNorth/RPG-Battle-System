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
 */
public abstract class Reducer {
    public abstract State reduce(Action action, State previousState);
}
