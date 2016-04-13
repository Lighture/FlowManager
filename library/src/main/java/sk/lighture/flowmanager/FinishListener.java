package sk.lighture.flowmanager;

/**
 * FinishListener is used as callback after all successful executions.
 */
public interface FinishListener {

    /**
     * Is called when executions of actions was successful.<br/>
     * <br/>
     * <b>Note:</b> is possible to call all methods for flow interaction
     *
     * @param flow Actual flow
     */
    void onFinished(Flow flow);
}
