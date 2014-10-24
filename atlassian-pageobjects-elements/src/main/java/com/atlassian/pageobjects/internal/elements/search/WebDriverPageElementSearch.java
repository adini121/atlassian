package com.atlassian.pageobjects.internal.elements.search;

import com.atlassian.annotations.Internal;
import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.elements.WebDriverLocatable;
import com.atlassian.pageobjects.elements.search.PageElementSearch;
import com.atlassian.pageobjects.elements.search.SearchQuery;

import javax.annotation.Nonnull;
import javax.inject.Inject;

/**
 * {@code WebDriver}-based implementation of {@link PageElementSearch}.
 *
 * @since 2.3
 */
@Internal
public class WebDriverPageElementSearch implements PageElementSearch
{
    @Inject
    private PageBinder pageBinder;

    private final WebDriverLocatable root;

    public WebDriverPageElementSearch(WebDriverLocatable root)
    {
        this.root = root;
    }

    @Nonnull
    @Override
    public SearchQuery search()
    {
        return pageBinder.bind(WebDriverElementSearchQuery.class, root);
    }
}
