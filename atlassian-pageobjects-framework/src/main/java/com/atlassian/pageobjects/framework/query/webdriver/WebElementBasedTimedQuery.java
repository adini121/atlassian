package com.atlassian.pageobjects.framework.query.webdriver;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * <p>
 * WebDriver based timed query that retrieves {@link org.openqa.selenium.WebElement} using provided
 * {@link org.openqa.selenium.By} and applies provided function from that element to the target value.
 *
 * <p>
 * If given element is not found, the 'invalid value' semantics of the timed query are applied. 
 *
 */
public class WebElementBasedTimedQuery<T> extends GenericWebDriverTimedQuery<T>
{
    public WebElementBasedTimedQuery(WebDriver driver, By by, final Function<WebElement, T> valueProvider, long timeout)
    {
        super(new WebElementSupplier<T>(driver, by, valueProvider), timeout);
    }

    public WebElementBasedTimedQuery(WebDriver driver, By by, final Function<WebElement, T> valueProvider, long timeout,
            long interval)
    {
        super(new WebElementSupplier<T>(driver, by, valueProvider), timeout, interval);
    }

    public WebElementBasedTimedQuery(WebElementBasedTimedQuery<T> origin, long timeout)
    {
        super(origin.webElementSupplier(), timeout, origin.interval);
    }

    WebElementSupplier<T> webElementSupplier()
    {
        return (WebElementSupplier<T>) valueSupplier; 
    }

    private static class WebElementSupplier<S> implements Supplier<S>
    {
        private final WebDriver webDriver;
        private final By by;
        private final Function<WebElement, S> valueProvider;

        public WebElementSupplier(final WebDriver webDriver, final By by, final Function<WebElement, S> valueProvider)
        {
            this.valueProvider = valueProvider;
            this.webDriver = checkNotNull(webDriver);
            this.by = checkNotNull(by);
        }

        public S get()
        {
            try
            {
                return valueProvider.apply(webDriver.findElement(by));
            }
            catch (NoSuchElementException e)
            {
                throw new InvalidValue();
            }
        }
    }
}
