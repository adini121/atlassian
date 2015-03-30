package com.atlassian.pageobjects.internal.elements.search;

import com.atlassian.annotations.Internal;
import com.atlassian.pageobjects.elements.search.SearchQuery;
import com.atlassian.pageobjects.elements.timeout.TimeoutType;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;

@Internal
@NotThreadSafe
class FilteringResult<E> extends BaseAnyResult<E>
{
    protected final SearchQuery.Result<E, ?> original;
    protected final Predicate<? super E> filter;

    FilteringResult(SearchQuery.Result<E, ?> original, Predicate<? super E> filter, TimeoutType timeoutType,
                    Dependencies dependencies)
    {
        super(timeoutType, dependencies);
        this.original = original;
        this.filter = filter;
    }

    @Nonnull
    @Override
    protected Iterable<E> executeSearch()
    {
        return FluentIterable.from(original.all()).filter(filter).toList();
    }
}
