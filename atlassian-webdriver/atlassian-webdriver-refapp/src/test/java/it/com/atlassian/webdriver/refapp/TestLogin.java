package it.com.atlassian.webdriver.refapp;

import com.atlassian.webdriver.utils.user.User;
import com.atlassian.webdriver.product.TestedProductFactory;
import com.atlassian.webdriver.refapp.RefappTestedProduct;
import com.atlassian.webdriver.refapp.page.RefappHomePage;
import org.junit.Test;
import webdriver.browsers.WebDriverBrowserAutoInstall;

import static org.junit.Assert.assertTrue;

public class TestLogin
{
    private static final RefappTestedProduct REFAPP = TestedProductFactory.create(RefappTestedProduct.class, "refapp",
                                    WebDriverBrowserAutoInstall.INSTANCE.getDriver());

    @Test
    public void testLogin()
    {
        RefappHomePage home = REFAPP.gotoLoginPage().loginAsAdmin();
        assertTrue(home.isAdmin());
        assertTrue(home.isLoggedIn());
        assertTrue(home.isLoggedInAsUser(new User("admin", "admin", null)));
    }
}