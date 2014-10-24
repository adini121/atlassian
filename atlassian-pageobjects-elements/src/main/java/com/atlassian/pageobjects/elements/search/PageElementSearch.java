package com.atlassian.pageobjects.elements.search;

import javax.annotation.Nonnull;

/**
 * Access point to advanced search within the context of the whole page, or a particular
 * {@link com.atlassian.pageobjects.elements.PageElement DOM element}.
 *
 *  TODO needs more documentation and examples.
 *
 * @since 2.3
 *
 * @see SearchQuery
 */
public interface PageElementSearch
{
    @Nonnull
    SearchQuery search();
}
