package com.atlassian.pageobjects.internal.elements.search;

import com.atlassian.annotations.Internal;
import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.elements.timeout.Timeouts;
import org.openqa.selenium.WebDriver;

import javax.inject.Inject;

@Internal
public final class Dependencies
{
    @Inject
    PageBinder pageBinder;

    @Inject
    Timeouts timeouts;

    @Inject
    WebDriver webDriver;
}
