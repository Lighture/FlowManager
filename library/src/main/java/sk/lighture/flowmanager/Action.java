package sk.lighture.flowmanager;

/**
 * Action represent logical unit of execution.
 */
public interface Action {

    /**
     * Method that is called when action is triggered.<br/>
     * <br/>
     * <b>Note:</b> you need <u>manually</u> call methods on {@link Flow} that move the state of flow e.g.<br/>
     * {@link Flow#nextAction()}<br/>
     * {@link Flow#repeatAction()}<br/>
     * {@link Flow#previousAction()}<br/>
     *
     * @param flow Actual flow
     */
    void execute(Flow flow);
}