package com.atlassian.pageobjects.internal.elements.search;

import com.atlassian.annotations.Internal;
import com.atlassian.pageobjects.elements.WebDriverLocators;
import com.atlassian.pageobjects.elements.timeout.TimeoutType;

import javax.annotation.concurrent.NotThreadSafe;

/**
 * @since 2.3
 */
@Internal
@NotThreadSafe
public class GlobalPageElementSearch extends WebDriverPageElementSearch
{
    public GlobalPageElementSearch()
    {
        super(WebDriverLocators.root(), TimeoutType.DEFAULT);
    }
}
