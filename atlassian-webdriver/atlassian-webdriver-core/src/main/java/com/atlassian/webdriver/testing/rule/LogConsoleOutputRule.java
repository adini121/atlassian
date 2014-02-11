package com.atlassian.webdriver.testing.rule;

import com.atlassian.webdriver.browsers.WebDriverBrowserAutoInstall;
import com.atlassian.webdriver.utils.WebDriverUtil;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import net.jsourcerer.webdriver.jserrorcollector.JavaScriptError;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Rule to log javascript console error messages.
 *
 * @since 2.3
 */
public class LogConsoleOutputRule extends TestWatcher
{
    private static final Logger DEFAULT_LOGGER = LoggerFactory.getLogger(LogConsoleOutputRule.class);

    private final Logger logger;
    private final Supplier<? extends WebDriver> webDriver;

    @Inject
    public LogConsoleOutputRule(WebDriver webDriver, Logger logger)
    {
        this(Suppliers.ofInstance(checkNotNull(webDriver, "webDriver")),logger);
    }

    public LogConsoleOutputRule(Supplier<? extends WebDriver> webDriver, Logger logger)
    {
        this.webDriver = checkNotNull(webDriver, "webDriver");
        this.logger = checkNotNull(logger, "logger");
    }

    public LogConsoleOutputRule(Logger logger)
    {
        this(WebDriverBrowserAutoInstall.driverSupplier(), logger);
    }

    public LogConsoleOutputRule()
    {
        this(DEFAULT_LOGGER);
    }

    @Override
    public void failed(final Throwable e, final Description description)
    {
        if (!isLogConsoleOutputEnabled())
        {
            return;
        }
        logger.info("----- Test '{}' Failed. ", description.getMethodName());
        logger.info("----- START CONSOLE OUTPUT DUMP\n\n\n{}\n\n\n", getConsoleOutput());
        logger.info("----- END CONSOLE OUTPUT DUMP");
    }

    public String getConsoleOutput()
    {
        final WebDriver driver = webDriver.get();
        if (!supportsConsoleOutput(driver))
        {
            return "<Console output only supported in Firefox right now, sorry!>";
        }
        else
        {
            final StringBuilder sb = new StringBuilder();
            List<JavaScriptError> errors = JavaScriptError.readErrors(driver);
            for (int i=0; i < errors.size(); i++)
            {
                JavaScriptError error = errors.get(i);
                if (i!=0) sb.append("\n");
                sb.append(error.toString());
            }
            return sb.toString();
        }
    }

    private boolean supportsConsoleOutput(final WebDriver driver)
    {
        return WebDriverUtil.isInstance(driver, FirefoxDriver.class);
    }

    private boolean isLogConsoleOutputEnabled()
    {
        return true;
    }
}
