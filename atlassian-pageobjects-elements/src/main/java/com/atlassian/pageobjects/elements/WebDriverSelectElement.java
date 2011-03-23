package com.atlassian.pageobjects.elements;

import com.atlassian.pageobjects.elements.timeout.TimeoutType;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link SelectElement}
 */
public class WebDriverSelectElement extends WebDriverElement implements SelectElement
{
     public WebDriverSelectElement(By locator)
    {
        super(locator);
    }

    public WebDriverSelectElement(By locator, TimeoutType defaultTimeout)
    {
        super(locator, defaultTimeout);
    }

    public WebDriverSelectElement(WebElement webElement)
    {
        super(webElement);
    }

    public WebDriverSelectElement(By locator, SearchContext searchContext)
    {    
        super(locator, searchContext);
    }

    private Option buildOption(WebElement option)
    {
        return Options.full(
            option.getAttribute("id"),
            option.getAttribute("value"),
            option.getText());
    }

    public List<Option> all()
    {
        List<Option> optionList = new ArrayList<Option>();

        for(WebElement option: new Select(waitForWebElement()).getOptions())
        {
            optionList.add(buildOption(option));
        }

        return optionList;
    }

    public Option selected()
    {
        return buildOption(new Select(waitForWebElement()).getFirstSelectedOption());
    }

    public SelectElement select(Option option)
    {
        for(WebElement currentOption: new Select(waitForWebElement()).getOptions())
        {
            if(option.equals(buildOption(currentOption)))
            {
                currentOption.setSelected();
                break;
            }
        }

        return this;
    }
}
