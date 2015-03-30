package com.atlassian.pageobjects.internal.elements.search;

import com.atlassian.annotations.Internal;
import com.atlassian.pageobjects.PageObjects;
import com.atlassian.pageobjects.elements.query.Conditions;
import com.atlassian.pageobjects.elements.query.Queries;
import com.atlassian.pageobjects.elements.query.TimedCondition;
import com.atlassian.pageobjects.elements.query.TimedQuery;
import com.atlassian.pageobjects.elements.search.SearchQuery;
import com.atlassian.pageobjects.elements.timeout.TimeoutType;
import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.google.common.collect.Iterables;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.Matchers.not;

@Internal
@NotThreadSafe
abstract class BaseResult<E, R extends SearchQuery.Result<E, R>> implements SearchQuery.Result<E, R>
{
    protected final Dependencies dependencies;
    protected final TimeoutType timeoutType;

    protected BaseResult(TimeoutType timeoutType, Dependencies dependencies)
    {
        this.dependencies = dependencies;
        this.timeoutType = timeoutType;
    }

    @Nullable
    @Override
    public E first()
    {
        return Iterables.getFirst(executeSearch(), null);
    }

    @Nonnull
    @Override
    public TimedCondition hasResult()
    {
        return Conditions.forMatcher(timed(), not(emptyIterable()));
    }

    @Nonnull
    @Override
    public Iterable<E> all()
    {
        return executeSearch();
    }

    @Nonnull
    @Override
    public TimedQuery<Iterable<E>> timed()
    {
        return Queries.forSupplier(dependencies.timeouts, supplier(), timeoutType);
    }

    @Nonnull
    @Override
    public Supplier<Iterable<E>> supplier()
    {
        return new Supplier<Iterable<E>>()
        {
            @Override
            public Iterable<E> get()
            {
                return executeSearch();
            }
        };
    }

    @Nonnull
    @Override
    public <F> SearchQuery.AnyResult<F> transform(@Nonnull Function<E, F> transformer)
    {
        return new TransformingResult<F, E>(this, transformer, timeoutType, dependencies);
    }

    @Nonnull
    @Override
    public <PO> SearchQuery.AnyResult<PO> bindTo(@Nonnull Class<PO> pageObjectClass, @Nonnull Object... extraArgs)
    {
        Function<E, PO> transformer = PageObjects.bindTo(dependencies.pageBinder, pageObjectClass, extraArgs);

        return new TransformingResult<PO, E>(this, transformer, timeoutType, dependencies);
    }

    @Nonnull
    protected abstract Iterable<E> executeSearch();

}
