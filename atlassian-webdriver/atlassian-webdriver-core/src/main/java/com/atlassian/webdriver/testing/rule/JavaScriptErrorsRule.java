package com.atlassian.webdriver.testing.rule;

import com.atlassian.webdriver.browsers.WebDriverBrowserAutoInstall;
import com.atlassian.webdriver.utils.WebDriverUtil;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Joiner;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import net.jsourcerer.webdriver.jserrorcollector.JavaScriptError;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.Set;

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
    private static final Set<String> EMPTY_SET = ImmutableSet.of();

    private final Logger logger;
    private final Supplier<? extends WebDriver> webDriver;

    private List<String> errors = null;

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
        errors = null;
    }

    @Override
    @VisibleForTesting
    public void succeeded(Description description)
    {
        if (!shouldCheckOnlyIfTestFailed())
        {
            checkErrors(description);
        }
    }

    @Override
    @VisibleForTesting
    public void failed(Throwable e, Description description)
    {
        checkErrors(description);
    }
    
    private void checkErrors(final Description description)
    {
        if (supportsConsoleOutput())
        {
            List<String> errors = getErrors();
            if (errors.isEmpty())
            {
                // Use INFO level if there were no errors, so you can filter these lines out if desired in your logger configuration
                logger.info("----- Test '{}' finished with 0 JS error(s). ", description.getMethodName());
            }
            else
            {
                // Use WARN level if there were errors
                logger.warn("----- Test '{}' finished with {} JS error(s). ", description.getMethodName(), getErrors().size());
                logger.warn("----- START CONSOLE OUTPUT DUMP\n\n{}\n", getConsoleOutput(errors));
                logger.warn("----- END CONSOLE OUTPUT DUMP");
                if (shouldFailOnJavaScriptErrors())
                {
                    throw new RuntimeException("Test failed due to javascript errors being detected: \n" + getConsoleOutput());
                }
            }
        }
        else
        {
            logger.info("Unable to provide console output. Console output is currently only supported on Firefox.");
        }
    }

    @VisibleForTesting
    public String getConsoleOutput()
    {
        return getConsoleOutput(getErrors());
    }

    private String getConsoleOutput(List<String> errors)
    {
        return Joiner.on("\n").join(errors);   
    }

    /**
     * Get the console output from the browser.
     *
     * @return The result of invoking {@link JavaScriptError#toString} via a List.
     */
    @VisibleForTesting
    protected List<String> getErrors()
    {
        if (errors == null)
        {
            errors = Lists.newArrayList();
            if (supportsConsoleOutput())
            {
                final Collection<String> errorsToIgnore = getErrorsToIgnore();
                final Collection<JavaScriptError> errors = JavaScriptError.readErrors(webDriver.get());
                for (JavaScriptError error : errors)
                {
                    if (errorsToIgnore.contains(error.getErrorMessage()))
                    {
                        logger.debug("Ignoring JS error: {}", error);
                    }
                    else
                    {
                        this.errors.add(error.toString());
                    }
                }
            }
        }
        return errors;
    }

    /**
     * An overridable method which provides a list of error messages
     * that should be ignored when collecting messages from the console.
     *
     * @return a list of exact error message strings to be ignored.
     */
    protected Set<String> getErrorsToIgnore()
    {
        return EMPTY_SET;
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

    /**
     * An overridable method which when returning true will skip
     * checking for Javascript errors after successful tests.
     *
     * @return true if the rule should only check JS errors for failed tests. Returns false by default.
     */
    protected boolean shouldCheckOnlyIfTestFailed()
    {
        return false;
    }

    private boolean supportsConsoleOutput()
    {
        return WebDriverUtil.isInstance(webDriver.get(), FirefoxDriver.class);
    }
}
