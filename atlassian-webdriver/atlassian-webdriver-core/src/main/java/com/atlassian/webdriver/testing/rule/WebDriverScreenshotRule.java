package com.atlassian.webdriver.testing.rule;

import com.atlassian.webdriver.AtlassianWebDriver;
import com.google.common.base.Supplier;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.io.File;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * A rule for taking screen-shots when a WebDriver test fails. It will also dump the html source of the page to
 * the target/webDriverTests directory.
 *
 * @since 2.1
 */
public class WebDriverScreenshotRule extends TestWatcher
{
    private static final Logger log = LoggerFactory.getLogger(WebDriverScreenshotRule.class);

    private final WebDriverSupport<AtlassianWebDriver> webDriverSupport;
    private final File artifactDir;

    private static File defaultArtifactDir()
    {
        return new File("target/webdriverTests");
    }

    protected WebDriverScreenshotRule(@Nonnull WebDriverSupport<AtlassianWebDriver> support, @Nonnull File artifactDir)
    {
        this.webDriverSupport = checkNotNull(support, "support");
        this.artifactDir = artifactDir;
    }

    public WebDriverScreenshotRule(@Nonnull Supplier<? extends AtlassianWebDriver> driverSupplier, @Nonnull File artifactDir)
    {
        this(WebDriverSupport.forSupplier(driverSupplier), artifactDir);
    }

    public WebDriverScreenshotRule(@Nonnull Supplier<? extends AtlassianWebDriver> driverSupplier)
    {
        this(driverSupplier, defaultArtifactDir());
    }

    @Inject
    public WebDriverScreenshotRule(@Nonnull AtlassianWebDriver webDriver)
    {
        this(WebDriverSupport.forInstance(webDriver), defaultArtifactDir());
    }

    public WebDriverScreenshotRule()
    {
        this(WebDriverSupport.fromAutoInstall(), defaultArtifactDir());
    }



    @Override
    protected void starting(final Description description)
    {
        File dir = getTargetDir(description);
        if (!dir.exists())
        {
            checkState(dir.mkdirs(), "Unable to create screenshot output directory " + dir.getAbsolutePath());
        }
    }

    @Override
    protected void failed(final Throwable e, final Description description)
    {
        final AtlassianWebDriver driver = webDriverSupport.getDriver();
        final File dumpFile = getTargetFile(description, "html");
        final File screenShotFile = getTargetFile(description, "png");
        log.info("----- {} failed. ", description.getDisplayName());
        log.info("----- At page: " + driver.getCurrentUrl());
        log.info("----- Dumping page source to {} and screenshot to {}", dumpFile.getAbsolutePath(),
                screenShotFile.getAbsolutePath());
        driver.dumpSourceTo(dumpFile);
        driver.takeScreenshotTo(screenShotFile);
    }

    private File getTargetDir(Description description)
    {
        return new File(artifactDir, description.getClassName());
    }

    private File getTargetFile(Description description, String extension)
    {
        return new File(getTargetDir(description), description.getMethodName() + "." + extension);
    }

}
