package com.atlassian.pageobjects.elements.search;

import com.atlassian.annotations.PublicApi;
import com.atlassian.pageobjects.elements.PageElement;
import com.atlassian.pageobjects.elements.query.TimedCondition;
import com.atlassian.pageobjects.elements.query.TimedQuery;
import com.atlassian.pageobjects.elements.timeout.TimeoutType;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Supplier;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.openqa.selenium.By;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Represents a hierarchical query to find one or more DOM elements on the tested page. See the documentation in
 * {@link PageElementSearch} for a detailed description of how to use this API.
 *
 * @since 2.3
 * @see PageElementSearch
 * @see com.atlassian.pageobjects.elements.testing.PageElementMatchers
 */
@PublicApi
public interface SearchQuery
{

    /**
     * Append a search step to find all elements from the results of previous steps, using {@code by} as the locator.
     *
     * @param by locator for the elements to find in the new search step
     * @return this query
     */
    @Nonnull
    SearchQuery by(@Nonnull By by);

    /**
     * Append a search step to find all elements from the results of previous steps, using {@code by} as the locator and
     * an additional {@code filter}.
     *
     * @param by locator for the elements to find in the new search step
     * @param filter predicate to filter the results in this step
     * @return this query
     * @see Predicate
     * @see com.atlassian.pageobjects.elements.PageElements
     */
    @Nonnull
    SearchQuery by(@Nonnull By by, @Nonnull Predicate<? super PageElement> filter);

    /**
     * Add a filter to the last search step in this query. If multiple filters are added to a single step, <i>all</i>
     * filters must be match be an element to remain in the results.
     *
     * @param filter filter to add
     * @return this query
     * @throws IllegalStateException if there are no search steps defined
     * @see Predicate
     * @see com.atlassian.pageobjects.elements.PageElements
     */
    @Nonnull
    SearchQuery filter(@Nonnull Predicate<? super PageElement> filter);

    /**
     * Add a matcher filter to the last search step in this query. If multiple filters are added to a single step,
     * <i>all</i> filters must be match be an element to remain in the results.
     *
     * @param filter filter to add
     * @return this query
     * @throws IllegalStateException if there are no search steps defined
     * @see Matcher
     * @see Matchers
     * @see com.atlassian.pageobjects.elements.testing.PageElementMatchers
     */
    @Nonnull
    SearchQuery filter(@Nonnull Matcher<? super PageElement> filter);

    /**
     * Execute the search.
     *
     * @return the search result
     */
    @Nonnull
    SearchResult find();

    @PublicApi
    interface Result<E, R extends Result<E, R>>
    {
        /**
         * First element, or {@code null}, if no result present.
         *
         * @return first element found by the search query
         */
        @Nullable
        E first();

        @Nonnull
        TimedCondition hasResult();

        @Nonnull
        Iterable<E> all();

        @Nonnull
        TimedQuery<Iterable<E>> timed();

        @Nonnull
        Supplier<Iterable<E>> supplier();

        @Nonnull
        R filter(@Nonnull Predicate<? super E> filter);

        @Nonnull
        R filter(@Nonnull Matcher<? super E> filter);

        @Nonnull
        <F> AnyResult<F> transform(@Nonnull Function<E, F> transformer);

        @Nonnull
        <PO> AnyResult<PO> bindTo(@Nonnull Class<PO> pageObjectClass, @Nonnull Object... extraArgs);
    }

    @PublicApi
    interface AnyResult<E> extends Result<E, AnyResult<E>> {}

    @PublicApi
    interface PageElementResult<PE extends PageElement> extends Result<PE, PageElementResult<PE>>
    {
        @Nonnull
        PageElementResult<PE> withTimeout(@Nonnull TimeoutType timeoutType);

        @Nonnull
        <PEE extends PE> PageElementResult<PEE> as(@Nonnull Class<PEE> pageElementClass);
    }

}
