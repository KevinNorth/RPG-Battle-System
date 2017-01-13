package com.kevinnorth.rpg_console_game;


import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/// <summary>
/// Represents a row in the actions table in the database.
/// Each row represents an action in the queue that needs to
/// processed to handle players' turns.
/// </summary>
public class ActionRecord {
    /// <summary> The database ID. </summary>
    public int ID;
    /// <summary> A String used to determine which IAction
    /// implementation this action corresponds to. </summary>
    public String ActionName;
    /// <summary> The ID of the user who submitted this action.
    /// Often, IAcitons will need to check this user ID to ensure
    /// that players are only modifying their own planets, fleets,
    /// etc. </summary>
    public int UserID;
    /// <summary> A key-value pair collection that gives additional
    /// information that an action needs to be processed. For example,
    /// a move fleet action might include ths fleet to be moved and
    /// its destination as parameters. </summary>
    public Map<String, String> Parameters;
    /// <summary> The date and time that the action was first added to
    /// the database. </summary>
    public Date DateAdded;
    /// <summary> The date and time that the action daemon attempted to
    /// run this action. This should be set to null if the action daemon
    /// has never processed the action. </summary>
    public Date DateRun;
    /// <summary> A string identifying the version of Takamo that was in
    /// production when this aciton was added to the database. </summary>
    /// <remarks> This can be used to allow us to make breaking changes to
    /// how specific actions work while avoiding breaking the unprocessed
    /// actions already in the database: The obsleted IAction
    /// implementations can stay in the codebase, tagged with their old
    /// version numbers, until all of the older rows have been processed.
    /// Then, the action daemon can use the Version string on each action
    /// row to determine whether to use the old or new IAction
    /// implementation to process that row. </remarks>
    public String Version;
    /// <summary> A human-readable string that describes which major
    /// component in the Takamo infastructure originally added this action
    /// to the database. For example, the action may have came from the
    /// browser-based game client, the mobile phone game client, the action
    /// daemon adding a new action automatically in response to another
    /// action, or a different daemon. </summary>
    /// <remarks> The primary purpose of this string isn't to perform any
    /// logic within Takamo. Instead, it's to make it easier to debug the
    /// game. For example, if there's a bug where the mobile phone client
    /// tries to create action rows with incorrect information, then when
    /// actions begin failing, we will be able to quickly see that the
    /// actions are all coming from the mobile client, making it easier to
    /// check the mobile client instead of double-checking that it isn't a
    /// problem with how the actino daemon processes rows. </remarks>
    public String HowAdded;
    /// <summary> If the action was run but ran unsuccessfully, this String
    /// will include a human-readable description of the error that was
    /// encountered. Otherwise, this string should be null. </summary>
    public String Error;
    /// <summary> If this action has been processed and ran to completion
    /// successfully, this should be set to true. If the action hasn't run
    /// at all yet or was run but had an error, this should be se to false.
    /// </summary>
    public boolean Completed;
    /// <summary> A list of the Prerequisites that should be satisfied/true
    /// before the action will be run. </summary>
    public List<PrerequisiteRecord> Prerequisites;

    private static class PrerequisiteRecord {

        public PrerequisiteRecord() {
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.ID;
        hash = 79 * hash + Objects.hashCode(this.ActionName);
        hash = 79 * hash + this.UserID;
        hash = 79 * hash + Objects.hashCode(this.Parameters);
        hash = 79 * hash + Objects.hashCode(this.DateAdded);
        hash = 79 * hash + Objects.hashCode(this.DateRun);
        hash = 79 * hash + Objects.hashCode(this.Version);
        hash = 79 * hash + Objects.hashCode(this.HowAdded);
        hash = 79 * hash + Objects.hashCode(this.Error);
        hash = 79 * hash + (this.Completed ? 1 : 0);
        hash = 79 * hash + Objects.hashCode(this.Prerequisites);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ActionRecord other = (ActionRecord) obj;
        if (this.ID != other.ID) {
            return false;
        }
        if (this.UserID != other.UserID) {
            return false;
        }
        if (this.Completed != other.Completed) {
            return false;
        }
        if (!Objects.equals(this.ActionName, other.ActionName)) {
            return false;
        }
        if (!Objects.equals(this.Version, other.Version)) {
            return false;
        }
        if (!Objects.equals(this.HowAdded, other.HowAdded)) {
            return false;
        }
        if (!Objects.equals(this.Error, other.Error)) {
            return false;
        }
        if (!Objects.equals(this.Parameters, other.Parameters)) {
            return false;
        }
        if (!Objects.equals(this.DateAdded, other.DateAdded)) {
            return false;
        }
        if (!Objects.equals(this.DateRun, other.DateRun)) {
            return false;
        }
        if (!Objects.equals(this.Prerequisites, other.Prerequisites)) {
            return false;
        }
        return true;
    }

    
}
