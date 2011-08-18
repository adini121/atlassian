package com.atlassian.webdriver.it.pageobjects.page;

import com.atlassian.pageobjects.Page;
import com.atlassian.webdriver.AtlassianWebDriver;

import javax.inject.Inject;

/**
 * @since v2.1
 */
public class ByJqueryPage implements Page
{
    @Inject
    AtlassianWebDriver driver;

    public String getUrl()
    {
        return "/html/byjquery.html";
    }
}
