package it.com.atlassian.webdriver.confluence.test;

import com.atlassian.webdriver.confluence.page.LicenseDetailsPage;
import org.junit.Test;

/**
 */
public class TestLicenseDetailsPage extends AbstractConfluenceWebTest
{

    @Test
    public void testLicenseDetailsPage()
    {
        product.visit(LicenseDetailsPage.class);
    }

}
