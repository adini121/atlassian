package com.atlassian.webdriver.jira.component.menu;

import com.atlassian.webdriver.Link;
import com.atlassian.webdriver.Linkable;
import com.atlassian.webdriver.PageObject;
import com.atlassian.webdriver.component.AbstractComponent;
import com.atlassian.webdriver.jira.JiraTestedProduct;
import com.atlassian.webdriver.product.TestedProduct;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


/**
 *
 */
public class AdminSideMenu extends AbstractComponent implements Linkable
{
    private static final By ADMIN_SIDE_MENU_LOCATOR = By.id("adminMenu");

    private WebElement sideMenuContainer;

    public AdminSideMenu(JiraTestedProduct testedProduct)
    {
        super(testedProduct);
    }

    @Override
    public void initialise()
    {
        super.initialise();
        sideMenuContainer = getDriver().findElement(ADMIN_SIDE_MENU_LOCATOR);
    }

    public <T extends PageObject> T gotoPage(Link<T> link) {
        return link.activate(sideMenuContainer, getTestedProduct());
    }
}
