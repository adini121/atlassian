package com.atlassian.pageobjects.internal.elements.search;

import com.atlassian.annotations.Internal;
import com.atlassian.pageobjects.elements.search.SearchQuery;
import com.atlassian.pageobjects.elements.timeout.TimeoutType;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;

@Internal
@NotThreadSafe
class TransformingResult<E, O> extends BaseAnyResult<E>
{
    protected final SearchQuery.Result<O, ?> original;
    protected final Function<? super O, ? extends E> transformer;

    TransformingResult(SearchQuery.Result<O, ?> original, Function<? super O, ? extends E> transformer,
                       TimeoutType timeoutType, Dependencies dependencies)
    {
        super(timeoutType, dependencies);
        this.original = original;
        this.transformer = transformer;
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    protected Iterable<E> executeSearch()
    {
        // FluentIterable API fail...
        return (Iterable) FluentIterable.from(original.all()).transform(transformer).toList();
    }
}
