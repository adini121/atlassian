package it.com.atlassian.webdriver.jira.test;

import com.atlassian.webdriver.jira.JiraTestedProduct;
import com.atlassian.webdriver.product.TestedProductFactory;
import com.atlassian.webdriver.test.AtlassianWebDriverFuncTestBase;

/**
 * TODO: Document this class / interface here
 *
 * @since v1.0
 */
public class AbstractJiraWebDriverTest extends AtlassianWebDriverFuncTestBase
{
    protected final JiraTestedProduct JIRA = TestedProductFactory.create(JiraTestedProduct.class, "jira", driver);
}
