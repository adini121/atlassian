package com.atlassian.webdriver.testing.rule;

import com.atlassian.webdriver.browsers.WebDriverBrowserAutoInstall;
import com.atlassian.webdriver.utils.WebDriverUtil;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
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
import static com.google.common.collect.Iterables.transform;

/**
 * Rule to log Javascript console error messages.
 *
 * At present, the logging only works in Firefox, since we use
 * a Firefox extension to collect the console output.
 *
 * @since 2.3
 */
public class JavaScriptErrorsRule extends TestWatcher
{
    private static final Logger DEFAULT_LOGGER = LoggerFactory.getLogger(JavaScriptErrorsRule.class);

    private final Logger logger;
    private final Supplier<? extends WebDriver> webDriver;
    private final ImmutableSet<String> errorsToIgnore;
    private final boolean failOnJavaScriptErrors;
    private final boolean checkOnlyIfTestFailed;
    private final ErrorRetriever errorRetriever;

    protected JavaScriptErrorsRule(ErrorRetriever errorRetriever,
            Supplier<? extends WebDriver> webDriver,
            Logger logger,
            Set<String> errorsToIgnore,
            boolean failOnJavaScriptErrors,
            boolean checkOnlyIfTestFailed)
    {
        this.errorRetriever = checkNotNull(errorRetriever, "errorRetriever");
        this.webDriver = checkNotNull(webDriver, "webDriver");
        this.logger = checkNotNull(logger, "logger");
        this.errorsToIgnore = ImmutableSet.copyOf(checkNotNull(errorsToIgnore, "errorsToIgnore"));
        this.failOnJavaScriptErrors = failOnJavaScriptErrors;
        this.checkOnlyIfTestFailed = checkOnlyIfTestFailed;
    }

    public JavaScriptErrorsRule(Supplier<? extends WebDriver> webDriver)
    {
        this(new DefaultErrorRetriever(webDriver), webDriver, DEFAULT_LOGGER, ImmutableSet.<String>of(), false, false);
    }

    public JavaScriptErrorsRule(WebDriver webDriver)
    {
        this(Suppliers.ofInstance(checkNotNull(webDriver, "webDriver")));
    }

    public JavaScriptErrorsRule()
    {
        this(WebDriverBrowserAutoInstall.driverSupplier());
    }

    /**
     * Returns a copy of this rule with a different underlying {@link ErrorRetriever}.
     * You may wish to override the ErrorRetriever in order to do custom post-processing of error messages.
     * @param errorRetriever  an implementation of {@link ErrorRetriever}
     * @return  a new JavaScriptErrorsRule based on the current instance
     */
    public JavaScriptErrorsRule errorRetriever(ErrorRetriever errorRetriever)
    {
        return new JavaScriptErrorsRule(errorRetriever, this.webDriver, this.logger, this.errorsToIgnore,
                this.failOnJavaScriptErrors, this.checkOnlyIfTestFailed);
    }

    /**
     * Returns a copy of this rule with a specific set of error messages to ignore.  Any error whose
     * message exactly matches one of the strings in this set will not be logged.
     * @param errorsToIgnore  a set of error messages to ignore
     * @return  a new JavaScriptErrorsRule based on the current instance
     */
    public JavaScriptErrorsRule errorsToIgnore(Set<String> errorsToIgnore)
    {
        return new JavaScriptErrorsRule(this.errorRetriever, this.webDriver, this.logger, errorsToIgnore,
                this.failOnJavaScriptErrors, this.checkOnlyIfTestFailed);
    }

    /**
     * Returns a copy of this rule that writes to a different logger.
     * @param logger  a {@link Logger}
     * @return  a new JavaScriptErrorsRule based on the current instance
     */
    public JavaScriptErrorsRule logger(Logger logger)
    {
        return new JavaScriptErrorsRule(this.errorRetriever, this.webDriver, logger, this.errorsToIgnore,
                this.failOnJavaScriptErrors, this.checkOnlyIfTestFailed);
    }

    /**
     * Returns a copy of this rule, specifying whether it should force a test failure when Javascript errors are found.
     * This property is false by default.
     * @param failOnJavaScriptErrors  true if Javascript errors should always cause a test to fail
     * @return  a new JavaScriptErrorsRule based on the current instance
     */
    public JavaScriptErrorsRule failOnJavaScriptErrors(boolean failOnJavaScriptErrors)
    {
        return new JavaScriptErrorsRule(this.errorRetriever, this.webDriver, this.logger, this.errorsToIgnore,
                failOnJavaScriptErrors, this.checkOnlyIfTestFailed);
    }

    /**
     * Returns a copy of this rule, specifying whether it should skip checking for Javascript errors on passed tests.
     * This property is false by default.
     * @param checkOnlyIfTestFailed  true if the rule should only check for errors on tests that have already failed
     * @return  a new JavaScriptErrorsRule based on the current instance
     */
    public JavaScriptErrorsRule checkOnlyIfTestFailed(boolean checkOnlyIfTestFailed)
    {
        return new JavaScriptErrorsRule(this.errorRetriever, this.webDriver, this.logger, this.errorsToIgnore,
                this.failOnJavaScriptErrors, checkOnlyIfTestFailed);
    }

    @Override
    @VisibleForTesting
    public void succeeded(Description description)
    {
        if (!checkOnlyIfTestFailed)
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
                if (failOnJavaScriptErrors)
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
        final ImmutableList.Builder<String> ret = ImmutableList.builder();
        if (supportsConsoleOutput())
        {
            final Iterable<ErrorInfo> errors = errorRetriever.getErrors();
            for (ErrorInfo error : errors)
            {
                if (errorsToIgnore.contains(error.getMessage()))
                {
                    logger.debug("Ignoring JS error: {}", error);
                }
                else
                {
                    ret.add(error.getDescription());
                }
            }
        }
        return ret.build();
    }

    private boolean supportsConsoleOutput()
    {
        return WebDriverUtil.isInstance(webDriver.get(), FirefoxDriver.class);
    }

    /**
     * Abstraction of an error message we have retrieved from the browser.
     */
    public interface ErrorInfo
    {
        /**
         * Returns the full error line as it should be displayed.
         */
        String getDescription();

        /**
         * Returns only the message portion of the error (for comparison to errorsToIgnore).
         */
        String getMessage();
    }

    /**
     * Abstraction of the underlying error retrieval mechanism.  The standard implementation is
     * DefaultErrorRetriever; this can be overridden for unit testing of this class, or to perform
     * custom post-processing of the results.
     */
    public interface ErrorRetriever
    {
        Iterable<ErrorInfo> getErrors();
    }

    public static class DefaultErrorRetriever implements ErrorRetriever
    {
        private final Supplier<? extends WebDriver> webDriver;

        DefaultErrorRetriever(Supplier<? extends WebDriver> webDriver)
        {
            this.webDriver = webDriver;
        }

        @Override
        public Iterable<ErrorInfo> getErrors()
        {
            return transform(JavaScriptError.readErrors(webDriver.get()), new Function<JavaScriptError, ErrorInfo>()
            {
                public ErrorInfo apply(final JavaScriptError e)
                {
                    return new ErrorInfo()
                    {
                        public String getDescription()
                        {
                            return e.toString();
                        }

                        public String getMessage()
                        {
                            return e.getErrorMessage();
                        }
                    };
                }
            });
        }
    }
}
