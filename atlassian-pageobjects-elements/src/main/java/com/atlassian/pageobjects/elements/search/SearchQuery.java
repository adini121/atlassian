package com.atlassian.pageobjects.elements.search;

import com.atlassian.annotations.PublicApi;
import com.atlassian.pageobjects.elements.PageElement;
import com.atlassian.pageobjects.elements.query.TimedQuery;
import com.atlassian.pageobjects.elements.timeout.TimeoutType;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Supplier;
import org.hamcrest.Matcher;
import org.openqa.selenium.By;

import javax.annotation.Nonnull;

/**
 * Represents a hierarchical query to find one or more DOM elements on the tested page.
 *
 * @since 2.3
 * @see PageElementSearch
 */
@PublicApi
public interface SearchQuery<P extends PageElement>
{
    @Nonnull
    SearchQuery<P> by(@Nonnull By by);

    @Nonnull
    SearchQuery<P> by(@Nonnull By by, @Nonnull Predicate<? super P> filter);

    @Nonnull
    SearchQuery<P> filter(@Nonnull Predicate<? super P> filter);

    @Nonnull
    SearchQuery<P> filter(@Nonnull Matcher<? super P> filter);

    @Nonnull
    PageElementResult<P> find();

    interface Result<E, R extends Result<E, R>>
    {
        @Nonnull
        E first();

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

    interface AnyResult<E> extends Result<E, AnyResult<E>> {}

    interface PageElementResult<PE extends PageElement> extends Result<PE, PageElementResult<PE>>
    {
        @Nonnull
        PageElementResult<PE> withTimeout(@Nonnull TimeoutType timeoutType);

        @Nonnull
        <PEE extends PE> PageElementResult<PEE> as(@Nonnull Class<PEE> pageElementClass);
    }
}
