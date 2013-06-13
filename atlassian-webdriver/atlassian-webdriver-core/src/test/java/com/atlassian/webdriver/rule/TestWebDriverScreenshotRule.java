package com.atlassian.webdriver.rule;

import com.atlassian.webdriver.testing.rule.WebDriverScreenshotRule;
import com.google.common.base.Suppliers;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;

import static com.atlassian.webdriver.matchers.FileMatchers.*;
import static org.apache.commons.io.FileUtils.readFileToString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Test case for {@link com.atlassian.webdriver.testing.rule.WebDriverScreenshotRule}.
 *
 * @since 2.1
 */
@RunWith(MockitoJUnitRunner.class)
public class TestWebDriverScreenshotRule
{

    @Mock private Statement mockTest;

    private WebDriver webDriver;

    @Rule public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Before
    public void initWebDriver()
    {
        webDriver = mock(WebDriver.class, withSettings().extraInterfaces(TakesScreenshot.class));
    }

    @Test
    public void shouldTakeScreenshotAndPageDump() throws Throwable
    {
        final File fakeScreenshot = temporaryFolder.newFile("fake.png");
        FileUtils.writeStringToFile(fakeScreenshot, "FAKE SCREENSHOT!");
        when(webDriver.getCurrentUrl()).thenReturn("something");
        when(webDriver.getPageSource()).thenReturn("<html>some source</html>");
        when(asTakingScreenshot().getScreenshotAs(OutputType.FILE)).thenReturn(fakeScreenshot);
        doThrow(new RuntimeException("failed")).when(mockTest).evaluate();
        final WebDriverScreenshotRule rule = new WebDriverScreenshotRule(Suppliers.ofInstance(webDriver),
                temporaryFolder.getRoot());
        final Description description = Description.createTestDescription(TestWebDriverScreenshotRule.class, "someTest");
        new SafeStatementInvoker(rule.apply(mockTest, description)).invokeSafely();
        assertThat(expectedTargetDir(), isDirectory());
        assertThat(expectedTargetFile("someTest.html"), isFile());
        assertEquals("<html>some source</html>", readFileToString(expectedTargetFile("someTest.html")));
        assertThat(expectedTargetFile("someTest.png"), isFile());
        assertEquals("FAKE SCREENSHOT!", readFileToString(expectedTargetFile("someTest.png")));

    }

    @Test
    public void shouldNotTakeScreenshotIfNotFailed() throws Throwable
    {
        when(webDriver.getCurrentUrl()).thenReturn("something");
        when(webDriver.getPageSource()).thenReturn("<html>some source</html>");
        final WebDriverScreenshotRule rule = new WebDriverScreenshotRule(Suppliers.ofInstance(webDriver),
                temporaryFolder.getRoot());
        final Description description = Description.createTestDescription(TestWebDriverScreenshotRule.class, "someTest");
        new SafeStatementInvoker(rule.apply(mockTest, description)).invokeSafely();
        verifyZeroInteractions(webDriver);
        assertThat(expectedTargetDir(), isDirectory()); // should still create the directory
        assertThat(expectedTargetFile("someTest.html"), not(exists()));
        assertThat(expectedTargetFile("someTest.png"), not(exists()));
    }

    private TakesScreenshot asTakingScreenshot()
    {
        return (TakesScreenshot) webDriver;
    }

    private File expectedTargetDir()
    {
        return new File(temporaryFolder.getRoot(), TestWebDriverScreenshotRule.class.getName());
    }

    private File expectedTargetFile(String name)
    {
        return new File(expectedTargetDir(), name);
    }


}
