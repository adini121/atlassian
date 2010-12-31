package it.com.atlassian.webdriver.pageobjects.page;

import com.atlassian.pageobjects.page.AdminHomePage;
import it.com.atlassian.webdriver.pageobjects.components.RefappHeader;


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
