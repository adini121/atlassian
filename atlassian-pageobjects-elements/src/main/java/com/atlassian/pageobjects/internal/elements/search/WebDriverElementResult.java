package com.atlassian.pageobjects.internal.elements.search;

import com.atlassian.annotations.Internal;
import com.atlassian.pageobjects.elements.PageElement;
import com.atlassian.pageobjects.elements.WebDriverElement;
import com.atlassian.pageobjects.elements.WebDriverLocatable;
import com.atlassian.pageobjects.elements.search.SearchQuery;
import com.atlassian.pageobjects.elements.timeout.TimeoutType;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.hamcrest.Matcher;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;
import java.util.Deque;

import static com.atlassian.pageobjects.internal.elements.search.WebDriverElementSearchQuery.newMatcherPredicate;

@Internal
@NotThreadSafe
class WebDriverElementResult<PE extends PageElement> extends BaseResult<PE, SearchQuery.PageElementResult<PE>>
        implements SearchQuery.PageElementResult<PE>
{
    protected final Predicate<? super PE> filter;
    protected final Class<PE> pageElementClass;
    protected final WebDriverLocatable root;
    protected final Deque<WebDriverElementSearchQuery.SearchStep> searchQueue;

    WebDriverElementResult(WebDriverLocatable root, Deque<WebDriverElementSearchQuery.SearchStep> searchQueue,
                           Class<PE> pageElementClass, TimeoutType timeoutType, Dependencies dependencies)
    {
        this(root, searchQueue, pageElementClass, timeoutType, Predicates.alwaysTrue(), dependencies);
    }

    WebDriverElementResult(WebDriverLocatable root, Deque<WebDriverElementSearchQuery.SearchStep> searchQueue,
                           Class<PE> pageElementClass, TimeoutType timeoutType, Predicate<? super PE> filter,
                           Dependencies dependencies)
    {
        super(timeoutType, dependencies);
        this.root = root;
        this.pageElementClass = pageElementClass;
        this.filter = filter;
        this.searchQueue = Lists.newLinkedList(searchQueue);
    }

    @Nonnull
    @Override
    public SearchQuery.PageElementResult<PE> withTimeout(@Nonnull TimeoutType timeoutType)
    {
        return new WebDriverElementResult<>(root, searchQueue, pageElementClass, timeoutType, filter, dependencies);
    }

    @Nonnull
    @Override
    public <PEE extends PE> SearchQuery.PageElementResult<PEE> as(@Nonnull Class<PEE> pageElementClass)
    {
        return new WebDriverElementResult<>(root, searchQueue, pageElementClass, timeoutType, filter, dependencies);
    }

    @Nonnull
    @Override
    public SearchQuery.PageElementResult<PE> filter(@Nonnull Predicate<? super PE> filter)
    {
        return new WebDriverElementResult<>(root, searchQueue, pageElementClass, timeoutType,
                Predicates.and(this.filter, filter), dependencies);
    }

    @Nonnull
    @Override
    public SearchQuery.PageElementResult<PE> filter(@Nonnull Matcher<? super PE> filter)
    {
        return filter(newMatcherPredicate(filter));
    }

    @Nonnull
    protected Iterable<PE> executeSearch() {
        Iterable<WebDriverLocatable> results = ImmutableList.of(root);
        for (WebDriverElementSearchQuery.SearchStep searchStep : searchQueue) {
            ImmutableList.Builder<WebDriverLocatable> newResults = ImmutableList.builder();
            for (WebDriverLocatable parent : results) {
                newResults.addAll(searchStep.apply(parent));
            }
            results = newResults.build();
        }

        return FluentIterable.from(results)
                .transform(WebDriverElement.bind(dependencies.pageBinder, pageElementClass, timeoutType))
                .filter(filter)
                .toList();
    }
}
