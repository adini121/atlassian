package it.com.atlassian.webdriver.confluence.test;

import com.atlassian.webdriver.confluence.ConfluenceTestedProduct;
import com.atlassian.webdriver.product.TestedProductFactory;
import com.atlassian.webdriver.test.AtlassianWebDriverFuncTestBase;

/**
 * TODO: Document this class / interface here
 *
 * @since v1.0
 */
public abstract class AbstractConfluenceWebDriverTest extends AtlassianWebDriverFuncTestBase
{

    protected final ConfluenceTestedProduct CONFLUENCE = TestedProductFactory.create(ConfluenceTestedProduct.class, "confluence", driver);

}
