package rx.plugins;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class RxJavaPluginsRule implements TestRule {

    private final RxJavaPlugins instance;
    private final RxJavaSchedulersHook schedulerHook;
    private final RxJavaObservableExecutionHook executionHook;

    public RxJavaPluginsRule(RxJavaSchedulersHook schedulerHook, RxJavaObservableExecutionHook executionHook) {
        this.instance = RxJavaPlugins.getInstance();
        this.schedulerHook = schedulerHook;
        this.executionHook = executionHook;
    }

    public RxJavaPluginsRule(RxJavaSchedulersHook schedulerHook) {
        this(schedulerHook, null);
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {

                //before: plugins reset, execution and schedulers hook defined
                instance.reset();
                instance.registerErrorHandler(null);
                instance.registerObservableExecutionHook(executionHook);
                instance.registerSingleExecutionHook(null);
                instance.registerSchedulersHook(schedulerHook);

                base.evaluate();

                //after: clean up
                instance.reset();
            }
        };
    }
}
