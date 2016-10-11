# RPG-Battle-System

A library for creating turn-based battles with action commands in libGDX.

The goal behind this library is simple: A turn-based battle with action sequences during turns, like Paper Mario, Mario & Luigi, or Undertale, presents an interesting architectural challenge. The code for the game's logic needs to be flexible enough to allow for any arbitrary action during a battle, but still be structured enough to enforce the rules of taking turns. This library provides what I believe is a design pattern that can answer such a game's needs.

# Installation
To use this project, download or clone it, then add it as a dependency to your Java project.

Although I'm trying to make this library as engine-agnostic as I can while still writing it in Java, I'm personally planning on using it in a LibGDX game. Once I've figured out how to add this library as a dependency to a LibGDX project, I will add more detailed configuration instructions here.

# How to Use the Library
This library is still in development. My goal is to write a short open-source RPG to prove that this library works. Once that game is complete, I will release it as an example of how to use the library and write additional documentation explaining how to learn from the game's design to provide a tutorial for using this library.

This isn't just matter of me being too lazy to write better documentation now (although that certainly is part of it ;3). I also believe that using this library in a short game will force me to iron out all of the kinks in this library's design, so anything I would write now would become obsolete quickly as I make large changes.

# Overview
This library includes six major components:

 1. The `Director`, which allows the other components to communicate with each other.
 2. The `Store`, which maintains the game's state using a state container pattern similar to [Redux](https://github.com/reactjs/redux).
 3. The `StateMachine`, which uses the [State Pattern](https://en.wikipedia.org/wiki/State_pattern) to provide a flexible way to write the game's logic.
 4. The `Configuration` classes, which store immutable information about a battle, such as which enemies and player characters are present at the beginning of the battle.
 5. The `Renderer`, which draws to the screen each frame.
 6. The `Reviecer`, which recieves input events from the game engine.

## The `Director`
The `Director` is probably the most straightforward class to use in the library: Each battle scene will have one instance of the `Director` class. Once the class is initialized, calling `Director.onFrame()` once per frame will run the game and the other classes in the library automagically.

## The `Store`
All mutable game state should be kept track of in the `Store`. This adds a significant amount of complexity to the design, but it allows the library to be much more flexible in its design. It allows the `StateMachine` to switch to any arbitrary state, running any arbitrary logic, and you can still be confident that the state in the `Store` contains all of the information you need to run the battle.

The `Store` uses a very similar pattern to [Redux](https://github.com/reactjs/redux). It contains an immutable `State` object containing all of the information representing the state of the battle at a single moment in time. When the state changes, instead of mutating the state directly, the `Store` executes a `Reducer`, an interface containing a function that takes two inputs: the current state and an `Action` containing information about why the state needs to change. The `Reducer` uses these inputs to create a new `State` object.

This is an indirect way of changing the state of the battle. I have to admit that I'm not sure at this time whether it is really necessary. As I continue to work with this library, I may rework the pattern that the `Store` uses.

## The `StateMachine`
The `StateMachine` implements the logic of your battle. It uses the [State Pattern](https://en.wikipedia.org/wiki/State_pattern) to provide flexibility in how the specific rules of your battle system are implemented.

This is the idea: In a basic game, you have a main loop that updates the game state every frame, and you also have a hook that handles user input. These two pieces codify the logic of your game. However, in an action-oriented turn-based battle, you may want to have some battles have different pieces of logic. For example, you might have a single boss battle that has some kind of card-based minigame component that would be cumbersome to fit into your main game loop just for a single battle.

To provide flexibility for such a situation, each `StateMachine` codifies the game logic for only a single phase of the turn-based battle. For instance, you might have one state for the player's turn, a second state for attack animations and action commands, and a third state for the enemy's turn. Each state only needs to worry about implementing the logic required for its own concerns. The player turn state, for instance, doesn't need to worry about handling the timing of an action command or moving the camera to an enemy as it decides what to do - instead, the player state simply trusts that the other states will perform their logic correctly when the time comes.

Furthermore, the finite state machine representing an individual battle's State Pattern implementation can be determined at runtime! To return to the example of the card minigame above, you can set your game up so that it only loads the card minigame state during the relevant boss fight. Not only does this allow you to isolate the card minigame in its own class, avoiding creating a god class that handles all possible scenarios, but it even allows you to avoid loading the card minigame logic into memory until you get to the fight that requires it. Another use case is, if you have a battle that has a mid-battle cutscene that is skipped in certain circumstances, you can determine at runtime whether the cutscene will be played and omit loading the cutscene state into memory if it's not needed.

## The `Configuration`
This is a very simple component. Each `Director` has a `Configuration` object. The `Configuration` class is simply an empty abstract class - you can put whatever information you want into it, and, using generics, all other classes in the library that need to query the `Configuration` will be able to do so freely.

Use the `Configuration` to record static information about the battle. For example, you can include each character's list of attacks and those attacks' properties in the `Configuration`. You can also include more complicated objects, such as objects describing attack animations and the inputs the player is expected to provide in order to correctly block those attacks, in the `Configuration`. You are responsible for ensuring that the other classes, such as the `StateMachine`, know how to configure those objects.

## The `Renderer`
The `Renderer` is simply an interface with a single method, `public abstract void render()`. This function is guaranteed to be called once per frame and should be used to draw to the screen, play sound affects, and provide whatever other forms of output. The `Renderer` is kept simple in this library so that you can freely use whatever frontend library or engine you wish with this library.

Feel free to keeo mutable state about the game's presentation in the `Renderer`. It's best to keep mutable information about how the battle is going in the `Store`, however. For example, which frame the main character's animation is in should be kept track of in the `Renderer`, but how much health the main character has left should be in the `Store`.

## The `Reciever`
Like the `Renderer`, the `Reciever` is a very thin interface that allows you to use whatever library you wish for getting input from the player.
