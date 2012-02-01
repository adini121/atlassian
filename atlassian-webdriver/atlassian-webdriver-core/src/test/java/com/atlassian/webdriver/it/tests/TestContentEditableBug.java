package com.atlassian.webdriver.it.tests;

import com.atlassian.pageobjects.Browser;
import com.atlassian.webdriver.it.AbstractFileBasedServerTest;
import com.atlassian.webdriver.it.pageobjects.page.contenteditable.ContentEditablePage;
import com.atlassian.webdriver.testing.annotation.IgnoreBrowser;
import com.atlassian.webdriver.testing.annotation.TestBrowser;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * @since 2.1
 */
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
    @TestBrowser("firefox")
    public void testEditingContentEditableIframeWithFirefoxDoesNotWork()
    {
        WebElement el = contentEditablePage.getContentEditable();
        WebElement el2 = el.findElement(By.id("test"));
        el2.click();
        el2.sendKeys("HELLO");

        // This is expected to fail as this is a bug in firefox driver.
        assertFalse(el2.getText().contains("HELLO"));
    }

    @Test
    @IgnoreBrowser(Browser.FIREFOX)
    public void testEditingContentEditableIframeWorks()
    {
        WebElement el = contentEditablePage.getContentEditable();
        WebElement el2 = el.findElement(By.id("test"));
        el2.click();
        el2.sendKeys("HELLO");

        // This is expected to pass for any driver that isn't firefox.
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
