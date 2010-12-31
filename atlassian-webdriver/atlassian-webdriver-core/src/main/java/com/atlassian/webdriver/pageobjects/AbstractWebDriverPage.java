package com.atlassian.webdriver.pageobjects;

import com.atlassian.pageobjects.Page;
import com.atlassian.pageobjects.binder.WaitUntil;
import com.atlassian.webdriver.utils.JavaScriptUtils;
import com.atlassian.webdriver.utils.by.ByHelper;

/**
 * Represents any page driven by Selenium WebDriver
 */
public abstract class AbstractWebDriverPage extends AbstractWebDriverPageObject implements Page
{
    @WaitUntil
    public void doWait()
    {
        driver.waitUntilElementIsLocated(ByHelper.BODY_TAG);
    }

    /**
     * Maximizes the page
     */
    public void maximize()
    {
        JavaScriptUtils.execute("if (window.screen){window.moveTo(0, 0);window.resizeTo(window.screen.availWidth,window.screen.availHeight);};", driver);
    }
}
