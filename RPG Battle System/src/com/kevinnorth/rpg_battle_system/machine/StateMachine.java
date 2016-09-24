package com.kevinnorth.rpg_battle_system.machine;

import com.kevinnorth.rpg_battle_system.Director;
import com.kevinnorth.rpg_battle_system.actors.Actor;
import com.kevinnorth.rpg_battle_system.reciever.InputEvent;
import com.kevinnorth.rpg_battle_system.store.Action;
import com.kevinnorth.rpg_battle_system.store.Reducer;
import com.kevinnorth.rpg_battle_system.store.State;
import com.kevinnorth.rpg_battle_system.store.StoreSubscriber;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Implements the rules of the battle using a finite state machine. Each state
 * in the FSM represents a different set of rules and control flow that the
 * battle goes through. By using a state machine to go through those
 * implementations of the rules, the battle system can include whatever
 * arbitrary rules you wish and can have them relate to each other in any way
 * you wish as well.</p>
 * 
 * <p>For example, some common types of rules you might encode into a single
 * MachineState include:</p>
 * 
 * <ul>
 * <li>How to handle the player's turn</li>
 * <li>How to handle enemies' turns</li>
 * <li>How to animate and respond to input during action commands</li>
 * <li>In-battle cutscenes</li>
 * <li>End-of-battle sequences</li>
 * </ul>
 * 
 * <p>Some examples of rulesets you could implement fairly easily with this
 * system that are more exotic:</p>
 * 
 * <ul>
 * <li>In-battle bonus minigames</li>
 * <li>Special attacks</li>
 * <li>In-battle time limits</li>
 * <li>Turns for a third team allied neither with the player nor the
 * enemies</li>
 * <li>Platforming minigames</li>
 * <li>Communication with a server</li>
 * <li>Moving a character from one team to another</li>
 * </ul>
 * 
 * <p>In a more naive solution, it would be easier to program a simple battle,
 * but implementing any of the exotic rulesets listed above would require
 * adding new code to an existing, brittle, and likely very large class. This
 * State Machine pattern allows you implement any new rules you wish simply by
 * writing new MachineState implementations.</p>
 * 
 * <p>Furthermore, MachineStates do not come with hardcoded transitions to other
 * states in the state machine! Instead, transitions are determined at runtime.
 * States can call one of two <code>changeState()</code> functions to transition
 * to another MachineState at any time. One immediately transitions to a
 * specific state that can be determined at runtime, and the other uses the same
 * Reducer pattern used by the Store to functionally determine which
 * MachineState to go to next.</p>
 * 
 * <p>Either way, you can not only give each battle different instances of
 * MachineStates that implement different rules - you can also have battles
 * transition between the same MachineStates in different orders. For example,
 * if you want to have a battle that is identical to most of your game's battles
 * except that it has a cutscene halfway through, the only changes to your
 * typical battle scenario would be to add a cutscene machine state and give
 * your existing machine states a new transition that goes to the cutscene at
 * the right time. This can be done using special flags in the state
 * machines themselves, using special flags in the Store, or giving your
 * machine states a different StateMachineReducer to use that knows how to go to
 * the cutscene at the right time.</p>
 * @param <StoreStateType> The class that the Store uses to keep track of state.
 * Using a generic type allows you to put whatever arbitrary data you wish into
 * the state as well as use different, specialized state classes in different
 * battles, but still gives you compile-time type checking to ensure that all of
 * your code is interacting with the state object correctly.
 * @param <StoreActionType> The class that the Store uses to describe actions
 * when changing the state using a reducer. A generic type is used for the same
 * reasons the StoreStateType is generic.
 * @param <TransitionActionType> The class that the StateMachine uses to
 * describe actions when changing the MachineState using a reducer. A generic
 * type is used for the same reasons the StoreStateType is generic.
 * @param <ActorType> The class used to describe an in-battle Actor. A generic
 * type is used for the same reasons the StoreStateType is generic.
 */
