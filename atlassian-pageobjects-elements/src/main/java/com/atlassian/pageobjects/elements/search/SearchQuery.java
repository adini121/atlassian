package com.atlassian.pageobjects.elements.search;

import com.atlassian.annotations.PublicApi;
import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.PageObjects;
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

    /**
     * The base functionality that any search result supports. {@code Result} essentially encapsulates a search process
     * that results in an ordered list of elements of type {@code E}.
     *
     * @param <E> type of the result element
     * @param <R> target result type
     */
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

        /**
         * Timed condition to check whether there was any result. If {@code hasResult().now()} returns {@code true},
         * {@link #first()} <i>should</i> return non-null value - and vice-versa (unless the state of the DOM that
         * affects the search has changed in-between calls to this method and {@code first()}.
         *
         * @return timed condition to check whether the search gave at least one result.
         */
        @Nonnull
        TimedCondition hasResult();

        /**
         * @return all elements that the search resulted in, at the moment of calling.
         */
        @Nonnull
        Iterable<E> all();

        /**
         * Timed query for the results. This is equivalent to {@link #all()}, but allows for timed assertions using
         * {@code Poller}, such as:
         * <p/>
         * <pre>
         *     {@code
         *     // ... wait until the table contains 3 users alice, bob and charlie
         *     Poller.waitUntil(myTableElement.search().
         *          .by(By.tagName("td")).filter(hasClass("user-cell"))
         *          .transform(getDataAttribute("username"))
         *          .timed(), contains("alice", "bob", "charlie"));
         *     }
         * </pre>
         *
         * @return timed query for all elements that the search resulted in
         */
        @Nonnull
        TimedQuery<Iterable<E>> timed();

        /**
         * Supplier of the results. The result of invoking {@link Supplier#get()} on the returned supplier is equivalent
         * to the call to {@link #all()}. Note that the supplier does not memoize the result, instead executing the
         * search every time {@code get()} is called.
         *
         * @return supplier for all elements that the search resulted in
         * @see Supplier
         */
        @Nonnull
        Supplier<Iterable<E>> supplier();

        /**
         * Filter the final results of the search using a {@link Predicate}.
         *
         * @param filter filter
         * @return a result object that takes current results and filters them using {@code filter}
         */
        @Nonnull
        R filter(@Nonnull Predicate<? super E> filter);

        /**
         * Filter the final results of the search using a {@link Matcher}.
         *
         * @param filter filter
         * @return a result object that takes current results and filters them using {@code filter}
         */
        @Nonnull
        R filter(@Nonnull Matcher<? super E> filter);

        /**
         * Transform the final results of the search using a {@link Function}. The function must take input compatible
         * with the original result type and return an instance of the new result type.
         *
         * @param transformer the result transformer
         * @param <F> the new result element type
         * @return a result object that transforms current results into instances of {@code F}
         */
        @Nonnull
        <F> AnyResult<F> transform(@Nonnull Function<? super E, ? extends F> transformer);

        /**
         * Transform the results into instance of page objects, by binding {@code pageObjectClass} and using the current
         * results as input to the bind, optionally with {@code extraArgs}.
         * <p/>
         * NOTE: the {@code pageObjectClass} must have constructor that accepts the original result element type and
         * any optional {@code extraArgs}, or otherwise runtime errors will occur <i>when attempting to retrieve the
         * results</i>. See {@code PageBinder} documentation for the details of the binding process.
         *
         * @param pageObjectClass page object class to bind
         * @param extraArgs optional extra arguments to pass to the binder
         * @param <PO> type parameter of {@code pageObjectClass}
         * @return a result object that transforms the current results into page objects wrapping the original results
         * @see PageBinder#bind(Class, Object...)
         * @see PageObjects#bind(PageBinder, Iterable, Class, Object...)
         */
        @Nonnull
        <PO> AnyResult<PO> bindTo(@Nonnull Class<PO> pageObjectClass, @Nonnull Object... extraArgs);
    }

    /**
     * Represents results where the elements are of any type. Supports the API of {@link AnyResult}.
     *
     * @param <E> element type
     */
    @PublicApi
    interface AnyResult<E> extends Result<E, AnyResult<E>> {}

    /**
     * Represents a result where the elements are instances of {@link PageElement}. Supports the API of
     * {@link AnyResult} <i>and</i> extra page-element-specific API on top of that.
     *
     * @param <PE> element type, must extend from {@code PageElement}
     * @see PageElement
     */
    @PublicApi
    interface PageElementResult<PE extends PageElement> extends Result<PE, PageElementResult<PE>>
    {
        /**
         * Change timeout of the elements in the result to {@code TimeoutType}.
         *
         * @param timeoutType the new timeout type to apply
         * @return a result object that applies {@code timeoutType} to the current results
         */
        @Nonnull
        PageElementResult<PE> withTimeout(@Nonnull TimeoutType timeoutType);

        /**
         * Bind the results to a more specific page element instance of type specified by {@code pageElementClass}.
         *
         * @param pageElementClass page element type to use, e.g. {@code CheckboxElement}
         * @return a result object that transforms the current results into instances of {@code pageElementClass}
         */
        @Nonnull
        <PEE extends PE> PageElementResult<PEE> as(@Nonnull Class<PEE> pageElementClass);
    }
}
