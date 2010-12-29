package com.atlassian.webdriver.refapp.page;

import com.atlassian.pageobjects.page.AdminHomePage;
import com.atlassian.webdriver.refapp.component.RefappHeader;

/**
 *
 */
public class RefappAdminHomePage extends RefappAbstractPage implements AdminHomePage<RefappHeader>
{

    public String getUrl()
    {
        return "/admin";
    }
}
