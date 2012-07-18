package com.atlassian.webdriver.rule;

import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.matchers.FileMatchers;
import com.atlassian.webdriver.testing.rule.WebDriverScreenshotRule;
import com.google.common.base.Suppliers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;

import static com.atlassian.webdriver.matchers.FileMatchers.hasAbsolutePath;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * Test case for {@link com.atlassian.webdriver.testing.rule.WebDriverScreenshotRule}.
 *
 * @since 2.1
 */
@RunWith(MockitoJUnitRunner.class)
public class TestWebDriverScreenshotRule
{

    @Mock private AtlassianWebDriver atlassianWebDriver;
    @Mock private Statement mockTest;

    @Rule public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void shouldTakeScreenshot() throws Throwable
    {
        when(atlassianWebDriver.getCurrentUrl()).thenReturn("something");
        doThrow(new RuntimeException("failed")).when(mockTest).evaluate();
        final WebDriverScreenshotRule rule = new WebDriverScreenshotRule(Suppliers.ofInstance(atlassianWebDriver),
                temporaryFolder.getRoot());
        final Description description = Description.createTestDescription(TestWebDriverScreenshotRule.class, "someTest");
        new SafeStatementInvoker(rule.apply(mockTest, description)).invokeSafely();
        verify(atlassianWebDriver).dumpSourceTo(argThat(
                hasAbsolutePath(expectedTargetDir().getAbsolutePath() + File.separator + "someTest.html")));
        verify(atlassianWebDriver).takeScreenshotTo(argThat(
                hasAbsolutePath(expectedTargetDir().getAbsolutePath() + File.separator + "someTest.png")));
        assertThat(expectedTargetDir(), FileMatchers.isDirectory());
    }

    @Test
    public void shouldNotTakeScreenshotIfNotFailed() throws Throwable
    {
        when(atlassianWebDriver.getCurrentUrl()).thenReturn("something");
        final WebDriverScreenshotRule rule = new WebDriverScreenshotRule(Suppliers.ofInstance(atlassianWebDriver),
                temporaryFolder.getRoot());
        final Description description = Description.createTestDescription(TestWebDriverScreenshotRule.class, "someTest");
        new SafeStatementInvoker(rule.apply(mockTest, description)).invokeSafely();
        verifyZeroInteractions(atlassianWebDriver);
        assertThat(expectedTargetDir(), FileMatchers.isDirectory()); // should still create the directory
    }

    private File expectedTargetDir()
    {
        return new File(temporaryFolder.getRoot(), TestWebDriverScreenshotRule.class.getName());
    }


}
