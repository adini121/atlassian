package it.com.atlassian.webdriver.confluence.test;

import com.atlassian.webdriver.confluence.page.ConfluenceAdminHomePage;
import org.junit.Test;

/**
 * TODO: Document this class / interface here
 *
 * @since v1.0
 */
public class TestConfluenceAdminHomePage extends AbstractConfluenceWebTest
{

    @Test
    public void testAdminHomePage()
    {
        pageNavigator.gotoPage(ConfluenceAdminHomePage.class);
    }

}
