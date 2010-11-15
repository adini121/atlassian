package it.com.atlassian.webdriver.confluence.test;

import com.atlassian.webdriver.confluence.page.LicenseDetailsPage;
import org.junit.Test;

/**
 * TODO: Document this class / interface here
 *
 * @since v1.0
 */
public class TestLicenseDetailsPage extends AbstractConfluenceAutoBrowserWebTest
{

    @Test
    public void testLicenseDetailsPage()
    {
        dashboard.gotoPage(LicenseDetailsPage.class);
    }

}
