package com.atlassian.webdriver.it.tests;

import com.atlassian.webdriver.it.AbstractFileBasedServerTest;
import com.atlassian.webdriver.it.pageobjects.page.ByJqueryPage;
import com.atlassian.webdriver.utils.by.ByJquery;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Test for checking ByJquery functionality in Atlassian WebDriver.
 */
public class TestByJquery extends AbstractFileBasedServerTest
{

    ByJqueryPage byJqueryPage;
    WebDriver driver;

    @Before
    public void init()
    {
        byJqueryPage = product.getPageBinder().navigateToAndBind(ByJqueryPage.class);
        driver = product.getTester().getDriver();
    }
    
    @Test
    public void testGetHeadingByJquery()
    {
        assertEquals("By Jquery test page",driver.findElement(ByJquery.$("h1")).getText());
    }

    @Test
    public void testSimpleClassSelector()
    {
        List<WebElement> els = driver.findElements(ByJquery.$(".class1"));
        assertTrue(els.size() == 1);

        WebElement el = els.get(0);
        assertEquals("div", el.getTagName());
        assertEquals("class1", el.getAttribute("class"));
        assertEquals("Simple class test", el.getText());
    }

    @Test
    public void testSimpleIdSelector()
    {
        List<WebElement> els = driver.findElements(ByJquery.$("#id1"));
        assertTrue(els.size() == 1);

        WebElement el = els.get(0);
        assertEquals("div", el.getTagName());
        assertEquals("id1", el.getAttribute("id"));
        assertEquals("Simple ID test", el.getText());
    }

    @Test
    public void testMultipleSelector()
    {
        List<WebElement> els = driver.findElements(ByJquery.$("#id2 .innerblock"));
        assertTrue(els.size() == 1);

        WebElement el = els.get(0);
        assertEquals("div", el.getTagName());
        assertEquals("innerblock", el.getAttribute("class"));
        assertEquals("Inner block test", el.getText());
    }

    @Test
    public void testMultipleElementsByClassName()
    {
        List<WebElement> els = driver.findElements(ByJquery.$("div.block2").children());
        assertTrue(els.size() == 2);


        for (WebElement el : els)
        {
            assertEquals("span", el.getTagName());
        }
    }

    @Test(expected = NoSuchElementException.class)
    public void testSingleSelectorElementNotFoundShouldThrowException()
    {
        driver.findElement(ByJquery.$("#NonExistant"));
    }

    @Test(expected = NoSuchElementException.class)
    public void testMultipleSelectorElementNotFoundShouldThrowException()
    {
        driver.findElement(ByJquery.$("#id2 .nonExistant"));
    }
}
