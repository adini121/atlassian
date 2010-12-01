package it.com.atlassian.webdriver.confluence.test;

import com.atlassian.webdriver.confluence.page.ConfluenceAdminHomePage;
import org.junit.Test;

/**
 * TODO: Document this class / interface here
 *
 * @since v1.0
 */
public class TestConfluenceAdminHomePage extends AbstractConfluenceAutoBrowserWebTest
{

    @Test
    public void testAdminHomePage()
    {
        dashboard.gotoPage(ConfluenceAdminHomePage.class);
    }

}
