package com.atlassian.webdriver.rule;

import com.atlassian.webdriver.testing.rule.LogConsoleOutputRule;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;

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

    @Test
    public void testOutputsSpecialMessageWhenCannotRetrieveConsoleErrors() throws Exception
    {
        final WebDriver driver = mock(WebDriver.class);
        final LogConsoleOutputRule rule = createRule(driver);

        rule.failed(new RuntimeException(), Description.createTestDescription(TestLogConsoleOutputRule.class, "testMethod"));
        verify(mockLogger).info("----- Test '{}' Failed. ", "testMethod");
        verify(mockLogger).info("----- START CONSOLE OUTPUT DUMP\n\n\n{}\n\n\n", "<Console output only supported in Firefox right now, sorry!>");
        verify(mockLogger).info("----- END CONSOLE OUTPUT DUMP");
        verifyNoMoreInteractions(mockLogger);
    }

    private LogConsoleOutputRule createRule(final WebDriver driver)
    {
        return new LogConsoleOutputRule(driver, mockLogger);
    }
}
