package it.com.atlassian.webdriver.refapp;

import com.atlassian.pageobjects.page.User;
import com.atlassian.pageobjects.product.TestedProductFactory;
import com.atlassian.webdriver.refapp.RefappTestedProduct;
import com.atlassian.webdriver.refapp.component.RefappHeader;
import com.atlassian.webdriver.refapp.page.RefappHomePage;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TestRefappHeader
{
    private static final RefappTestedProduct REFAPP = TestedProductFactory.create(RefappTestedProduct.class);

    @Test
    public void testLogin()
    {
        RefappHeader header = REFAPP.gotoLoginPage().loginAsSysAdmin(RefappHomePage.class).getHeader();
        assertTrue(header.isAdmin());
        assertTrue(header.isLoggedIn());
        assertTrue(header.isLoggedInAsUser(new User("admin", "admin", null)));
    }
}