package sk.lighture.flowmanager;

import java.util.List;

/**
 * Flow is control element in execution of actions.
 */
public interface Flow {

    /**
     * Starts execution of previous action
     */
    void previousAction();

    /**
     * Starts re-execution of actual action
     */
    void repeatAction();

    /**
     * Starts execution of next action
     */
    void nextAction();

    /**
     * Insert action after actual action.<br/>
     * <br/>
     * <b>Note:</b> calling this method multiple times in a row will reverse the order in which were they called.<br/>
     * For adding multiple actions in correct order, call {@link Flow#insertActions(Action...)} or {@link Flow#insertActions(List)}.
     *
     * @param action Action to insert
     */
    void insertAction(Action action);

    /**
     * Insert varargs of actions after actual action.<br/>
     *
     * @param actions Varargs of actions to insert
     */
    void insertActions(Action... actions);

    /**
     * Insert list of actions after actual action.<br/>
     *
     * @param actions List of actions to insert
     */
    void insertActions(List<Action> actions);

    /**
     * Restart state will restart action pointer to the start of flow<br/>
     * <br/>
     * <b>Note:</b> you need <u>manually</u> call {@link Flow#nextAction()} that start the execution of flow<br/>
     */
    void restartState();

    /**
     * Restore state will move current action pointer to previous action.<br/>
     * When there is no previous action, pointer stays on first one.<br/>
     * <br/>
     * <b>Note:</b> you need <u>manually</u> call methods on {@link Flow} that move the state of flow and trigger action execution e.g.<br/>
     * {@link Flow#nextAction()}<br/>
     * {@link Flow#repeatAction()}<br/>
     * {@link Flow#previousAction()}<br/>
     * <br/>
     * <b>Note:</b> calling this method and {@link Flow#nextAction()} will trigger actual action execution<br/>
     */
    void restoreState();

    /**
     * Skip state will move current action pointer to next action.<br/>
     * When there is no next action, pointer stays on last one.<br/>
     * <br/>
     * <b>Note:</b> you need <u>manually</u> call methods on {@link Flow} that move the state of flow and trigger action execution e.g.<br/>
     * {@link Flow#nextAction()}<br/>
     * {@link Flow#repeatAction()}<br/>
     * {@link Flow#previousAction()}<br/>
     * <br/>
     * <b>Note:</b> calling this method and {@link Flow#nextAction()} will skip next action and execute second action after actual<br/>
     */
    void skipState();

    /**
     * Interrupt will cause skipping the execution of next actions and calls {@link InterruptListener#onInterrupted(Throwable, Flow)}.
     *
     * @param throwable Cause of interruption
     */
    void interrupt(Throwable throwable);
}