package com.kevinnorth.rpg_battle_system.store;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * <p>An object that keeps track of the entire mutable state in the battle
 * system. If it's a number that changes, it's kept in the Store. This includes
 * information like:</p>
 * 
 * <ul>
 * <li>Each character's health</li>
 * <li>Any buffs or debuffs characters have</li>
 * <li>Characters' turn orders</li>
 * <li>Information used to control animations in response to other events</li>
 * <li>Special flags used by individual battles</li>
 * <li>Information that sophisticates computer-controlled characters use to
 * make decisions about their turns</li>
 * </ul>
 * 
 * <p>All of this information is kept in a single large State object. Except for
 * when it is constructed, a State instance is immutable. To change between
 * states, the Store uses Reducers. These are functions that take the current
 * battle State and an Action, which describes an event that occurred during the
 * battle, as inputs and outputs the new State to use.</p>
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
 * 
 * <p>In addition, any interested object can subscribe to the Store to be
 * notified whenever the State changes. Arbitrary objects can also poll the
 * Store to see what the State is if they need the State to make decisions but
 * do not need to know when it changes.</p>
 * 
 * <p>The State keeps track of a history of every State that a battle has gone
 * through over the course of its lifetime. This can be accessed and inspected
 * by any arbitrary object as well.</p>
 * @param <StateType> The class that the Store uses to keep track of state.
 * Using a generic type allows you to put whatever arbitrary data you wish into
 * the state as well as use different, specialized state classes in different
 * battles, but still gives you compile-time type checking to ensure that all of
 * your code is interacting with the state object correctly.
 * @param <ActionType> The class that the Store uses to describe actions
 * when changing the state using a reducer. A generic type is used for the same
 * reasons the StoreStateType is generic.
 */
public class Store<StateType extends State, ActionType extends Action> {
    private final Set<StoreSubscriber<StateType>> subscribers;
    private final List<StateType> stateHistory;
    private StateType currentState;
    
    /**
     * @param initialState A State describing the battle immediately after
     * starting.
     */
    public Store(StateType initialState) {
        this.subscribers = new HashSet<>();
        this.stateHistory = new LinkedList<>();
        this.currentState = initialState;
    }

    /**
     * Gets the Store's current State. This can be used for objects that wish
     * to poll the Store instead of (or in addition to) being notified of
     * changes via <code>addSubscriber()</code>.
     * @return The Store's current state.
     */
    public StateType getCurrentState() {
        return currentState;
    }
    
    /**
     * Subscribes the specified object to this Store so that any time the
     * Store's state changes, the object will have a chance to respond to the
     * changes.
     * @param subscriber The object to sbuscribe to the Store.
     */
    public void addSubscriber(StoreSubscriber<StateType> subscriber) {
        subscribers.add(subscriber);
    }
    
    /**
     * Unsubscribes the specified subscriber from updates to the Store state.
     * @param subscriber The subscriber to unsubscribe.
     * @return <code>true</code> if the subscriber was removed.
     * <code>false</code> if the subscriber wasn't already subscribed. Either
     * way, the subscriber will not receive updates after this function is
     * called.
     */
    public boolean removeSubscriber(StoreSubscriber<StateType> subscriber) {
        return subscribers.remove(subscriber);
    }
    
    /**
     * @return An immutable List that contains every State the battle has been
     * in, in the order those States occurred, with the first State appearing
     * at the front of the List.  Does not include the current State.
     */
    public List<StateType> getStateHistory() {
        return Collections.unmodifiableList(stateHistory);
    }
    
    /**
     * Uses a Reducer to change the State of the battle. In addition, all objects
     * that are subscribed to the Store will be alerted and have a chance to
     * respond to the new State before this function returns. The subscribers
     * may further change the State before this function returns.
     * @param reducer The pure function to use to decide what the next State
     * will be.
     * @param action An Action that describes the event that requires the State
     * to change.
     * @return The next State that the battle enters. (This might be identical
     * to the previous State. There will be no special indication if this is the
     * case, and even if the new State is identical to the previous State, all
     * subscribers will be notified of a state change.)
     */
    public StateType changeState(Reducer<StateType, ActionType> reducer,
            ActionType action) {
        StateType newState = reducer.reduce(action, getCurrentState());
        setCurrentState(newState);
        
        alertSubscribers();
        
        return newState;
    }
        
    private void alertSubscribers() {
        // Use a for loop instead of a stream to ensure that
        // the subscribers are updated without paralellism,
        // avoiding race conditions.
        for(StoreSubscriber subscriber : subscribers) {
            boolean didStateUpdateRecursively =
                    subscriber.recieveNewState(currentState);
            if(didStateUpdateRecursively) {
                break;
            }
        }
    }

    private void setCurrentState(StateType newState) {
        stateHistory.add(currentState);
        currentState = newState;
    }
}