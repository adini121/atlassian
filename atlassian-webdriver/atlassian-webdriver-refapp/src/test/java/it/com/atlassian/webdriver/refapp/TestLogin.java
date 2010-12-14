package it.com.atlassian.webdriver.refapp;

import com.atlassian.pageobjects.page.User;
import com.atlassian.pageobjects.product.TestedProductFactory;
import com.atlassian.webdriver.refapp.RefappTestedProduct;
import com.atlassian.webdriver.refapp.page.RefappHomePage;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TestLogin
{
    private static final RefappTestedProduct REFAPP = TestedProductFactory.create(RefappTestedProduct.class);

    @Test
    public void testLogin()
    {
        RefappHomePage home = REFAPP.gotoLoginPage().loginAsSysAdmin(RefappHomePage.class);
        assertTrue(home.isAdmin());
        assertTrue(home.isLoggedIn());
        assertTrue(home.isLoggedInAsUser(new User("admin", "admin", null)));
    }
}