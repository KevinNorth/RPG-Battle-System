/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kevinnorth.rpg_battle_system.logic;

/**
 * Indicates that a StateMachine attempted to switch to an invalid MachineState.
 */
public class MissingLogicStateException extends RuntimeException {
    public MissingLogicStateException(String string) {
    }
}
