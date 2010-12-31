package it.com.atlassian.webdriver.pageobjects.page;

import com.atlassian.pageobjects.binder.ValidateState;
import com.atlassian.pageobjects.binder.WaitUntil;
import com.atlassian.webdriver.utils.by.ByJquery;
import org.openqa.selenium.By;

/**
 * Represents the page returned by the AUIServlet plugin that is deployed to RefApp
 */
public class RefAppAUIPage extends RefappAbstractPage {
    public String getUrl()
    {
        return "/plugins/servlet/webdriver/auipage";
    }

    @WaitUntil
    public void doWait()
    {
        driver.waitUntilElementIsLocated(ByJquery.$("h2:contains(AUI Page)"));
    }
}
