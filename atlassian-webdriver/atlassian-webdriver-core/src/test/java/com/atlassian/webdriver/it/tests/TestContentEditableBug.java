package com.atlassian.webdriver.it.tests;

import com.atlassian.pageobjects.browser.Browser;
import com.atlassian.pageobjects.browser.IgnoreBrowser;
import com.atlassian.webdriver.it.AbstractFileBasedServerTest;
import com.atlassian.webdriver.it.pageobjects.page.contenteditable.ContentEditablePage;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.assertTrue;

@IgnoreBrowser(value = {Browser.HTMLUNIT, Browser.HTMLUNIT_NOJS}, reason = "SELENIUM-165 HtmlUnit does not support contenteditable")
public class TestContentEditableBug extends AbstractFileBasedServerTest
{

    ContentEditablePage contentEditablePage;
    WebDriver driver;

    @Before
    public void init()
    {
        contentEditablePage = product.visit(ContentEditablePage.class);
        driver = product.getTester().getDriver();
    }

    @Test
    public void testEditingContentEditableIframeWorks()
    {
        WebElement el = contentEditablePage.getContentEditable();
        WebElement el2 = el.findElement(By.id("test"));
        el2.click();
        el2.sendKeys("HELLO");

        assertTrue(el2.getText().contains("HELLO"));
    }

    @Test
    public void testEditingContentEditableDivWorks()
    {
        WebElement el = driver.findElement(By.id("div-ce"));
        el.sendKeys("WORLD");
        assertTrue(el.getText().contains("WORLD"));
    }

}
