package com.atlassian.webdriver;

import com.atlassian.webdriver.browsers.AutoInstallConfiguration;
import com.atlassian.webdriver.testing.rule.WebDriverScreenshotRule;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @since 2.0
 * @deprecated This class is no longer necessary as most of this is provided
 * by the rules in: {@link com.atlassian.webdriver.testing.rule}
 * Scheduled for removal in 2.3
 */
@Deprecated
public abstract class AtlassianWebDriverTestBase
{
    private final Logger log = LoggerFactory.getLogger(AtlassianWebDriverTestBase.class);

    protected static AtlassianWebDriver driver;

    @Rule
    public WebDriverScreenshotRule rule = new WebDriverScreenshotRule();

    @After
    public void tearDown() {
        if (driver != null) {
            driver.manage().deleteAllCookies();
        }
    }

    @BeforeClass
    public static void startUp() {
        driver = WebDriverFactory.getDriver(AutoInstallConfiguration.setupBrowser());
    }

    @AfterClass
    public static void cleanUp() throws Exception {
        driver.quit();
        driver = null;
    }

}
