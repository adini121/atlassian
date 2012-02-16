package com.atlassian.webdriver.testing.rule;

import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.LifecycleAwareWebDriverGrid;
import org.junit.rules.ExternalResource;

/**
 * A simple rule to clear the browsers session at the end of a test.
 * This removes the need to have an @After method that logs the user out of the
 * TestedProduct.
 *
 * This simply clears the cookies from the browser making it much quicker then actually
 * going through the products logout process.
 *
 * @since 2.1.0
 */
public class SessionCleanupRule extends ExternalResource
{
    @Override
    protected void after()
    {
        AtlassianWebDriver driver = LifecycleAwareWebDriverGrid.getCurrentDriver();
        if (driver != null)
        {
            driver.manage().deleteAllCookies();
        }
    }
}
