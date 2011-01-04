package com.atlassian.webdriver.jira.component.menu;

import com.atlassian.webdriver.pageobjects.components.AbstractWebDriverMenu;
import com.atlassian.webdriver.utils.Check;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Represents an AUI dropdown menu
 */
public class AuiDropdownMenu<P extends AuiDropdownMenu<P>> extends AbstractWebDriverMenu<P>
{
    /**
     * Default constructor
     * @param rootElementLocator The locator to the root element of the menu.
     */
    public AuiDropdownMenu(By rootElementLocator)
    {
        super(rootElementLocator);
    }

    @Override
    protected void activate(WebElement rootElement)
    {
        rootElement.findElement(By.cssSelector("a.drop")).click();
    }

    @Override
    protected boolean isOpen(WebElement rootElement) {
        return Check.hasClass("active", rootElement)
                && !Check.elementExists(By.className("loading"), rootElement)
                && Check.elementExists(By.tagName("li"), rootElement);
    }


}
