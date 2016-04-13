package sk.lighture.flowmanager;

import org.junit.Test;

import java.util.Arrays;

import sk.lighture.flowmanager.impl.FlowManagerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class FlowManagerUnitTest {

    int counter = 0;

    @Test
    public void correct_addOneAction_executedFinished() throws Exception {
        FlowManagerFactory.newInstance().addAction(new Action() {
            @Override
            public void execute(Flow flow) {
                assertNotNull(flow);
                assertEquals(0, counter);
                counter++;
                flow.nextAction();
            }
        }).setInterruptListener(new InterruptListener() {
            @Override
            public void onInterrupted(Throwable throwable, Flow flow) {
                fail();
            }
        }).setFinishListener(new FinishListener() {
            @Override
            public void onFinished(Flow flow) {
                assertNotNull(flow);
                assertEquals(1, counter);
                counter++;
            }
        }).start();
        assertEquals(2, counter);
    }

    @Test
    public void correct_addOneActionsArray_executedFinished() throws Exception {
        FlowManagerFactory.newInstance().addActions(new Action() {
            @Override
            public void execute(Flow flow) {
                assertNotNull(flow);
                assertEquals(0, counter);
                counter++;
                flow.nextAction();
            }
        }).setInterruptListener(new InterruptListener() {
            @Override
            public void onInterrupted(Throwable throwable, Flow flow) {
                fail();
            }
        }).setFinishListener(new FinishListener() {
            @Override
            public void onFinished(Flow flow) {
                assertNotNull(flow);
                assertEquals(1, counter);
                counter++;
            }
        }).start();
        assertEquals(2, counter);
    }

    @Test
    public void correct_addOneActionsList_executedFinished() throws Exception {
        FlowManagerFactory.newInstance().addActions(Arrays.asList(new Action[]{
                new Action() {
                    @Override
                    public void execute(Flow flow) {
                        assertNotNull(flow);
                        assertEquals(0, counter);
                        counter++;
                        flow.nextAction();
                    }
                }
        })).setInterruptListener(new InterruptListener() {
            @Override
            public void onInterrupted(Throwable throwable, Flow flow) {
                fail();
            }
        }).setFinishListener(new FinishListener() {
            @Override
            public void onFinished(Flow flow) {
                assertNotNull(flow);
                assertEquals(1, counter);
                counter++;
            }
        }).start();
        assertEquals(2, counter);
    }

    @Test
    public void correct_addMoreAction_executedFinished() throws Exception {
        FlowManagerFactory.newInstance().addAction(new Action() {
            @Override
            public void execute(Flow flow) {
                assertEquals(0, counter);
                counter++;
                flow.nextAction();
            }
        }).addAction(new Action() {
            @Override
            public void execute(Flow flow) {
                assertEquals(1, counter);
                counter++;
                flow.nextAction();
            }
        }).setInterruptListener(new InterruptListener() {
            @Override
            public void onInterrupted(Throwable throwable, Flow flow) {
                fail();
            }
        }).setFinishListener(new FinishListener() {
            @Override
            public void onFinished(Flow flow) {
                assertEquals(2, counter);
                counter++;
            }
        }).start();
        assertEquals(3, counter);
    }

    @Test
    public void correct_addMoreActionsArray_executedFinished() throws Exception {
        FlowManagerFactory.newInstance().addActions(new Action() {
            @Override
            public void execute(Flow flow) {
                assertEquals(0, counter);
                counter++;
                flow.nextAction();
            }
        }, new Action() {
            @Override
            public void execute(Flow flow) {
                assertEquals(1, counter);
                counter++;
                flow.nextAction();
            }
        }).setInterruptListener(new InterruptListener() {
            @Override
            public void onInterrupted(Throwable throwable, Flow flow) {
                fail();
            }
        }).setFinishListener(new FinishListener() {
            @Override
            public void onFinished(Flow flow) {
                assertEquals(2, counter);
                counter++;
            }
        }).start();
        assertEquals(3, counter);
    }

    @Test
    public void correct_addMoreActionsList_executedFinished() throws Exception {
        FlowManagerFactory.newInstance().addActions(Arrays.asList(new Action[]{
                new Action() {
                    @Override
                    public void execute(Flow flow) {
                        assertEquals(0, counter);
                        counter++;
                        flow.nextAction();
                    }
                },
                new Action() {
                    @Override
                    public void execute(Flow flow) {
                        assertEquals(1, counter);
                        counter++;
                        flow.nextAction();
                    }
                }
        })).setInterruptListener(new InterruptListener() {
            @Override
            public void onInterrupted(Throwable throwable, Flow flow) {
                fail();
            }
        }).setFinishListener(new FinishListener() {
            @Override
            public void onFinished(Flow flow) {
                assertNotNull(flow);
                assertEquals(2, counter);
                counter++;
            }
        }).start();
        assertEquals(3, counter);
    }

    @Test
    public void negative_interruptedFirstAction_executedInterruped() throws Exception {
        FlowManagerFactory.newInstance().addAction(new Action() {
            @Override
            public void execute(Flow flow) {
                assertEquals(0, counter);
                counter++;
                flow.interrupt(new Throwable());
            }
        }).addAction(new Action() {
            @Override
            public void execute(Flow flow) {
                fail();
            }
        }).setInterruptListener(new InterruptListener() {
            @Override
            public void onInterrupted(Throwable throwable, Flow flow) {
                assertNotNull(flow);
                assertNotNull(throwable);
                assertEquals(1, counter);
                counter++;
            }
        }).setFinishListener(new FinishListener() {
            @Override
            public void onFinished(Flow flow) {
                fail();
            }
        }).start();
        assertEquals(2, counter);
    }

    @Test
    public void negative_interruptedSecondAction_executedInterruped() throws Exception {
        FlowManagerFactory.newInstance().addAction(new Action() {
            @Override
            public void execute(Flow flow) {
                assertEquals(0, counter);
                counter++;
                flow.nextAction();
            }
        }).addAction(new Action() {
            @Override
            public void execute(Flow flow) {
                assertEquals(1, counter);
                counter++;
                flow.interrupt(new Throwable());
            }
        }).addAction(new Action() {
            @Override
            public void execute(Flow flow) {
                fail();
            }
        }).setInterruptListener(new InterruptListener() {
            @Override
            public void onInterrupted(Throwable throwable, Flow flow) {
                assertNotNull(flow);
                assertNotNull(throwable);
                assertEquals(2, counter);
                counter++;
            }
        }).setFinishListener(new FinishListener() {
            @Override
            public void onFinished(Flow flow) {
                fail();
            }
        }).start();
        assertEquals(3, counter);
    }

    @Test
    public void negative_exceptionThrownFirstAction_executedInterruped() throws Exception {
        FlowManagerFactory.newInstance().addAction(new Action() {
            @Override
            public void execute(Flow flow) {
                assertEquals(0, counter);
                counter++;
                throw new RuntimeException();
            }
        }).addAction(new Action() {
            @Override
            public void execute(Flow flow) {
                fail();
            }
        }).setInterruptListener(new InterruptListener() {
            @Override
            public void onInterrupted(Throwable throwable, Flow flow) {
                assertNotNull(flow);
                assertTrue(throwable instanceof RuntimeException);
                assertEquals(1, counter);
                counter++;
            }
        }).setFinishListener(new FinishListener() {
            @Override
            public void onFinished(Flow flow) {
                fail();
            }
        }).start();
        assertEquals(2, counter);
    }

    @Test
    public void negative_exceptionThrownSecondAction_executedInterruped() throws Exception {
        FlowManagerFactory.newInstance().addAction(new Action() {
            @Override
            public void execute(Flow flow) {
                assertEquals(0, counter);
                counter++;
                flow.nextAction();
            }
        }).addAction(new Action() {
            @Override
            public void execute(Flow flow) {
                assertEquals(1, counter);
                counter++;
                throw new RuntimeException();
            }
        }).addAction(new Action() {
            @Override
            public void execute(Flow flow) {
                fail();
            }
        }).setInterruptListener(new InterruptListener() {
            @Override
            public void onInterrupted(Throwable throwable, Flow flow) {
                assertNotNull(flow);
                assertTrue(throwable instanceof RuntimeException);
                assertEquals(2, counter);
                counter++;
            }
        }).setFinishListener(new FinishListener() {
            @Override
            public void onFinished(Flow flow) {
                fail();
            }
        }).start();
        assertEquals(3, counter);
    }

    @Test
    public void correct_previousRepeatNextAction_executedFinished() throws Exception {
        FlowManagerFactory.newInstance().addAction(new Action() {
            @Override
            public void execute(Flow flow) {
                if (counter == 0) {
                    counter++;
                    flow.nextAction();
                } else if (counter == 2) {
                    counter++;
                    flow.repeatAction();
                } else if (counter == 3) {
                    counter++;
                    flow.nextAction();
                } else {
                    fail();
                }

            }
        }).addAction(new Action() {
            @Override
            public void execute(Flow flow) {
                if (counter == 1) {
                    counter++;
                    flow.previousAction();
                } else if (counter == 4) {
                    counter++;
                    flow.nextAction();
                } else {
                    fail();
                }
            }
        }).setInterruptListener(new InterruptListener() {
            @Override
            public void onInterrupted(Throwable throwable, Flow flow) {
                fail();
            }
        }).setFinishListener(new FinishListener() {
            @Override
            public void onFinished(Flow flow) {
                assertEquals(5, counter);
                counter++;
            }
        }).start();
        assertEquals(6, counter);
    }

    @Test
    public void correct_restoreSkipState_executedFinished() throws Exception {
        FlowManagerFactory.newInstance().addAction(new Action() {
            @Override
            public void execute(Flow flow) {
                flow.skipState();
                counter++;
                flow.nextAction();
            }
        }).addAction(new Action() {
            @Override
            public void execute(Flow flow) {
                fail();
            }
        }).addAction(new Action() {
            @Override
            public void execute(Flow flow) {
                counter++;
                flow.nextAction();
            }
        }).addAction(new Action() {
            @Override
            public void execute(Flow flow) {
                if (counter == 2) {
                    counter++;
                    flow.restoreState();
                    flow.restoreState();
                    flow.nextAction();
                } else if (counter == 4) {
                    counter++;
                    flow.nextAction();
                } else {
                    fail();
                }
            }
        }).setInterruptListener(new InterruptListener() {
            @Override
            public void onInterrupted(Throwable throwable, Flow flow) {
                fail();
            }
        }).setFinishListener(new FinishListener() {
            @Override
            public void onFinished(Flow flow) {
                assertEquals(5, counter);
                counter++;
            }
        }).start();
        assertEquals(6, counter);
    }

    @Test
    public void correct_restoreSkipEdgeCaseState_executedFinished() throws Exception {
        FlowManagerFactory.newInstance().addAction(new Action() {
            @Override
            public void execute(Flow flow) {
                if (counter == 0) {
                    counter++;
                    flow.restoreState();
                    flow.restoreState();
                    flow.repeatAction();
                } else if (counter == 1) {
                    counter++;
                    flow.nextAction();
                } else {
                    fail();
                }
            }
        }).addAction(new Action() {
            @Override
            public void execute(Flow flow) {
                counter++;
                flow.skipState();
                flow.skipState();
                flow.nextAction();
            }
        }).setInterruptListener(new InterruptListener() {
            @Override
            public void onInterrupted(Throwable throwable, Flow flow) {
                fail();
            }
        }).setFinishListener(new FinishListener() {
            @Override
            public void onFinished(Flow flow) {
                assertEquals(3, counter);
                counter++;
            }
        }).start();
        assertEquals(4, counter);
    }

    @Test
    public void correct_restart_executedFinished() throws Exception {
        FlowManagerFactory.newInstance()
                .addAction(new Action() {
                    @Override
                    public void execute(Flow flow) {
                        if (counter == 0) {
                            counter++;
                            flow.nextAction();
                        } else if (counter == 3) {
                            counter++;
                            flow.nextAction();
                        } else {
                            fail();
                        }
                    }
                })
                .addAction(new Action() {
                    @Override
                    public void execute(Flow flow) {
                        if (counter == 1) {
                            counter++;
                            flow.nextAction();
                        } else if (counter == 4) {
                            counter++;
                            flow.nextAction();
                        } else {
                            fail();
                        }
                    }
                })
                .setInterruptListener(new InterruptListener() {
                    @Override
                    public void onInterrupted(Throwable throwable, Flow flow) {
                        fail();
                    }
                })
                .setFinishListener(new FinishListener() {
                    @Override
                    public void onFinished(Flow flow) {
                        if (counter == 2) {
                            counter++;
                            flow.restartState();
                            flow.nextAction();
                        } else if (counter == 5) {
                            counter++;
                        } else {
                            fail();
                        }
                    }
                }).start();
        assertEquals(6, counter);
    }

    @Test
    public void correct_insertAction_executedFinished() throws Exception {
        FlowManagerFactory.newInstance().addAction(new Action() {
            @Override
            public void execute(Flow flow) {
                assertEquals(0, counter);
                counter++;
                flow.insertAction(new Action() {
                    @Override
                    public void execute(Flow flow) {
                        assertEquals(1, counter);
                        counter++;
                        flow.nextAction();
                    }
                });
                flow.nextAction();
            }
        }).addAction(new Action() {
            @Override
            public void execute(Flow flow) {
                assertEquals(2, counter);
                counter++;
                flow.nextAction();
            }
        }).setInterruptListener(new InterruptListener() {
            @Override
            public void onInterrupted(Throwable throwable, Flow flow) {
                fail();
            }
        }).setFinishListener(new FinishListener() {
            @Override
            public void onFinished(Flow flow) {
                assertEquals(3, counter);
                counter++;
            }
        }).start();
        assertEquals(4, counter);
    }

    @Test
    public void correct_insertActionsArray_executedFinished() throws Exception {
        FlowManagerFactory.newInstance().addAction(new Action() {
            @Override
            public void execute(Flow flow) {
                assertEquals(0, counter);
                counter++;
                flow.insertActions(new Action() {
                    @Override
                    public void execute(Flow flow) {
                        assertEquals(1, counter);
                        counter++;
                        flow.nextAction();
                    }
                }, new Action() {
                    @Override
                    public void execute(Flow flow) {
                        assertEquals(2, counter);
                        counter++;
                        flow.nextAction();
                    }
                });
                flow.nextAction();
            }
        }).setInterruptListener(new InterruptListener() {
            @Override
            public void onInterrupted(Throwable throwable, Flow flow) {
                fail();
            }
        }).setFinishListener(new FinishListener() {
            @Override
            public void onFinished(Flow flow) {
                assertEquals(3, counter);
                counter++;
            }
        }).start();
        assertEquals(4, counter);
    }

    @Test
    public void correct_insertActionsList_executedFinished() throws Exception {
        FlowManagerFactory.newInstance().addAction(new Action() {
            @Override
            public void execute(Flow flow) {
                assertEquals(0, counter);
                counter++;
                flow.insertActions(Arrays.asList(new Action[]{
                        new Action() {
                            @Override
                            public void execute(Flow flow) {
                                assertEquals(1, counter);
                                counter++;
                                flow.nextAction();
                            }
                        }
                }));
                flow.nextAction();
            }
        }).setInterruptListener(new InterruptListener() {
            @Override
            public void onInterrupted(Throwable throwable, Flow flow) {
                fail();
            }
        }).setFinishListener(new FinishListener() {
            @Override
            public void onFinished(Flow flow) {
                assertEquals(2, counter);
                counter++;
            }
        }).start();
        assertEquals(3, counter);
    }

    @Test
    public void correct_insertMoreAction_executedFinished() throws Exception {
        FlowManagerFactory.newInstance().addAction(new Action() {
            @Override
            public void execute(Flow flow) {
                assertEquals(0, counter);
                counter++;
                flow.insertAction(new Action() {
                    @Override
                    public void execute(Flow flow) {
                        assertEquals(2, counter);
                        counter++;
                        flow.nextAction();
                    }
                });
                flow.insertAction(new Action() {
                    @Override
                    public void execute(Flow flow) {
                        assertEquals(1, counter);
                        counter++;
                        flow.nextAction();
                    }
                });
                flow.nextAction();
            }
        }).setInterruptListener(new InterruptListener() {
            @Override
            public void onInterrupted(Throwable throwable, Flow flow) {
                fail();
            }
        }).setFinishListener(new FinishListener() {
            @Override
            public void onFinished(Flow flow) {
                assertEquals(3, counter);
                counter++;
            }
        }).start();
        assertEquals(4, counter);
    }

}