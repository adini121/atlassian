package com.atlassian.webdriver.testing.rule;

import com.atlassian.webdriver.testing.annotation.IgnoreBrowser;
import com.atlassian.webdriver.utils.Browser;
import com.atlassian.webdriver.utils.WebDriverUtil;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A rule that allows annotating test methods with the {@link IgnoreBrowser}
 * annotation and will skip the test if the current running driver
 * is in the list of browsers to ignore.
 *
 * WARNING: This currently does not report the test as skipped in the final report.
 *
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
                IgnoreBrowser ignoreBrowser = method.getAnnotation(IgnoreBrowser.class);
                if (ignoreBrowser != null && ignoreBrowser.value().length > 0) {
                    Browser latestBrowser = WebDriverUtil.getLatestBrowser();

                    for (Browser browser : ignoreBrowser.value())
                    {
                        if (browser == latestBrowser || browser == Browser.ALL)
                        {
                            log.info(method.getName() + " ignored, reason: " + ignoreBrowser.reason());
                            return;
                        }
                    }
                }

                base.evaluate();
            }
        };
    }
}
