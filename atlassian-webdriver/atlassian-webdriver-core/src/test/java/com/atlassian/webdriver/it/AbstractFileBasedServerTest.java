package com.atlassian.webdriver.it;

import com.atlassian.webdriver.it.pageobjects.SimpleTestedProduct;
import com.atlassian.webdriver.testing.annotation.TestedProductClass;
import com.atlassian.webdriver.testing.rule.IgnoreBrowserRule;
import com.atlassian.webdriver.testing.rule.SessionCleanupRule;
import com.atlassian.webdriver.testing.rule.WebDriverScreenshotRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

import javax.inject.Inject;

@TestedProductClass(SimpleTestedProduct.class)
@RunWith(FileBasedServerRunner.class)
public abstract class AbstractFileBasedServerTest 
{
    @Inject protected static SimpleTestedProduct product;

    @Inject @Rule public IgnoreBrowserRule ignoreRule;
    @Inject @Rule public WebDriverScreenshotRule webDriverScreenshotRule;
    @Inject @Rule public SessionCleanupRule sessionCleanupRule;

}
