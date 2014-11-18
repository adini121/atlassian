package com.atlassian.webdriver.testing.rule;

import com.atlassian.webdriver.browsers.WebDriverBrowserAutoInstall;
import com.atlassian.webdriver.utils.WebDriverUtil;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.Lists;
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
 * At present, the logging only works in Firefox, since we use
 * a Firefox extension to collect the console output.
 *
 * @since 2.3
 */
public class JavaScriptErrorsRule extends TestWatcher
{
    private static final Logger DEFAULT_LOGGER = LoggerFactory.getLogger(JavaScriptErrorsRule.class);
    private static final List<String> EMPTY_LIST = Lists.newArrayList();

    private final Logger logger;
    private final Supplier<? extends WebDriver> webDriver;

    private boolean haveReadErrors = false;
    private List<String> errorStrings = Lists.newArrayList();

    @Inject
    public JavaScriptErrorsRule(WebDriver webDriver, Logger logger)
    {
        this(Suppliers.ofInstance(checkNotNull(webDriver, "webDriver")),logger);
    }

    public JavaScriptErrorsRule(Supplier<? extends WebDriver> webDriver, Logger logger)
    {
        this.webDriver = checkNotNull(webDriver, "webDriver");
        this.logger = checkNotNull(logger, "logger");
    }

    public JavaScriptErrorsRule(Logger logger)
    {
        this(WebDriverBrowserAutoInstall.driverSupplier(), logger);
    }

    public JavaScriptErrorsRule()
    {
        this(DEFAULT_LOGGER);
    }

    @Override
    protected void starting(final Description description)
    {
        haveReadErrors = false;
    }

    @Override
    @VisibleForTesting
    public void finished(final Description description)
    {
        if (!isLogConsoleOutputEnabled())
        {
            return;
        }
        if (supportsConsoleOutput())
        {
            logger.info("----- Test '{}' finished with {} JS error(s). ", description.getMethodName(), errors().size());
            if (errors().size() > 0)
            {
                logger.info("----- START CONSOLE OUTPUT DUMP\n\n{}\n", getConsoleOutput());
                logger.info("----- END CONSOLE OUTPUT DUMP");
                if (shouldFailOnJavaScriptErrors())
                {
                    throw new RuntimeException("Test failed due to javascript errors being detected: " + errors());
                }
            }
        }
        else
        {
            logger.info("<Console output only supported in Firefox right now, sorry!>");
        }
    }

    @VisibleForTesting
    public String getConsoleOutput()
    {
        final StringBuilder sb = new StringBuilder();
        for (String error : errors())
        {
            sb.append(error);
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Get the console output from the browser.
     *
     * @return The result of invoking {@link JavaScriptError#toString} via a List.
     */
    protected List<String> errors()
    {
        if (!haveReadErrors)
        {
            errorStrings = Lists.newArrayList();
            if (supportsConsoleOutput())
            {
                final List<String> errorsToIgnore = errorsToIgnore();
                final List<JavaScriptError> errors = JavaScriptError.readErrors(webDriver.get());
                for (JavaScriptError error : errors)
                {
                    if (errorsToIgnore.contains(error.getErrorMessage()))
                    {
                        if (logger.isDebugEnabled())
                        {
                            logger.debug("Ignoring JS error: {0}", error);
                        }
                    }
                    else
                    {
                        errorStrings.add(error.toString());
                    }
                }
            }
            haveReadErrors = true;
        }
        return errorStrings;
    }

    /**
     * An overridable method which provides a list of error messages
     * that should be ignored when collecting messages from the console.
     *
     * @return a list of exact error message strings to be ignored.
     */
    protected List<String> errorsToIgnore()
    {
        return EMPTY_LIST;
    }

    /**
     * An overridable method which when returning true will cause
     * the rule to throw an exception, thus causing the test method
     * to record a failure.
     *
     * @return true if the test method being wrapped should fail if a javascript error is found. Returns false by default.
     */
    protected boolean shouldFailOnJavaScriptErrors()
    {
        return false;
    }

    private boolean supportsConsoleOutput()
    {
        return WebDriverUtil.isInstance(webDriver.get(), FirefoxDriver.class);
    }

    private boolean isLogConsoleOutputEnabled()
    {
        return true;
    }
}
