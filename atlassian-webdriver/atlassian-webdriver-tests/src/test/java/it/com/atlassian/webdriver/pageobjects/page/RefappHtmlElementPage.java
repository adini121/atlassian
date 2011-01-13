package it.com.atlassian.webdriver.pageobjects.page;

import com.atlassian.pageobjects.binder.WaitUntil;
import com.atlassian.webdriver.pageobjects.element.Element;
import com.atlassian.webdriver.pageobjects.element.ElementBy;
import com.atlassian.webdriver.utils.by.ByJquery;

/**
 * Represents the page returned by the HtmlServlet plugin that is deployed to RefApp
 */
public class RefappHtmlElementPage extends RefappAbstractPage
{
    @ElementBy(id="test1_addElementsButton")
    private Element test1_addElementsButton;

    @ElementBy(id="test1_delayedSpan")
    private Element test1_delayedSpan;

    public String getUrl()
    {
        return "/plugins/servlet/webdriver/htmlpage";
    }

     @WaitUntil
    public void doWait()
    {
        driver.waitUntilElementIsLocated(ByJquery.$("h1:contains(Html Elements Page)"));
    }

    public Element test1_addElementsButton()
    {
        return test1_addElementsButton;
    }

      public Element test1_delayedSpan()
    {
        return test1_delayedSpan;
    }

}
