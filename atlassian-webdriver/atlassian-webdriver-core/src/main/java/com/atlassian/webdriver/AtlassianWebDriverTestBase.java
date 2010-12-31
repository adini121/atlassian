package com.atlassian.webdriver;

import com.atlassian.webdriver.browsers.AutoInstallConfiguration;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.MethodRule;
import org.junit.rules.TestWatchman;
import org.junit.runners.model.FrameworkMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;


/**
 * TODO: Document this class / interface here
 *
 * @since v1.0
 */
public abstract class AtlassianWebDriverTestBase
{
    private final Logger log = LoggerFactory.getLogger(AtlassianWebDriverTestBase.class);

    protected static AtlassianWebDriver driver;

    @Rule
    public MethodRule rule = new TestWatchman() {

        private String destinationFolder;

        @Override
        public void starting(final FrameworkMethod method)
        {
            log.info("----- Starting " + method.getName());

            destinationFolder = "target/webdriverTests/" + method.getMethod().getDeclaringClass().getName();

            File dir = new File(destinationFolder);
            // Clean up the directory for the next run
            if (dir.exists()) {
                dir.delete();
            }

            dir.mkdirs();
        }

        @Override
        public void succeeded(final FrameworkMethod method)
        {
            log.info("----- Succeeded " + method.getName());
        }

        @Override
        public void failed(final Throwable e, final FrameworkMethod method)
        {
            String baseFileName =  destinationFolder + "/" + method.getName();
            File dumpFile = new File(baseFileName + ".html");
            log.error(e.getMessage(), e);
            log.info("----- Test Failed. " + e.getMessage());
            log.info("----- At page: "+ driver.getCurrentUrl());
            log.info("----- Dumping page source to: " + dumpFile.getAbsolutePath());

            // Take a screen shot and dump it.
            driver.dumpSourceTo(dumpFile);
            driver.takeScreenshotTo(new File(baseFileName + ".png"));
            
        }

        @Override
        public void finished(final FrameworkMethod method)
        {
            log.info("----- Finished " + method.getName());
        }
    };

    @After
    public void tearDown() {
        if (driver != null) {
            driver.manage().deleteAllCookies();
        }
    }

    @BeforeClass
    public static void startUp() {
        driver = WebDriverFactory.getDriver(AutoInstallConfiguration.setupBrowser());
    }

    @AfterClass
    public static void cleanUp() throws Exception {
        driver.quit();
        driver = null;
    }

}
