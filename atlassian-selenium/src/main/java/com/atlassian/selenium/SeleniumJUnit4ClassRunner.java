package com.atlassian.selenium;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

/**
 * A test runner that registers a listener {@link it.com.atlassian.applinks.CaptureScreenshotListener}
 * to capture screenshots if a test fails.
 *
 * @since 2.0
 */
public class SeleniumJUnit4ClassRunner extends BlockJUnit4ClassRunner
{
    /**
     * Creates a BlockJUnit4ClassRunner to run {@code klass}
     *
     * @throws org.junit.runners.model.InitializationError if the test class is malformed.
     */
    public SeleniumJUnit4ClassRunner(Class<?> klass) throws InitializationError
    {
        super(klass);
    }

    @Override
    public void run(final RunNotifier notifier)
    {
        CaptureScreenshotListener listener = new CaptureScreenshotListener();
        notifier.addListener(listener);
        super.run(notifier);
        notifier.removeListener(listener);
    }
}
