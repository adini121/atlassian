package com.atlassian.pageobjects.elements.search;

import com.atlassian.annotations.PublicApi;
import com.atlassian.pageobjects.elements.query.TimedCondition;
import com.atlassian.pageobjects.elements.query.TimedQuery;
import com.atlassian.pageobjects.elements.timeout.TimeoutType;

import javax.annotation.Nonnull;

/**
 * <h3>Search Overview</h3>
 * Access point to advanced search within the context of the whole page, or a particular
 * {@link com.atlassian.pageobjects.elements.PageElement DOM element}.
 *
 * <p/>
 * The search query builder object provided by this method allows for issuing a sophisticated, multi-level search
 * across the DOM and returning the result in a desired form. The following paragraphs discuss the supported queries and
 * results.
 *
 * <h3>Queries</h3>
 * The {@code SearchQuery} object is essentially a sequence of search steps that, when
 * {@link SearchQuery#find() executed}, will be applied sequentially, starting with the root context (either the entire
 * page, or a specific DOM element, depending on what this {@code PageElementSearch} refers to). Each single step will
 * be applied to <i>all parents</i> that were found by the previous steps, resulting in a new list of DOM elements that
 * will be roots for the next step, and so on until the last search step is executed.
 *
 * <p/>
 * Each single step <i>must</i> be specified using a {@link org.openqa.selenium.By by locator}, using one of the
 * {@code by()} methods on {@link SearchQuery}, the canonical one being {@link SearchQuery#by(org.openqa.selenium.By)}.
 *
 * <p/>
 * Additionally each query supports {@code filters} to further narrow down the results. Filters can be specified by:
 * <ul>
 *     <li>{@link com.google.common.base.Predicate Guava predicates} - the
 *     {@link com.atlassian.pageobjects.elements.PageElements} class contains a collection of common predicates to use</li>
 *     <li>{@link org.hamcrest.Matcher Hamcrest matchers} - the
 *     {@link com.atlassian.pageobjects.elements.testing.PageElementMatchers} class contains a collection of common
 *     matchers to use</li>
 * </ul>
 * <p/>
 * Filters are added by means of the {@code SearchQuery.filter(...)} methods, which add the filter to the <i>most
 * recently</i> added search step (if no step has been specified using {@code SearchQuery.by(...)}, an exception will be
 * raised). Filters are tested using logical add operation, meaning that <i>each</i> filter has to be satisfied by a
 * given element to remain in the search results. Clients may use {@link com.google.common.base.Predicates#or(Iterable)}
 * if the goal is to satisfy <i>any</i> predicate.
 * Note that {@link SearchQuery#by(org.openqa.selenium.By, com.google.common.base.Predicate)} is a shortcut for adding
 * a query with a single filter (specified by a {@code Guava} predicate).
 *
 * <h4>Example</h4>
 * The following query searches for an {@code ul} (unordered list) element that has "user-dropdown" ID, then finds
 * all <i>visible</i> {@code li} (list item) elements within it that have "user-element" CSS class, and finally
 * retrieves {@code span} items from those with a {@code data-username} attribute set on them. This could be applied to
 * finding all usernames in a drop-down with a hypothetical HTML structure reflected by the search.
 * <p/>
 * <pre>
 * {@code
 *      mySearch.search()
 *          .by(By.id("user-dropdown"))
 *          .by(By.tagName("li")) // can also use Elements.TAG_LI here
 *              .filter(isVisible()) // from PageElements
 *              .filter(hasClass("user-element")) // from PageElements
 *          .by(By.tagName("span"), hasDataAttribute("data-username")) // from PageElements
 *          .find()
 * }
 * </pre>
 *
 * <h3>Results</h3>
 * Once {@link SearchQuery#find()} is executed, an object representing the result is returned, which allows to further
 * filter the search result, as well as return it in a form most suitable for a use case at hand.
 *
 * <h4>Result types</h4>
 * Results can be:
 * <ul>
 *     <li>{@link SearchQuery.AnyResult generic} - this kind of result can refer to any result type. Such results offer:
 *     <ul>
 *         <li>returning the results as first element, the whole list, a
 *         {@link com.google.common.base.Supplier Guava supplier}, or a {@link TimedQuery} (see {@code Results} section
 *         below)</li>
 *         <li>{@link SearchQuery.AnyResult#filter(com.google.common.base.Predicate) filter} the results in the same
 *         fashion as {@code SearchQuery} offers</li>
 *         <li>{@link SearchQuery.AnyResult#transform(com.google.common.base.Function) transform} the elements in the
 *         result into a type of choice, using a function</li>
 *         <li>{@link SearchQuery.AnyResult#bindTo(Class, Object...) transform the results into page objects}, by
 *         binding the page object class of choice and using the original elements as input to the binding process</li>
 *     </ul>
 *     </li>
 *     <li>{@link SearchQuery.PageElementResult Specific to page elements} - that is the initial result from search
 *     query; Page element results have <i>all the functionality</i> of {@code AnyResult} plus extra functionality
 *     specific to page elements:
 *     <ul>
 *         <li>{@link SearchQuery.PageElementResult#withTimeout(TimeoutType) change default timeout} of the page
 *         elements in the result</li>
 *         <li>{@link SearchQuery.PageElementResult#as(Class) narrow down the page element} to a more specific element
 *         representation, e.g. {@link com.atlassian.pageobjects.elements.CheckboxElement}</li>
 *     </ul>
 *     </li>
 * </ul>
 *
 * <h4>Using the result</h4>
 * The result object offers multiple methods to obtain the results, depending on what the client wants to achieve:
 * <ul>
 *     <li>{@link SearchQuery.AnyResult#first()} - execute the search now and return the first result, or {@code null},
 *     if there is no results</li>
 *     <li>{@link SearchQuery.AnyResult#all()} - execute the search now and return all results, or an empty collection,
 *     if no results were found</li>
 *     <li>{@link SearchQuery.AnyResult#hasResult()} - obtain a {@link TimedCondition} that will periodically execute
 *     the search and allow the client to wait until there is any result/there is no result matching the query, with
 *     a desired timeout - using {@link com.atlassian.pageobjects.elements.query.Poller}</li>
 *     <li>{@link SearchQuery.AnyResult#supplier()} - obtain a {@link com.google.common.base.Supplier Guava supplier}
 *     that will execute the search each time its {@code Supplier.get()} method is called</li>
 *     <li>{@link SearchQuery.AnyResult#timed()} - obtain a {@link TimedQuery} that will periodically execute
 *     the search and allow the client to wait until the result matches its expectations, with a desired timeout - using
 *     {@link com.atlassian.pageobjects.elements.query.Poller}</li>
 * </ul>
 *
 * <h4>Example</h4>
 * Extending the previous example, the following method (presumably in a page object) searches for all the {@code span}
 * elements in a drop-down that represent users, and subsequently retrieves the username value from them and returns as
 * as {@link TimedQuery}. The subsequent call to {@code Poller} (presumably in a test) executes timed assertion that
 * the list of users contains 3 usernames ("alice", "bob" and "charlie") within the timeout of an AJAX load (expecting
 * the hypothetical drop-down to issue an AJAX call to populate its contents).
 * <p/>
 * <pre>
 * {@code
 * public TimedQuery<String> getUsernames() {
 *    return mySearch.search()
 *              .by(By.id("user-dropdown"))
 *              .by(By.tagName("li")) // can also use Elements.TAG_LI here
 *                  .filter(isVisible()) // from PageElements
 *                  .filter(hasClass("user-element")) // from PageElements
 *              .by(By.tagName("span"), hasDataAttribute("username")) // from PageElements, check for "data-username" attribute
 *              .find()
 *              .withTimeout(TimeoutType.AJAX_ACTION)
 *              .transform(getDataAttribute("username")) // from PageElements
 *              .timed()
 * }
 *
 * // ...
 * Poller.waitUntil(myDropdown.getUsernames(), Matchers.contains("alice", "bob", "charlie"));
 * }
 * </pre>
 *
 * @since 2.3
 *
 * @see SearchQuery
 */
@PublicApi
public interface PageElementSearch
{
    /**
     * @return a new search query object that allows execute the search in the current context
     */
    @Nonnull
    SearchQuery search();
}
