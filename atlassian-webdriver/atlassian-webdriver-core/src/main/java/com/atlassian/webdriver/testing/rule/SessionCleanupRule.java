package com.atlassian.webdriver.testing.rule;

import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.LifecycleAwareWebDriverGrid;
import org.junit.rules.ExternalResource;

/**
 * A simple rule to clear the browsers session at the end of a test.
 */
public class SessionCleanupRule extends ExternalResource
{
    @Override
    protected void after()
    {
        AtlassianWebDriver driver = LifecycleAwareWebDriverGrid.getCurrentDriver();
        driver.manage().deleteAllCookies();
    }
}
