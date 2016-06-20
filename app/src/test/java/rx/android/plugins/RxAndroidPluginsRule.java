package rx.android.plugins;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class RxAndroidPluginsRule implements TestRule {

    private final RxAndroidPlugins instance;
    private final RxAndroidSchedulersHook schedulerHook;

    public RxAndroidPluginsRule(RxAndroidSchedulersHook schedulerHook) {
        this.instance = RxAndroidPlugins.getInstance();
        this.schedulerHook = schedulerHook;
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {

                //before: plugins reset, execution and schedulers hook defined
                instance.reset();
                instance.registerSchedulersHook(schedulerHook);

                base.evaluate();

                //after: clean up
                instance.reset();
            }
        };
    }
}
