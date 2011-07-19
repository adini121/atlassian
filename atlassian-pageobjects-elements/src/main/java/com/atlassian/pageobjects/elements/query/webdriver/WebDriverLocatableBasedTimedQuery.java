package com.atlassian.pageobjects.elements.query.webdriver;

import com.atlassian.pageobjects.elements.WebDriverLocatable;
import com.atlassian.webdriver.AtlassianWebDriver;
import com.google.common.base.Function;
import com.google.common.base.Supplier;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import static com.atlassian.pageobjects.elements.util.StringConcat.asString;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * <p/>
 * {@link com.atlassian.pageobjects.elements.WebDriverLocatable} based timed query that retrieves
 * {@link org.openqa.selenium.WebElement} using provided {@link com.atlassian.pageobjects.elements.WebDriverLocatable}
 * and applies provided function from that element to the target value.
 *
 * <p/>
 * If given element is not found, the 'invalid value' semantics of the timed query are applied. 
 *
 */
public class WebDriverLocatableBasedTimedQuery<T> extends GenericWebDriverTimedQuery<T>
{
    private final WebDriverLocatable locatable;

    public WebDriverLocatableBasedTimedQuery(WebDriverLocatable locatable, AtlassianWebDriver driver,
                                             Function<WebElement, T> valueProvider, long timeout)
    {
        super(new LocatableBasedSupplier<T>(driver, locatable, valueProvider), timeout);
        this.locatable = locatable;
    }

    public WebDriverLocatableBasedTimedQuery(WebDriverLocatable locatable, AtlassianWebDriver driver,
                                             Function<WebElement, T> valueProvider, long timeout, long interval)
    {
        super(new LocatableBasedSupplier<T>(driver, locatable, valueProvider), timeout, interval);
        this.locatable = locatable;
    }

    public WebDriverLocatableBasedTimedQuery(WebDriverLocatable locatable, AtlassianWebDriver driver,
                                             Function<WebElement, T> valueProvider, long timeout, long interval,
                                             T invalidValue)
    {
        super(new LocatableBasedSupplier<T>(driver, locatable, valueProvider, invalidValue), timeout, interval);
        this.locatable = locatable;
    }

    public WebDriverLocatableBasedTimedQuery(WebDriverLocatableBasedTimedQuery<T> origin, long timeout)
    {
        super(origin.webElementSupplier(), timeout, origin.interval);
        this.locatable = origin.locatable;
    }

    LocatableBasedSupplier<T> webElementSupplier()
    {
        return (LocatableBasedSupplier<T>) valueSupplier;
    }

    Function<WebElement,T> valueProvider()
    {
        return webElementSupplier().valueProvider;
    }

    @Override
    public String toString()
    {
        return asString(super.toString(), "[locatable=", locatable, ",valueProvider=", valueProvider(), "]");
    }

    private static class LocatableBasedSupplier<S> implements Supplier<S>
    {
        private final AtlassianWebDriver webDriver;
        private final WebDriverLocatable locatable;
        private final Function<WebElement, S> valueProvider;
        private final S invalidValue;

         public LocatableBasedSupplier(AtlassianWebDriver webDriver ,WebDriverLocatable locatable,
                                       Function<WebElement, S> valueProvider, S invalid)
        {
            this.valueProvider = valueProvider;
            this.webDriver = webDriver;
            this.locatable = checkNotNull(locatable);
            this.invalidValue = invalid;
        }

        public LocatableBasedSupplier(AtlassianWebDriver webDriver, WebDriverLocatable locatable, Function<WebElement, S> valueProvider)
        {
            this(webDriver, locatable, valueProvider, null);
        }

        public S get()
        {
            try
            {
                return valueProvider.apply((WebElement) locatable.waitUntilLocated(webDriver, 0));
            }
            catch(StaleElementReferenceException e)
            {
                // element became stale between the time we got it from the locatable and we called the
                //  value provider. Error out here and try on next poll.
                throw new InvalidValue(invalidValue);
            }
            catch (NoSuchElementException e1)
            {
                throw new InvalidValue(invalidValue);
            }
        }
    }
}
