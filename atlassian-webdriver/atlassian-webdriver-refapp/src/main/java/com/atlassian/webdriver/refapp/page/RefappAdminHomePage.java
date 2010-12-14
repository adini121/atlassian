package com.atlassian.webdriver.refapp.page;

import com.atlassian.webdriver.page.AdminHomePage;
import com.atlassian.webdriver.refapp.RefappTestedProduct;

import java.net.URI;
import java.net.URISyntaxException;

/**
 *
 */
public class RefappAdminHomePage extends RefappAbstractPage
{

    public String getUrl()
    {
        return "/admin";
    }
}
