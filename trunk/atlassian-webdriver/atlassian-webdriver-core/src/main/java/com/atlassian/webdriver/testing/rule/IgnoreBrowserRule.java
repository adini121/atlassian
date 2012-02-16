package com.atlassian.webdriver.testing.rule;

import com.atlassian.pageobjects.Browser;
import com.atlassian.pageobjects.util.BrowserUtil;
import com.atlassian.webdriver.WebDriverFactory;
import com.atlassian.webdriver.testing.annotation.IgnoreBrowser;
import com.atlassian.webdriver.testing.annotation.TestBrowser;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assume.assumeThat;

/**
 * A rule that allows annotating test methods with the {@link IgnoreBrowser}
 * annotation and will skip the test if the current running driver
 * is in the list of browsers to ignore. Also skips tests annotated with {@link TestBrowser} since they require a
 * particular browser.
 * <p />
 * Requires surefire 2.7.2 or higher to show skipped tests in test results.
 * @since 2.1.0
 */
public class IgnoreBrowserRule implements MethodRule
{
    private static final Logger log = LoggerFactory.getLogger(IgnoreBrowserRule.class);

    public Statement apply(final Statement base, final FrameworkMethod method, Object target)
    {
        return new Statement()
        {
            @Override
            public void evaluate() throws Throwable
            {
                Class<?> clazz = method.getMethod().getDeclaringClass();
                Package pkg = clazz.getPackage();
                checkRequiredBrowsers(method.getAnnotation(TestBrowser.class));
                checkRequiredBrowsers(clazz.getAnnotation(TestBrowser.class));
                checkRequiredBrowsers(pkg.getAnnotation(TestBrowser.class));
                checkIgnoredBrowsers(method.getAnnotation(IgnoreBrowser.class));
                checkIgnoredBrowsers(clazz.getAnnotation(IgnoreBrowser.class));
                base.evaluate();
            }

            private void checkRequiredBrowsers(TestBrowser testBrowser)
            {
                if (testBrowser != null)
                {
                    Browser latestBrowser = BrowserUtil.getCurrentBrowser();
                    Browser browser = WebDriverFactory.getBrowser(testBrowser.value());
                    if (browser != latestBrowser)
                    {
                        log.info(method.getName() + " ignored, since it requires <" + browser + ">");
                        assumeThat(browser, equalTo(latestBrowser));
                    }
                }
            }

            private void checkIgnoredBrowsers(IgnoreBrowser ignoreBrowser)
            {
                if (ignoreBrowser != null && ignoreBrowser.value().length > 0)
                {
                    Browser latestBrowser = BrowserUtil.getCurrentBrowser();

                    for (Browser browser : ignoreBrowser.value())
                    {
                        // if/when Assume supports skip reason messages itself, we won't need the logging
                        // or the if around the Assume calls
                        if (browser == latestBrowser || browser == Browser.ALL)
                        {
                            log.info(method.getName() + " ignored, reason: " + ignoreBrowser.reason());
                            assumeThat(browser, not(latestBrowser));
                            assumeThat(browser, not(Browser.ALL));
                        }
                    }
                }
            }
        };
    }
}
