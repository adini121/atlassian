package com.atlassian.webdriver.it;

import com.atlassian.webdriver.WebDriverFactory;
import com.atlassian.webdriver.it.tests.IgnoreBrowser;
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
 * Simple test runner that check whether the Test class has the TestBrowser
 * annotation so that the webdriver browser system property is set before
 * the browser is launched.
 */
public class WebDriverBrowserRunner extends BlockJUnit4ClassRunner
{
    private final Class testClass;
    private String originalBrowserValue = WebDriverFactory.getBrowserProperty();

    public WebDriverBrowserRunner(Class testClass) throws InitializationError
    {
        super(testClass);
        this.testClass = testClass;
    }

    @Override
    public Description getDescription()
    {
        return super.getDescription();
    }

    @Override
    public void run(final RunNotifier notifier)
    {
        TestBrowser browserAnnotation = (TestBrowser) testClass.getAnnotation(TestBrowser.class);

        if (browserAnnotation != null)
        {
            String browserValue = browserAnnotation.value();
            System.setProperty("webdriver.browser", browserValue);
        }
        else
        {
            System.setProperty("webdriver.browser", originalBrowserValue);
        }

        super.run(notifier);
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
