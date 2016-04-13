package sk.lighture.flowmanager;

import java.util.List;

import sk.lighture.flowmanager.impl.FlowManagerFactory;

/**
 * Flow manager is helper class for executing multiple synchronous or asynchronous actions in set order.<br/>
 * Method {@link FlowManager#start()} should be called only once and other state changes should be done by {@link Flow}.<br/>
 * <br/>
 * Use {@link FlowManagerFactory#newInstance()} for new instance creation
 */
public interface FlowManager {

    /**
     * Add action with builder style.
     *
     * @param action Action to add
     * @return instance of {@link FlowManager}
     */
    FlowManager addAction(Action action);

    /**
     * Add actions as varargs with builder style.
     *
     * @param actions Varargs of actions to add
     * @return instance of {@link FlowManager}
     */
    FlowManager addActions(Action... actions);

    /**
     * Add actions as list with builder style.
     *
     * @param actions List of actions to add
     * @return instance of {@link FlowManager}
     */
    FlowManager addActions(List<Action> actions);

    /**
     * Set interrupt listener that will be called when {@link Throwable} is thrown or {@link Flow#interrupt(Throwable)} is called.
     *
     * @param listener listener
     * @return instance of {@link FlowManager}
     */
    FlowManager setInterruptListener(InterruptListener listener);

    /**
     * Set finish listener that will be called after last action calls {@link Flow#nextAction()}.
     *
     * @param listener listener
     * @return instance of {@link FlowManager}
     */
    FlowManager setFinishListener(FinishListener listener);

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
     * Start execution of actions by executing the first one.<br/>
     * <b>Note:</b> you should call this method only <u>once</u>
     */
    FlowManager start();
}