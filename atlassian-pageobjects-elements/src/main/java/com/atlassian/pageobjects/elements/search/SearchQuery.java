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
 * TODO needs more documentation and examples.
 *
 * @since 2.3
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
    Result<P> find();

    interface Result<PP>
    {
        @Nonnull
        PP first();

        @Nonnull
        Iterable<PP> all();

        @Nonnull
        TimedQuery<Iterable<PP>> timed();

        @Nonnull
        Supplier<Iterable<PP>> supplier();

        @Nonnull
        <PT> Result<PP> bind(@Nonnull Class<PT> pageObjectClass, @Nonnull Object... extraArgs);

        @Nonnull
        Result<PP> filter(@Nonnull Predicate<? super PP> filter);

        @Nonnull
        Result<PP> filter(@Nonnull Matcher<? super PP> filter);

        @Nonnull
        <PT> Result<PT> transform(@Nonnull Function<PP, PT> transformer);

        @Nonnull
        Result<PP> withTimeout(@Nonnull TimeoutType timeoutType);
    }
}
