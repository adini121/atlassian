package com.atlassian.pageobjects.elements.search;

import com.atlassian.annotations.PublicApi;
import com.atlassian.pageobjects.elements.PageElement;

/**
 * The default search result of {@link SearchQuery}, which returns a list of {@link PageElement page elements}.
 *
 * @since 2.3
 * @see SearchQuery
 * @see PageElementSearch
 */
@PublicApi
public interface SearchResult extends SearchQuery.PageElementResult<PageElement>
{}
