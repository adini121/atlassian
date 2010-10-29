package com.atlassian.webdriver.utils.element;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * TODO: Document this class / interface here
 *
 * @since v1.0
 */
public class DeferredElement implements WebElement
{
    private final static int DEFERRED_WAIT = 20;

    private final By elementLocator;
    private final WebDriver driver;
    
    private WebElement element;

    public DeferredElement(By elementLocator, WebDriver driver)
    {
        this.elementLocator = elementLocator;
        this.driver = driver;
    }

    private WebElement getElement()
    {

        if (element == null)
        {
            WebDriverWait wait = new WebDriverWait(driver, DEFERRED_WAIT);
            wait.until(new ExpectedCondition<Boolean>() {

                public Boolean apply(final WebDriver from)
                {
                    try
                    {
                        element = from.findElement(elementLocator);
                    }
                    catch (NoSuchElementException e)
                    {
                        return false;
                    }

                    return true;
                }
            });
        }

        return element;
    }


    public void click()
    {
        getElement().click();
    }

    public void submit()
    {
        getElement().submit();
    }

    public String getValue()
    {
        return getElement().getValue();
    }

    public void sendKeys(final CharSequence... keysToSend)
    {
        getElement().sendKeys(keysToSend);
    }

    public void clear()
    {
        getElement().clear();
    }

    public String getTagName()
    {
        return getElement().getTagName();
    }

    public String getAttribute(final String name)
    {
        return getElement().getAttribute(name);
    }

    public boolean toggle()
    {
        return getElement().toggle();
    }

    public boolean isSelected()
    {
        return getElement().isSelected();
    }

    public void setSelected()
    {
        getElement().setSelected();
    }

    public boolean isEnabled()
    {
        return getElement().isEnabled();
    }

    public String getText()
    {
        return getElement().getText();
    }

    public List<WebElement> findElements(final By by)
    {
        return getElement().findElements(by);
    }

    public WebElement findElement(final By by)
    {
        return getElement().findElement(by);
    }
}
