package com.atlassian.pageobjects.elements.test;

import com.atlassian.pageobjects.Browser;
import com.atlassian.pageobjects.elements.GlobalElementFinder;
import com.atlassian.pageobjects.elements.PageElement;
import com.atlassian.pageobjects.elements.PageElementActions;
import com.atlassian.pageobjects.elements.PageElementFinder;
import com.atlassian.pageobjects.elements.test.pageobjects.page.ElementsPage;
import com.atlassian.webdriver.testing.annotation.IgnoreBrowser;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import javax.inject.Inject;

import static junit.framework.Assert.assertNotNull;

@IgnoreBrowser(Browser.HTMLUNIT_NOJS)
public class TestPageElementActions extends AbstractFileBasedServerTest
{
    private PageElementFinder elementFinder;

    @Before
    public void initFinder()
    {
        elementFinder = product.getPageBinder().bind(GlobalElementFinder.class);
    }

    @Test
    public void shouldGetActionsViaContext()
    {
        product.visit(ElementsPage.class);
        final PageElementActions actions = product.getPageBinder().bind(PageObjectWithActions.class).actions;
        assertNotNull(actions);
        testActions(actions);
    }

    @Test
    public void shouldGetActionsViaPageBinder()
    {
        product.visit(ElementsPage.class);
        final PageElementActions actions = product.getPageBinder().bind(PageElementActions.class);
        assertNotNull(actions);
        testActions(actions);
    }

    private void testActions(PageElementActions actions)
    {
        final PageElement firstButton = elementFinder.find(By.id("test1_addElementsButton"));
        final PageElement secondButton = elementFinder.find(By.id("test2_toggleInputVisibility"));
        final PageElement input = elementFinder.find(By.id("test5_input"));
        // just make sure it won't blow for now
        actions.moveToElement(firstButton).click()
                .click(secondButton)
                .clickAndHold()
                .release()
                .clickAndHold(firstButton)
                .release(firstButton)
                .contextClick(secondButton)
                .doubleClick()
                .doubleClick(firstButton)
                .dragAndDrop(firstButton, secondButton)
                .dragAndDropBy(secondButton, 10, 10)
                .keyDown(Keys.ALT)
                .keyUp(Keys.ALT)
                .keyDown(firstButton, Keys.CONTROL)
                .keyUp(firstButton, Keys.CONTROL)
                .moveByOffset(100, 100)
                .moveToElement(firstButton)
                .moveToElement(secondButton, 10, 10)
                .click(input)
                .sendKeys("blah")
                .click(firstButton)
                .sendKeys(input, "moreblah")
                .perform();
    }

    public static class PageObjectWithActions
    {
        @Inject
        private PageElementActions actions;
    }

}
