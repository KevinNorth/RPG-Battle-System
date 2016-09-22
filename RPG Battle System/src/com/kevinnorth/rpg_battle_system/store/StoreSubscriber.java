/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kevinnorth.rpg_battle_system.store;

/**
 * <p>Marks an object as being capable of receiving notifications whenever the
 * Store state changes.</p>
 * 
 * <p>To subscribe an object to a Store, have the object implement this
 * interface, then call</p>
 * 
 * <code>
 * store.addSubscriber(yourObject);
 * </code>
 * 
 * <p>on the Store you want to subscribe to.
 */
public interface StoreSubscriber {
    /**
     * <p>Called whenever a Store that this StoreSubscriber is subscribed to
     * changes its state. You can change the state again within this function,
     * but be sure to return the correct value whether you change the state
     * or not:</p>
     * 
     * <ul>
     * <li> Return <code>false</code> if you do <b>not</b> modify the state
     * before returning from this function.</li>
     * <li> Return <code>true</code> if you <b>do</b> modify the state.
     * </ul>
     * 
     * <p>Remember, to modify the state, you need to call
     * <code>store.changeState()</code> with an appropriate <code>Action</code>
     * and <code>Reducer</code> - you cannot change the state directly.</p>
     * 
     * @param newState The new State object from the Store.
     * @return <code>true</code> if you call <code>store.changeState()</code>
     * before returning. <code>false</code> otherwise. <i>Returning the
     * wrong value will lead to unpredictable bugs!</i>
     */
    public boolean recieveNewState(State newState);
}
