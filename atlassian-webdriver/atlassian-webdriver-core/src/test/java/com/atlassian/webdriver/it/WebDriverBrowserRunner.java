package com.atlassian.webdriver.it;

import com.atlassian.webdriver.testing.annotation.IgnoreBrowser;
import com.atlassian.webdriver.utils.Browser;
import com.atlassian.webdriver.utils.WebDriverUtil;
import org.junit.Ignore;
import org.junit.internal.AssumptionViolatedException;
import org.junit.internal.runners.model.EachTestNotifier;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

/**
 * A test runner that checks to see if the executing test method
 * has the {@link IgnoreBrowser} annotation and if the current running driver
 * is in the list of browsers to ignore then the test will be skipped.
 */
public class WebDriverBrowserRunner extends BlockJUnit4ClassRunner
{
    public WebDriverBrowserRunner(Class testClass) throws InitializationError
    {
        super(testClass);
    }

    // Copied from {@link BlockJUnit4ClassRunner} and extra functionality added
    // To allow ignoring of tests for specific browsers
    @Override
	protected void runChild(FrameworkMethod method, RunNotifier notifier) {
		EachTestNotifier eachNotifier= makeNotifier(method, notifier);
        IgnoreBrowser ignoreBrowser = method.getAnnotation(IgnoreBrowser.class);
        Ignore ignore = method.getAnnotation(Ignore.class);
		if (ignoreBrowser != null && ignoreBrowser.value().length > 0) {
            Browser latestBrowser = WebDriverUtil.getLatestBrowser();

            for (Browser browser : ignoreBrowser.value())
            {
                if (browser == latestBrowser || browser == Browser.ALL)
                {
                    eachNotifier.fireTestIgnored();
			        return;
                }
            }
		}
        else if (ignore != null)
        {
            eachNotifier.fireTestIgnored();
			return;
        }

		eachNotifier.fireTestStarted();
		try {
			methodBlock(method).evaluate();
		} catch (AssumptionViolatedException e) {
			eachNotifier.addFailedAssumption(e);
		} catch (Throwable e) {
			eachNotifier.addFailure(e);
		} finally {
			eachNotifier.fireTestFinished();
		}
	}

    // Copied from {@link BlockJUnit4ClassRunner}
    private EachTestNotifier makeNotifier(FrameworkMethod method,
			RunNotifier notifier) {
		Description description= describeChild(method);
		return new EachTestNotifier(notifier, description);
	}
}
