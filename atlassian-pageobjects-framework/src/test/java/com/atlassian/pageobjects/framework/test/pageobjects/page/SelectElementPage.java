package com.atlassian.pageobjects.framework.test.pageobjects.page;

import com.atlassian.pageobjects.Page;
import com.atlassian.pageobjects.framework.ElementFinder;
import com.atlassian.pageobjects.framework.element.*;
import org.openqa.selenium.By;

import javax.inject.Inject;

/**
 * Represents selectelement.html
 */
public class SelectElementPage implements Page
{
    @Inject
    ElementFinder elementFinder;

    @ElementBy(id="test1_Select")
    SelectElement select1;

    @ElementBy(id="test4_Select")
    MultiSelectElement select4;


    public String getUrl()
    {
         return "/html/selectelement.html";
    }

    public SelectElement findSelect(By locator)
    {
        return elementFinder.find(locator, WebDriverSelectElement.class);
    }

    public MultiSelectElement findMultiSelect(By locator)
    {
        return elementFinder.find(locator, WebDriverMultiSelectElement.class);
    }

    public SelectElement getSelectElement1()
    {
        return select1;
    }

    public MultiSelectElement getMultiSelectElement4()
    {
        return select4;
    }
}
