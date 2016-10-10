package com.kevinnorth.rpg_battle_system;

import com.kevinnorth.rpg_battle_system.actors.Actor;
import com.kevinnorth.rpg_battle_system.machine.StateMachine;
import com.kevinnorth.rpg_battle_system.machine.StateMachineTransitionAction;
import com.kevinnorth.rpg_battle_system.renderer.Renderer;
import com.kevinnorth.rpg_battle_system.store.Action;
import com.kevinnorth.rpg_battle_system.store.Reducer;
import com.kevinnorth.rpg_battle_system.store.State;
import com.kevinnorth.rpg_battle_system.store.Store;
import com.kevinnorth.rpg_battle_system.store.StoreSubscriber;
import java.util.List;

/**
 * <p>The class responsible for controlling an entire battle sequence. It delegates
 * its responsibilities to five main subsystems:</p>
 * 
 * <ul>
 * <li>The <b>Store</b> is a state store that keeps track of all of the
 * "numbers," like player health, player order, and special flags, that keep
 * "score" of the battle. Other components can modify the state using reducers,
 * pure functions that take actions specifying what's going on in the battle and
 * the current state and output a new state.</li>
 * <li>The <b>State Machine</b> is in charge of implementing all of the rules in
 * the battle. It is implemented as a finite state machine where each state
 * knows how to enforce different pieces of logic in the battle. Different
 * scenes can specify different state machines, allowing battles to include
 * arbitrary special rules.</li>
 * <li>The <b>Actors</b> are immutable objects that keep track of configuration
 * information for each entity in the battle. This includes obvious actors, such
 * as the players and enemies, and less obvious actors, like UI elements, the
 * background, and the objects that specify interactive attack animations.</li>
 * <li>The <b>Renderer</b> is in charge of feeding information to the game
 * engine to draw to the screen, interact with the OS, save files, and so on. It
 * mostly draws to the screen and plays sound and music.</li>
 * <li>The <b>Receiver</b> receives information, like user input and time, from
 * the game engine.</li>
 * </ul>
 * 
 * <p>In addition, the Director facilitates communication between these
 * subsystems so that they have limited dependencies on each other and is in
 * charge of initializing and tearing down each battle sequence.</p>
 * @param <StoreStateType> The class that the Store uses to keep track of state.
 * Using a generic type allows you to put whatever arbitrary data you wish into
 * the state as well as use different, specialized state classes in different
 * battles, but still gives you compile-time type checking to ensure that all of
 * your code is interacting with the state object correctly.
 * @param <StoreActionType> The class that the Store uses to describe actions
 * when changing the state using a reducer. A generic type is used for the same
 * reasons the StoreStateType is generic.
 * @param <ActorType> The class used to describe an in-battle Actor. A generic
 * type is used for the same reasons the StoreStateType is generic.
 */
public class Director<StoreStateType extends State,
        StoreActionType extends Action, ActorType extends Actor> {
    private final Store<StoreStateType, StoreActionType> store;
    private final StateMachine<StoreStateType, StoreActionType,
            ? extends StateMachineTransitionAction, ActorType>
            stateMachine;
    private final Renderer<StoreStateType> renderer;

    public Director(Store<StoreStateType, StoreActionType> store,
            StateMachine<StoreStateType, StoreActionType,
                    ? extends StateMachineTransitionAction,
                    ActorType> stateMachine,
                    Renderer<StoreStateType> renderer) {
        this.store = store;
        this.stateMachine = stateMachine;
        this.renderer = renderer;
        
        store.addSubscriber(stateMachine);
    }
    
    /**
     * Call this function once per frame to update the game logic and state and
     * change what is shown on screen.
     * @param deltaTime The amount of time, in seconds, since the previous frame.
     */
    public void onFrame(float deltaTime) {
        stateMachine.handleFrame(deltaTime);
        StoreStateType currentState = store.getCurrentState();
        renderer.render(currentState, deltaTime);
    }
    
    /**
     * Uses a Reducer to change the State recorded by the Store. In addition,
     * all objects that are subscribed to the Store will be alerted and have a
     * chance to respond to the new State before this function returns. The
     * subscribers may further change the State before this function returns.
     * @param reducer The pure function to use to decide what the next State
     * will be.
     * @param action An Action that describes the event that requires the State
     * to change.
     * @return The next State that the battle enters. (This might be identical
     * to the previous State. There will be no special indication if this is the
     * case, and even if the new State is identical to the previous State, all
     * subscribers will be notified of a state change.)
     */
    public StoreStateType changeStoreState(
            Reducer<StoreStateType, StoreActionType> reducer,
            StoreActionType action) {
        return store.changeState(reducer, action);
    }
    
    /**
     * Gets the Store's current State.
     * @return The Store's current state.
     */
    public StoreStateType getStoreState() {
        return store.getCurrentState();
    }

    /**
     * @return An immutable List that contains every State the Store has been
     * in, in the order those States occurred, with the first State appearing
     * at the front of the List. Does not include the current State.
     */
    public List<StoreStateType> getStoreHistory() {
        return store.getStateHistory();
    }
    
    /**
     * Subscribes the specified object to the Store so that any time the
     * Store's state changes, the object will have a chance to respond to the
     * changes.
     * @param subscriber The object to sbuscribe to the Store.
     */
    public void addStoreSubscriber(StoreSubscriber<StoreStateType> subscriber) {
        store.addSubscriber(subscriber);
    }
    
    /**
     * Unsubscribes the specified subscriber from updates to the Store state.
     * @param subscriber The subscriber to unsubscribe.
     * @return <code>true</code> if the subscriber was removed.
     * <code>false</code> if the subscriber wasn't already subscribed. Either
     * way, the subscriber will not receive updates after this function is
     * called.
     */
    public boolean removeStoreSubscriber(
            StoreSubscriber<StoreStateType> subscriber) {
        return store.removeSubscriber(subscriber);
    }
}