package com.atlassian.pageobjects.elements.search;


import com.atlassian.pageobjects.elements.query.TimedCondition;
import com.atlassian.pageobjects.elements.query.TimedQuery;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Supplier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 */
public interface GenericQuery<E, Q extends GenericQuery<E, Q>> extends Supplier<Iterable<E>>
{
    // generic transforms/filters
    @Nonnull
    Q filter(@Nonnull Predicate<? super E> predicate);

    @Nonnull
    <F> AnyQuery<F> map(@Nonnull Function<? super E, F> mapper);

    @Nonnull
    <F> AnyQuery<F> flatMap(@Nonnull Function<? super E, Iterable<F>> mapper);

    @Nonnull
    <F> AnyQuery<F> bindTo(@Nonnull Class<F> pageObjectClass, @Nonnull Object... extraArgs);



    // generic results (+ get() from supplier)
    @Nullable
    E first();

    @Nonnull
    Iterable<E> now();

    @Nonnull
    TimedCondition hasResult();

    @Nonnull
    TimedQuery<Iterable<E>> timed();
}
