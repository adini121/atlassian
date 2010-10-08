package com.atlassian.webdriver.jira.component.menu;

import com.atlassian.webdriver.Linkable;
import com.atlassian.webdriver.PageObject;
import com.atlassian.webdriver.jira.JiraTestedProduct;
import com.atlassian.webdriver.product.TestedProduct;


/**
 *
 */
public class AdminSideMenu implements Linkable
{
    private final JiraTestedProduct testedProduct;

    public AdminSideMenu(JiraTestedProduct testedProduct)
    {
        this.testedProduct = testedProduct;
    }

    public <T extends PageObject> T gotoPage(com.atlassian.webdriver.Link<T> link) {
        return link.activate(testedProduct);
    }
}
