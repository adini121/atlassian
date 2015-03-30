package com.atlassian.pageobjects.internal.elements.search;

import com.atlassian.annotations.Internal;
import com.atlassian.pageobjects.elements.search.SearchQuery;
import com.atlassian.pageobjects.elements.timeout.TimeoutType;
import com.google.common.base.Predicate;
import org.hamcrest.Matcher;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;

import static com.atlassian.pageobjects.internal.elements.search.WebDriverElementSearchQuery.newMatcherPredicate;

@Internal
@NotThreadSafe
abstract class BaseAnyResult<E> extends BaseResult<E, SearchQuery.AnyResult<E>>
        implements SearchQuery.AnyResult<E>
{
    BaseAnyResult(TimeoutType timeoutType, Dependencies dependencies)
    {
        super(timeoutType, dependencies);
    }

    @Nonnull
    @Override
    public SearchQuery.AnyResult<E> filter(@Nonnull Predicate<? super E> filter)
    {
        return new FilteringResult<E>(this, filter, timeoutType, dependencies);
    }

    @Nonnull
    @Override
    public SearchQuery.AnyResult<E> filter(@Nonnull Matcher<? super E> filter)
    {
        return new FilteringResult<E>(this, newMatcherPredicate(filter), timeoutType, dependencies);
    }
}
