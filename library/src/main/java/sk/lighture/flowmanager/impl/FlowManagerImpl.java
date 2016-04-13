package sk.lighture.flowmanager.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sk.lighture.flowmanager.Action;
import sk.lighture.flowmanager.FlowManager;
import sk.lighture.flowmanager.FinishListener;
import sk.lighture.flowmanager.Flow;
import sk.lighture.flowmanager.InterruptListener;

class FlowManagerImpl implements FlowManager {

    private final FlowImpl flow;
    private InterruptListener interruptListener;
    private FinishListener finishListener;
    private List<Action> actions;
    private int actual;

    public FlowManagerImpl() {
        flow = new FlowImpl();
        actions = new ArrayList<>();
        restartState();
    }

    @Override
    public FlowManager addAction(Action action) {
        actions.add(action);
        return this;
    }

    @Override
    public FlowManager addActions(Action... actions) {
        return addActions(Arrays.asList(actions));
    }

    @Override
    public FlowManager addActions(List<Action> actions) {
        this.actions.addAll(actions);
        return this;
    }

    @Override
    public FlowManager setInterruptListener(InterruptListener interruptListener) {
        this.interruptListener = interruptListener;
        return this;
    }

    @Override
    public FlowManager setFinishListener(FinishListener finishListener) {
        this.finishListener = finishListener;
        return this;
    }

    @Override
    public void restartState() {
        actual = -1;
    }

    @Override
    public void restoreState() {
        checkAndSetActual(actual - 1);
    }

    @Override
    public void skipState() {
        checkAndSetActual(actual + 1);
    }

    @Override
    public FlowManager start() {
        executeNext();
        return this;
    }

    private void executeNext() {
        if (actual + 1 >= actions.size()) {
            callFinishListener();
        } else {
            execute(++actual);
        }
    }

    private void execute(int position) {
        checkAndSetActual(position);
        try {
            actions.get(actual).execute(flow);
        } catch (Throwable throwable) {
            callInterruptListener(throwable);
        }
    }

    private void callInterruptListener(Throwable throwable) {
        if (interruptListener != null) {
            interruptListener.onInterrupted(throwable, flow);
        }
    }

    private void callFinishListener() {
        if (finishListener != null) {
            finishListener.onFinished(flow);
        }
    }

    private void checkAndSetActual(int newValue) {
        if (newValue < 0) {
            newValue = 0;
        } else if (newValue >= actions.size()) {
            newValue = actions.size() - 1;
        }
        actual = newValue;
    }

    class FlowImpl implements Flow {

        @Override
        public void previousAction() {
            execute(--actual);
        }

        @Override
        public void repeatAction() {
            execute(actual);
        }

        @Override
        public void nextAction() {
            executeNext();
        }

        @Override
        public void restartState() {
            FlowManagerImpl.this.restartState();
        }

        @Override
        public void insertAction(Action action) {
            actions.add(actual + 1, action);
        }

        @Override
        public void insertActions(Action... actions) {
            insertActions(Arrays.asList(actions));
        }

        @Override
        public void insertActions(List<Action> actions) {
            FlowManagerImpl.this.actions.addAll(actual + 1, actions);
        }

        @Override
        public void restoreState() {
            FlowManagerImpl.this.restoreState();
        }

        @Override
        public void skipState() {
            FlowManagerImpl.this.skipState();
        }

        @Override
        public void interrupt(Throwable throwable) {
            FlowManagerImpl.this.callInterruptListener(throwable);
        }
    }
}