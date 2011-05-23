package it.com.atlassian.webdriver.confluence.test;

import com.atlassian.pageobjects.TestedProductFactory;
import com.atlassian.pageobjects.page.LoginPage;
import com.atlassian.webdriver.confluence.ConfluenceTestedProduct;
import com.atlassian.webdriver.confluence.component.header.ConfluenceHeader;
import com.atlassian.webdriver.confluence.page.DashboardPage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestConfluenceHeader
{
    private static final ConfluenceTestedProduct CONFLUENCE = TestedProductFactory.create(ConfluenceTestedProduct.class);

    @Test
    public void testLoginInfo()
    {
        ConfluenceHeader header = CONFLUENCE.gotoLoginPage().loginAsSysAdmin(DashboardPage.class).getHeader();

        assertTrue(header.isAdmin());
        assertTrue(header.isLoggedIn());
    }

    @Test
    public void testLogout()
    {
        ConfluenceHeader header = CONFLUENCE.gotoHomePage().getHeader();
        assertFalse(header.isAdmin());
        assertFalse(header.isLoggedIn());
    }

    @After
    @Before
    public void logout()
    {
        CONFLUENCE.gotoHomePage().getHeader().logout(LoginPage.class);
    }
}
