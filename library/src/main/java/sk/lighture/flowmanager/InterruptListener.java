package sk.lighture.flowmanager;

/**
 * InterruptListener is used as callback after any action is interrupted.
 */
public interface InterruptListener {

    /**
     * Is called when execution of any action is interrupted.<br/>
     * <br/>
     * <b>Note:</b> is possible to call all methods for flow interaction
     *
     * @param throwable Cause of interruption
     * @param flow      Actual flow
     */
    void onInterrupted(Throwable throwable, Flow flow);
}
