package com.atlassian.pageobjects.internal.elements.search;

import com.atlassian.annotations.Internal;
import com.atlassian.pageobjects.elements.PageElement;
import com.atlassian.pageobjects.elements.WebDriverLocatable;
import com.atlassian.pageobjects.elements.search.SearchResult;
import com.atlassian.pageobjects.elements.timeout.TimeoutType;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.Deque;

@Internal
@NotThreadSafe
public class DefaultWebDriverElementResult extends WebDriverElementResult<PageElement>
        implements SearchResult
{
    DefaultWebDriverElementResult(WebDriverLocatable root, Deque<WebDriverElementSearchQuery.SearchStep> searchQueue,
                                  TimeoutType timeoutType, Dependencies dependencies)
    {
        super(root, searchQueue, PageElement.class, timeoutType, dependencies);
    }
}
