package com.atlassian.webdriver.testing.rule;

import com.atlassian.webdriver.testing.annotation.IgnoreBrowser;
import com.atlassian.webdriver.utils.Browser;
import com.atlassian.webdriver.utils.WebDriverUtil;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

/**
 * A rule that allows annotating test methods with the {@link IgnoreBrowser}
 * annotation and will skip the test if the current running driver
 * is in the list of browsers to ignore.
 */
public class IgnoreBrowserRule implements MethodRule
{
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
                            System.out.println(method.getName() + " ignored, reason: " + ignoreBrowser.reason());
                            return;
                        }
                    }
                }

                base.evaluate();
            }
        };
    }
}
