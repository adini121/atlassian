package com.atlassian.webdriver.testing.rule;

import com.atlassian.webdriver.testing.annotation.IgnoreBrowser;
import com.atlassian.webdriver.utils.Browser;
import com.atlassian.webdriver.utils.WebDriverUtil;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assume.assumeThat;

/**
 * A rule that allows annotating test methods with the {@link IgnoreBrowser}
 * annotation and will skip the test if the current running driver
 * is in the list of browsers to ignore.
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
                checkBrowsers(method.getAnnotation(IgnoreBrowser.class));
                checkBrowsers(method.getMethod().getDeclaringClass().getAnnotation(IgnoreBrowser.class));
                base.evaluate();
            }

            private void checkBrowsers(IgnoreBrowser ignoreBrowser)
            {
                if (ignoreBrowser != null && ignoreBrowser.value().length > 0) {
                    Browser latestBrowser = WebDriverUtil.getLatestBrowser();

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
