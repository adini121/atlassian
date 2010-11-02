package it.com.atlassian.webdriver.refapp;

import com.atlassian.webdriver.component.user.User;
import com.atlassian.webdriver.page.UserDiscoverable;
import com.atlassian.webdriver.product.TestedProduct;
import com.atlassian.webdriver.product.TestedProductFactory;
import com.atlassian.webdriver.refapp.RefappTestedProduct;
import com.atlassian.webdriver.refapp.page.RefappHomePage;
import org.junit.Test;
import webdriver.browsers.WebDriverBrowserAutoInstall;

import static org.junit.Assert.assertTrue;

public class TestLogin
{
    @Test
    public void testLogin()
    {
        TestedProduct refapp = TestedProductFactory.create();
        refapp.gotoLoginPage().loginAsAdmin();
        UserDiscoverable page = (UserDiscoverable)refapp.gotoLoginPage();
        assertTrue(page.isAdmin());
        assertTrue(page.isLoggedIn());
    }
}
