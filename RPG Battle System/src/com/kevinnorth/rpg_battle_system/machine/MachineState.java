package com.kevinnorth.rpg_battle_system.machine;

import com.kevinnorth.rpg_battle_system.reciever.InputEvent;
import com.kevinnorth.rpg_battle_system.store.Action;
import com.kevinnorth.rpg_battle_system.store.Reducer;
import com.kevinnorth.rpg_battle_system.store.State;

/**
 * <p>A state in the StateMachine finite state machine used to control a battle's
 * rules.</p>
 * 
 * <p>This class provides several handles that are guaranteed to be called at
 * certain points in time. This allows the MachineState to respond to events to
 * implement different rules.</p>
 * 
 * <p>Although MachineStates can keep their own internal states, we strongly
 * recommend against that. Instead, MachineStates should enforce their rules by
 * modifying the Store state and changing to other MachineStates. Remember, the
 * Store provides a rich set of mutable information and can be used to set
 * special flags, which you can set to control the behavior of the same
 * MachineState later on.</p>
 * 
 * <p>Here are a couple examples of how a StateMachine can be used to implement
 * rules:</p>
 * 
 * <ul>
 * <li>While the player is deciding which action to take on their turn, the
 * MachineState can keep track of the state of the user interface and provide
 * the logic to handle user input like menu selections. Once the player has
 * made a decision, the machine state can transition to a new machine state
 * depending on the player's choice - perhaps an animation to show the use of an
 * item, or an attack sequence to give the player an action minigame to control
 * their attack.</li>
 * <li>During an attack animation, a machine state can provide logic for an
 * action minigame. It will query the Actors to get an object describing the
 * attack animation, perform logic each frame to determine the precise moments
 * to sent Actions and Reducers to the Store to apply damage and other effects,
 * and process user input to determine how the player affected the result of the
 * attack. Once the attack animation is finished, it can transition to the
 * next machine state. It can easily do so dynamically, choosing between going
 * to another character's turn, showing a cutscene, ending the battle, or doing
 * something else.</li>
 * </ul>
 * 
 * <p>As you can see, implementing a MachineState can be a difficult task. These
 * classes, perhaps more than any others in this library, will determine how
 * your game plays!</p>
 */
public abstract class MachineState {
    private final StateMachine stateMachine;
    
    /**
     * @param stateMachine The StateMachine that this MachineState belongs to.
     */
    public MachineState(StateMachine stateMachine) {
        this.stateMachine = stateMachine;
    }

    /**
     * @return A list of the Strings that can be used to identify and transition
     * between MachineStates in this particular instance of StateMachine.
     */
    public final Iterable<String> getAvailableStateNames() {
        return stateMachine.getAvailableStateNames();
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
    protected final void changeMachineState(StateMachineReducer reducer,
            StateMachineTransitionAction action) {
        stateMachine.changeMachineState(reducer, action);
    }

    /**
     * <p>Changes MachineState to the MachineState corresponding to one of the
     * MachineStates corresponding to one of the Strings returned by
     * <code>getAvailableStateNames()</code>. This can be used to transition
     * between MachineStates with less overhead than the other implementation of
     * <code>changeMachineState()</code>.</p>
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
    protected final void changeMachineState(String newStateName) {
        stateMachine.changeMachineState(newStateName);
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
    protected final State changeStoreState(Reducer reducer, Action action) {
        return stateMachine.changeStoreState(reducer, action);
    }
    
    /**
     * Gives the MachineState a chance to respond whenever the Store's state
     * changes.
     * @param newState The new Store State.
     * @return <code>true</code> if you call <code>store.changeState()</code>
     * before returning. <code>false</code> otherwise. <i>Returning the
     * wrong value will lead to unpredictable bugs!</i>
     */
    public abstract boolean recieveNewState(State newState);
    
    /**
     * Gives the MachineState a change to respond to user input.
     * @param inputEvent An object describing the user's input.
     */
    public abstract void handleInput(InputEvent inputEvent);

    /**
     * Called once per frame.
     * @param deltaTime The amount of time, in seconds, that has passed since
     * the last time handleFrame() was called.
     */
    public abstract void handleFrame(float deltaTime);
}
