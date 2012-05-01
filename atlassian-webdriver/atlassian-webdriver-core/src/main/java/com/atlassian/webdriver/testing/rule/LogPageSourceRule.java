package com.atlassian.webdriver.testing.rule;

import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.LifecycleAwareWebDriverGrid;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Similarly to {@link com.atlassian.webdriver.testing.rule.WebDriverScreenshotRule}, this rule will
 * dump page source directly to the log upon test failure.
 *
 * @since 2.1
 */
public final class LogPageSourceRule extends TestWatcher
{
    private static final Logger DEFAULT_LOGGER = LoggerFactory.getLogger(LogPageSourceRule.class);

    private final Logger logger;

    public LogPageSourceRule(Logger logger)
    {
        this.logger = checkNotNull(logger);
    }

    public LogPageSourceRule()
    {
        this(DEFAULT_LOGGER);
    }

    @Override
    public void failed(final Throwable e, final Description description)
    {
        final AtlassianWebDriver driver = LifecycleAwareWebDriverGrid.getCurrentDriver();
        logger.info("----- %s Failed. ", description.getMethodName());
        logger.info("----- Page source:\n");
        logger.info(driver.getPageSource());
    }
}
