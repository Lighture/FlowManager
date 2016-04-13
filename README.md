# FlowManager
Flow manager is helper class for executing multiple synchronous or asynchronous actions in set order.

Usage example:
```
FlowManagerFactory.newInstance()
          .addAction(new Action() {
              @Override
              public void execute(Flow flow) {
                  Log.d("FlowManager", "Action 1");
                  flow.nextAction();
              }
          })
          .addAction(new Action() {
              @Override
              public void execute(Flow flow) {
                  Log.d("FlowManager", "Action 2");
                  flow.nextAction();
              }
          })
          .setInterruptListener(new InterruptListener() {
              @Override
              public void onInterrupted(Throwable throwable, Flow flow) {
                  Log.e("FlowManager", "Flow interrupted", throwable);
              }
          })
          .setFinishListener(new FinishListener() {
              @Override
              public void onFinished(Flow flow) {
                  Log.d("FlowManager", "Flow finished");
              }
          })
          .start();
```

Gradle:

Add it in your root build.gradle at the end of repositories:
```
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```

Add the dependency:
```
dependencies {
        compile 'com.github.Lighture:FlowManager:0.1'
}
```
