package sk.lighture.flowmanager.impl;

import sk.lighture.flowmanager.FlowManager;

/**
 * Factory for creating new instance of {@link FlowManager}.
 */
public final class FlowManagerFactory {

    private FlowManagerFactory() {
    }

    /**
     * Create a new instance of {@link FlowManager}.
     *
     * @return Action manager instance
     */
    public static FlowManager newInstance() {
        return new FlowManagerImpl();
    }
}
