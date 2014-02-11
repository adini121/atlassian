package com.atlassian.webdriver.it.tests;

import com.atlassian.pageobjects.Page;
import com.atlassian.pageobjects.browser.Browser;
import com.atlassian.pageobjects.browser.RequireBrowser;
import com.atlassian.webdriver.it.AbstractSimpleServerTest;
import com.atlassian.webdriver.it.pageobjects.page.jsconsolelogging.IncludedScriptErrorPage;
import com.atlassian.webdriver.it.pageobjects.page.jsconsolelogging.NoErrorsPage;
import com.atlassian.webdriver.it.pageobjects.page.jsconsolelogging.WindowErrorPage;
import com.atlassian.webdriver.testing.rule.LogConsoleOutputRule;
import com.atlassian.webdriver.utils.element.WebDriverPoller;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class TestLogConsoleOutput extends AbstractSimpleServerTest
{
    private WebDriver driver;
    private LogConsoleOutputRule rule;
    @Inject private WebDriverPoller poller;
    @Inject private Logger logger;

    @Before
    public void setup()
    {
        poller = poller.withDefaultTimeout(5, TimeUnit.SECONDS);
        driver = product.getTester().getDriver();
        rule = new LogConsoleOutputRule(driver, logger);
    }

    @Test
    @RequireBrowser(Browser.FIREFOX)
    public void testPageWithNoErrors()
    {
        product.visit(NoErrorsPage.class);
        final String consoleOutput = rule.getConsoleOutput();
        assertThat(consoleOutput, equalTo("[]"));
    }

    @Test
    @RequireBrowser(Browser.FIREFOX)
    public void testSingleErrorInWindowScope()
    {
        final Page page = product.visit(WindowErrorPage.class);
        final String consoleOutput = rule.getConsoleOutput();
        assertThat(consoleOutput, containsString("ReferenceError: foo is not defined"));
        assertThat(consoleOutput, containsString(page.getUrl()));
    }

    @Test
    @RequireBrowser(Browser.FIREFOX)
    public void testMultipleErrorsInIncludedScripts()
    {
        final IncludedScriptErrorPage page = product.visit(IncludedScriptErrorPage.class);
        final String consoleOutput = rule.getConsoleOutput();
        assertThat(consoleOutput, containsString("TypeError: $ is not a function"));
        assertThat(consoleOutput, containsString(page.objectIsNotFunctionScriptUrl()));

        assertThat(consoleOutput, containsString("Error: throw Error('bail')"));
        assertThat(consoleOutput, containsString(page.throwErrorObjectScriptUrl()));
    }
}
