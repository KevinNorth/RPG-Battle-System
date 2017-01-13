/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kevinnorth.rpg_battle_system.logic;

/**
 * <p>An object describing why and how a transition to a new MachineState is
 * occurring. Used as input into <code>StateMachineReducer.reduce()</code>.</p>
 *
 * <p>Subclassing this type and directing the other classes to use
 * your subclass via generics allows you to put whatever arbitrary data you wish
 * into the action as well as use different, specialized action classes in
 * different battles, but still gives you compile-time type checking to ensure
 * that all of your code is interacting with the action class correctly.</p>
 *
 * @see StateMachineReducer
 */
public abstract class LogicMachineTransitionAction {

}
