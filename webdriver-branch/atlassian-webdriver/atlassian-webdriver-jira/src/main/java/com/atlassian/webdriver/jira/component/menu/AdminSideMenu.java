package com.atlassian.webdriver.jira.component.menu;

import com.atlassian.webdriver.jira.component.link.AdminSideMenuPageLinks;
import com.atlassian.webdriver.component.link.Link;
import com.atlassian.webdriver.component.link.LinkNavigator;
import com.atlassian.webdriver.component.link.PageLinkFactory;
import com.atlassian.webdriver.page.Page;
import org.openqa.selenium.WebDriver;

import java.util.Map;


/**
 *
 */
public class AdminSideMenu extends LinkNavigator
{
    static
    {
        AdminSideMenu.addPageLinks(AdminSideMenuPageLinks.getPageLinks());
    }

    public AdminSideMenu(WebDriver driver)
    {
        super(driver);
    }

    public static void addPageLinks(Map<Link, Page> pageLinks)
    {
        PageLinkFactory.add(pageLinks);
    }


}
