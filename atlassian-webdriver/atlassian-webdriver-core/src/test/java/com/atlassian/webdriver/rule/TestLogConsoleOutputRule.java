package com.atlassian.webdriver.rule;

import com.atlassian.webdriver.testing.rule.LogConsoleOutputRule;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Test case for {@link com.atlassian.webdriver.testing.rule.LogConsoleOutputRule}.
 *
 * @since 2.3
 */
@RunWith(MockitoJUnitRunner.class)
public class TestLogConsoleOutputRule
{
    @Mock private Logger mockLogger;

    private List<String> errorsFound;
    private boolean failOnErrorsFound;

    @Before
    public void setUp()
    {
        errorsFound = Lists.newArrayList();
        failOnErrorsFound = false;
    }

    @Test
    public void testOutputsSpecialMessageWhenCannotRetrieveConsoleErrorsOnTestFailed() throws Exception
    {
        final WebDriver driver = mock(WebDriver.class);
        final LogConsoleOutputRule rule = createRule(driver);

        rule.finished(Description.createTestDescription(TestLogConsoleOutputRule.class, "testMethod"));
        verify(mockLogger).info("<Console output only supported in Firefox right now, sorry!>");
        verifyNoMoreInteractions(mockLogger);
    }

    @Test
    public void testLogsWhenNoErrorsFound()
    {
        final FirefoxDriver driver = mock(FirefoxDriver.class);
        final LogConsoleOutputRule rule = createRule(driver);

        rule.finished(Description.createTestDescription(TestLogConsoleOutputRule.class, "testMethod"));
        verify(mockLogger).info("----- Test '{}' finished with {} JS error(s). ", "testMethod", 0);
        verifyNoMoreInteractions(mockLogger);
    }

    @Test
    public void testCanBeOverriddenToFailTestWhenErrorsFound()
    {
        final FirefoxDriver driver = mock(FirefoxDriver.class);
        final LogConsoleOutputRule rule = createRule(driver);

        errorsFound.add("TypeError: $ is not a function");
        failOnErrorsFound = true;

        try
        {
            rule.finished(Description.createTestDescription(TestLogConsoleOutputRule.class, "testMethod"));
            assertThat("Expected an exception to be thrown, but wasn't", true, equalTo(false));
        }
        catch (RuntimeException e)
        {
            assertThat(e.getMessage(), containsString("Test failed due to javascript errors being detected"));
        }
        verify(mockLogger).info("----- Test '{}' finished with {} JS error(s). ", "testMethod", 1);
    }

    private LogConsoleOutputRule createRule(final WebDriver driver)
    {
        return new LogConsoleOutputRule(driver, mockLogger)
        {
            @Override
            protected boolean shouldFailOnJavaScriptErrors()
            {
                return failOnErrorsFound;
            }

            @Override
            protected List<String> errors()
            {
                return errorsFound;
            }
        };
    }
}
