package com.atlassian.pageobjects.internal.elements.search;

import com.atlassian.annotations.Internal;
import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.elements.PageElement;
import com.atlassian.pageobjects.elements.WebDriverElement;
import com.atlassian.pageobjects.elements.WebDriverLocatable;
import com.atlassian.pageobjects.elements.WebDriverLocators;
import com.atlassian.pageobjects.elements.search.SearchQuery;
import com.atlassian.pageobjects.elements.timeout.TimeoutType;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import org.apache.commons.lang.mutable.MutableInt;
import org.hamcrest.Matcher;
import org.openqa.selenium.*;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;
import javax.inject.Inject;
import java.util.Collections;
import java.util.Deque;

import static com.atlassian.pageobjects.elements.WebDriverLocatable.LocateTimeout;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@code WebDriver}-based implementation of {@link SearchQuery}.
 *
 * @since 2.3
 */
@Internal
@NotThreadSafe
final class WebDriverElementSearchQuery implements SearchQuery
{
    @Inject
    private PageBinder pageBinder;

    @Inject
    private WebDriver webDriver;

    private final WebDriverLocatable root;
    private final Deque<SearchStep> searchQueue = Lists.newLinkedList();


    public WebDriverElementSearchQuery(WebDriverLocatable root)
    {
        this.root = root;
    }

    @Nonnull
    @Override
    public SearchQuery by(@Nonnull By by)
    {
        searchQueue.add(new SearchStep(by));

        return this;
    }

    @Nonnull
    @Override
    public SearchQuery by(@Nonnull By by, @Nonnull Predicate<? super PageElement> filter)
    {
        searchQueue.add(new SearchStep(by, filter));

        return this;
    }

    @Nonnull
    @Override
    public SearchQuery filter(@Nonnull Predicate<? super PageElement> filter)
    {
        checkSearchQueue();
        searchQueue.getLast().addFilter(filter);

        return this;
    }

    @Nonnull
    @Override
    public SearchQuery filter(@Nonnull Matcher<? super PageElement> filter)
    {
        checkSearchQueue();
        searchQueue.getLast().addFilter(filter);

        return this;
    }

    @Nonnull
    @Override
    public PageElementResult<PageElement> find()
    {
        return null;
    }

    private void checkSearchQueue()
    {
        if (searchQueue.isEmpty())
        {
            throw new IllegalStateException("There is no search steps specified, cannot specify a filter");
        }
    }

    private final class SearchStep implements Function<WebDriverLocatable, Iterable<WebDriverLocatable>>
    {
        private final By by;
        private Predicate<? super PageElement> filter;

        private SearchStep(By by)
        {
            this(by, Predicates.alwaysTrue());
        }

        private SearchStep(By by, Predicate<? super PageElement> filter)
        {
            this.by = checkNotNull(by, "by");
            this.filter = checkNotNull(filter, "filter");
        }

        @Override
        public Iterable<WebDriverLocatable> apply(WebDriverLocatable root)
        {
            try
            {
                SearchContext searchContext = root.waitUntilLocated(webDriver, LocateTimeout.zero());
                // need to transform into PageElement and then back to apply the predicate - lame for now, but cannot
                // use PageElementFinder interface as the implementation is not good (SELENIUM-253)
                return FluentIterable.from(searchContext.findElements(by))
                        .transform(toListLocatable(by, new MutableInt(), root))
                        .transform(WebDriverElement.bind(pageBinder, WebDriverElement.class, TimeoutType.DEFAULT))
                        .filter(filter)
                        .transform(WebDriverElement.TO_LOCATABLE)
                        .toImmutableList();
            }
            catch (NoSuchElementException e)
            {
                return Collections.emptyList();
            }
        }

        void addFilter(Predicate<? super PageElement> filter)
        {
            this.filter = Predicates.and(this.filter, checkNotNull(filter, "filter"));
        }

        void addFilter(final Matcher<? super PageElement> filter)
        {
            checkNotNull(filter, "filter");

            addFilter(new Predicate<PageElement>()
            {
                @Override
                public boolean apply(PageElement element)
                {
                    return filter.matches(element);
                }
            });
        }
    }

    private static Function<WebElement, WebDriverLocatable> toListLocatable(final By locator, final MutableInt index,
                                                                            final WebDriverLocatable parent)
    {
        return new Function<WebElement, WebDriverLocatable>()
        {
            @Override
            public WebDriverLocatable apply(WebElement element)
            {
                WebDriverLocatable result = WebDriverLocators.list(element, locator, index.intValue(), parent);
                index.increment();
                return result;

            }
        };
    }
}
