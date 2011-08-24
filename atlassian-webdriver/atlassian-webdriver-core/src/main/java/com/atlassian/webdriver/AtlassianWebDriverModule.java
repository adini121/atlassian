package com.atlassian.webdriver;

import com.atlassian.pageobjects.TestedProduct;
import com.atlassian.webdriver.pageobjects.PageFactoryPostInjectionProcessor;
import com.atlassian.webdriver.pageobjects.WebDriverTester;
import com.atlassian.webdriver.poller.Poller;
import com.atlassian.webdriver.poller.webdriver.WebDriverPoller;
import com.google.inject.Binder;
import com.google.inject.Module;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;

/**
 *
 */
public class AtlassianWebDriverModule implements Module
{
    private final TestedProduct<? extends WebDriverTester> testedProduct;

    public AtlassianWebDriverModule(TestedProduct<? extends WebDriverTester> testedProduct)
    {
        this.testedProduct = testedProduct;
    }

    public void configure(Binder binder)
    {
        binder.bind(AtlassianWebDriver.class).toInstance(testedProduct.getTester().getDriver());
        binder.bind(WebDriver.class).toInstance(testedProduct.getTester().getDriver());
        binder.bind(SearchContext.class).toInstance(testedProduct.getTester().getDriver());
        binder.bind(PageFactoryPostInjectionProcessor.class);
        binder.bind(Poller.class).to(WebDriverPoller.class);
    }
}
