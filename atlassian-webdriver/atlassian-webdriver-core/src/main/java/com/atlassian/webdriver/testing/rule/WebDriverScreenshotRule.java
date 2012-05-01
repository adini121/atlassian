package com.atlassian.webdriver.testing.rule;

import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.LifecycleAwareWebDriverGrid;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * A rule for taking screenshots when a webdriver test fails.
 * It will also dump the html source of the page to the target/webDriverTests directory.
 *
 * @since 2.1.0
 */
public class WebDriverScreenshotRule extends TestWatcher
{
    private static final Logger log = LoggerFactory.getLogger(WebDriverScreenshotRule.class);

    private String destinationFolder;

    @Override
    public void starting(final Description description)
    {
        destinationFolder = "target/webdriverTests/" + description.getClassName();
        File dir = new File(destinationFolder);
        if (!dir.exists())
        {
            dir.mkdirs();
        }
    }

    @Override
    public void failed(final Throwable e, final Description description)
    {
        final AtlassianWebDriver driver = LifecycleAwareWebDriverGrid.getCurrentDriver();
        final String baseFileName = destinationFolder + File.separator + description.getMethodName();
        final File dumpFile = new File(baseFileName + ".html");
        log.error(e.getMessage(), e);
        log.info("----- Test Failed. " + e.getMessage());
        log.info("----- At page: " + driver.getCurrentUrl());
        log.info("----- Dumping page source to: " + dumpFile.getAbsolutePath());

        // Take a screen shot and dump it.
        driver.dumpSourceTo(dumpFile);
        driver.takeScreenshotTo(new File(baseFileName + ".png"));
    }

}
