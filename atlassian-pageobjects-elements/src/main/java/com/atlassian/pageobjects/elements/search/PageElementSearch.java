package com.atlassian.pageobjects.elements.search;

import com.atlassian.pageobjects.elements.PageElement;

import javax.annotation.Nonnull;

/**
 * Access point to advanced search withing the context of the whole page, or a particular
 * {@link com.atlassian.pageobjects.elements.PageElement DOM element}.
 *
 * @since 2.3
 *
 * @see SearchQuery
 */
public interface PageElementSearch
{
    @Nonnull
    SearchQuery<PageElement> search();

    @Nonnull
    <P extends PageElement> SearchQuery<P> searchFor(@Nonnull Class<P> elementClass);
}