public class StateMachine<StoreStateType extends State,
        StoreActionType extends Action, 
        TransitionActionType extends StateMachineTransitionAction,
        ActorType extends Actor>
        implements StoreSubscriber<StoreStateType> {
    private final Director<StoreStateType, StoreActionType, ActorType> director;
    private MachineState<StoreStateType, StoreActionType,
            TransitionActionType, ActorType> currentState;
    private final Map<String, MachineState<StoreStateType,
            StoreActionType, TransitionActionType, ActorType>>
            availableStates;

    /**
     * @param director The battle's Director
     * @param currentState The MachineState to start with
     */
    public StateMachine(Director<StoreStateType, StoreActionType,
            ActorType> director, MachineState<StoreStateType, StoreActionType,
            TransitionActionType, ActorType> currentState) {
        this.director = director;
        this.currentState = currentState;
        this.availableStates = new HashMap<>();
    }
    
    /**
     * <p>Adds a new MachineState to the list of MachineStates this StateMachine
     * can transition to.</p>
     * 
     * <p>Each MachineState is associated with a String, effectively a name,
     * that is used at runtime to transition between different MachineStates.
     * The name you pass with this MachineState must not have been used earlier
     * for another MachineState on this StateMachine. If this precondition isn't
     * met, this method will throw an exception.</p>
     * 
     * <p>Associating each MachineState with a name and comparing Strings at
     * runtime is more dangerous than specifying an instance of a class
     * directly, but it has several benefits:</p>
     * 
     * <ul>
     * <li>The same instance of a MachineState can be reused, saving memory and
     * avoiding hitting the garbage collector frequently, especially if some
     * MachineStates have large memory footprints.</li>
     * <li>If you use names that describe MachineStates in terms of their roles
     * in a battle instead of what they are, you can change the transition graph
     * simply by associating different MachineStates with the same names. For
     * example, you might have a name "PostAttack", which occurs after an
     * AttackMachineState transitions and typically goes to the next player's
     * turn. If you wanted to insert a cutscene in a battle, you could simply
     * insert a different MachineState that determines whether going into the
     * cutscene is appropriate and name it "PostAttack" instead. This would
     * seamlessly cause your AttackMachineState to transition to the
     * pre-cutscene MachineState at runtime with little additional code.</li>
     * <li>In a similar way, you can reuse StateMachineReducers.</li>
     * </ul>
     * 
     * @param name The name to associate with the new MachineState.
     * @param machineState The MachineState to add to the StateMachine.
     */
    public void addMachineState(String name,
            MachineState<StoreStateType, StoreActionType,
                    TransitionActionType, ActorType> machineState) {
        if(availableStates.containsKey(name)) {
            throw new IllegalStateException("The name \"" + name + "\" is "
                    + "already being used for a MachinteState in this "
                    + "StateMachine. Using the same name twice would lose "
                    + "the previous MachineState.");
        }
        
        availableStates.put(name, machineState);
    }

    /**
     * @return A list of the Strings that can be used to identify and transition
     * between MachineStates in this particular instance of StateMachine.
     */
    public Iterable<String> getAvailableStateNames() {
        return availableStates.keySet();
    }
    
    /**
     * Gives the current MachineState a chance to respond whenever the
     * Store's state changes.
     * @param newState The new Store State.
     * @return <code>true</code> if you call <code>store.changeState()</code>
     * before returning. <code>false</code> otherwise. <i>Returning the
     * wrong value will lead to unpredictable bugs!</i>
     */
    @Override
    public boolean recieveNewState(StoreStateType newState) {
        return currentState.recieveNewState(newState);
    }
    
    /**
     * Forwards input events to the current MachineState.
     * @param inputEvent An object describing the user's input.
     */
    public void handleInput(InputEvent inputEvent) {
        currentState.handleInput(inputEvent);
    }
    
    /**
     * Called once per frame, allowing the current MachineState to control the
     * battle over time.
     * @param deltaTime The amount of time, in seconds, that has passed since
     * the last time handleFrame() was called.
     */
    public void handleFrame(float deltaTime) {
        currentState.handleFrame(deltaTime);
    }
    
    /**
     * <p>Changes the current MachineState. This function uses an approach
     * similar to the one used by the Store State: It takes a reducer function
     * and a description of the action that is causing the machine state
     * transition and returns the next machine state to use.</p>
     * 
     * <p>If you would like to update the Store state at the same time as
     * transitioning between MachineStates, you can call 
     * <code>changeStoreState(reducer, action)</code> from the same piece of
     * code that is changing between MachineStates. As the new MachineState may
     * begin executing some of its code immediately, you should call
     * <code>changeStoreState(reducer, action)</code> before calling
     * <code>changeMachineState(reducer, action)</code>. Bizarre race conditions
     * can occur otherwise.</p>
     * 
     * @param reducer The StateMachineReducer that contains the logic to decide
     * what the next MachineState will be.
     * @param action A StateMachineTranisitionAction that describes the reason
     * why the State Machine is transitioning.
     * @throws MissingMachineStateException If the String returned by the
     * <code>reducer</code> isn't in the collection returned by
     * <code>getAvailableStateNames()</code>.
     * 
     */
    /* This method's visibility is deliberately set to package visibility,
     * which is why it isn't prefixed by a visibility modifier. */
    void changeMachineState(
            StateMachineReducer<StoreStateType, TransitionActionType> reducer,
            TransitionActionType action) {
        StoreStateType currentStoreState = director.getStoreState();
        String currentStateString = getCurrentStateName();
                
        String newStateName = reducer.reduce(currentStateString,
                currentStoreState, action, getAvailableStateNames());
        
        changeMachineState(newStateName);
    }
    
    /**
     * <p>Changes MachineState to the MachineState corresponding to one of the
     * names used with <code>addMachineState()</code>. This can be used to
     * transition between MachineStates with less overhead than the other
     * implementation of <code>changeMachineState()</code>.</p>
     *
     * <p>If you would like to update the Store state at the same time as
     * transitioning between MachineStates, you can call 
     * <code>changeStoreState(reducer, action)</code> from the same piece of
     * code that is changing between MachineStates. As the new MachineState may
     * begin executing some of its code immediately, you should call
     * <code>changeStoreState(reducer, action)</code> before calling
     * <code>changeMachineState(reducer, action)</code>. Bizarre race conditions
     * can occur otherwise.</p>
     * @param newStateName A String corresponding to the MachineState you wish
     * to transition to. This String should have been used as the key
     * corresponding to the MachineState value in the
     * <code>availableStates</code> map originally passed to this StateMachine's
     * constructor.
     * @throws MissingMachineStateException If <code>newStateName</code>
     * isn't in the collection returned by
     * <code>getAvailableStateNames()</code>.
     */
    /* This method's visibility is deliberately set to package visibility,
     * which is why it isn't prefixed by a visibility modifier. */
    void changeMachineState(String newStateName) {
        currentState = availableStates.get(newStateName);
        
        if(currentState == null) {
            throw new MissingMachineStateException(
                    "There is no state associated with the string \""
                            + newStateName + "\".");
        }
    }
    
    /**
     * Use a Reducer to change the State of the battle. In addition, all objects
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
    /* This method's visibility is deliberately set to package visibility,
     * which is why it isn't prefixed by a visibility modifier. */
    StoreStateType changeStoreState(
            Reducer<StoreStateType, StoreActionType> reducer,
            StoreActionType action) {
        return director.changeStoreState(reducer, action);
    }
    
    private String getCurrentStateName() {
        for(Map.Entry<String, MachineState<StoreStateType, StoreActionType,
                TransitionActionType, ActorType>> namedMachineState
                : availableStates.entrySet()) {
            if(namedMachineState.getValue() == currentState) {
                return namedMachineState.getKey();
            }
        }
        
        throw new IllegalStateException("Could not find a String matching the "
        + "current state in the availableStates map. Did you forcably switch "
        + "to a MachineState not initially passed into StateMachine as an "
        + "available state? If so, that is not allowed!");
    }
}
