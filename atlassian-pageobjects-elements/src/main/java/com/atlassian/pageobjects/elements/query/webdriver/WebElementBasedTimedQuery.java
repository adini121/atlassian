package com.atlassian.pageobjects.elements.query.webdriver;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import org.openqa.selenium.*;

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

    public WebElementBasedTimedQuery(SearchContext searchContext, By by, final Function<WebElement, T> valueProvider, long timeout,
            long interval)
    {
        super(new WebElementSupplier<T>(searchContext, by, valueProvider), timeout, interval);
    }

    public WebElementBasedTimedQuery(SearchContext searchContext, By by, final Function<WebElement, T> valueProvider, long timeout,
            long interval, T invalidValue)
    {
        super(new WebElementSupplier<T>(searchContext, by, valueProvider, invalidValue), timeout, interval);
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
        private final SearchContext searchContext;
        private final By by;
        private final Function<WebElement, S> valueProvider;
        private final S invalidValue;

         public WebElementSupplier(final SearchContext searchContext, final By by, final Function<WebElement, S> valueProvider,
                 S invalid)
        {
            this.valueProvider = valueProvider;
            this.searchContext = checkNotNull(searchContext);
            this.by = checkNotNull(by);
            this.invalidValue = invalid;
        }

        public WebElementSupplier(final SearchContext searchContext, final By by, final Function<WebElement, S> valueProvider)
        {
            this(searchContext, by, valueProvider, null);
        }

        public S get()
        {
            try
            {
                return valueProvider.apply(searchContext.findElement(by));
            }
            catch (NoSuchElementException e)
            {
                throw new InvalidValue(invalidValue);
            }
        }
    }
}
