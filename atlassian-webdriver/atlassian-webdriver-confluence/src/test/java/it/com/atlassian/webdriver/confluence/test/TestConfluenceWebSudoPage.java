package it.com.atlassian.webdriver.confluence.test;

import com.atlassian.pageobjects.component.WebSudoBanner;
import com.atlassian.pageobjects.page.WebSudoPage;
import com.atlassian.webdriver.confluence.page.DashboardPage;
import com.atlassian.webdriver.confluence.page.PeopleDirectoryPage;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 */
public class TestConfluenceWebSudoPage extends AbstractConfluenceWebTest
{
    @Test
    public void testAdministratorAccessPage()
    {
        PeopleDirectoryPage peoplePage = CONFLUENCE.visit(WebSudoPage.class).confirm(PeopleDirectoryPage.class);
        assertTrue(peoplePage.hasUser("admin"));
    }

    @Test
    public void testWebSudoBanner()
    {
        PeopleDirectoryPage peoplePage = CONFLUENCE.visit(WebSudoPage.class).confirm(PeopleDirectoryPage.class);
        WebSudoBanner webSudoBanner = peoplePage.getHeader().getWebSudoBanner();
        assertTrue(webSudoBanner.isShowing());

        DashboardPage dashboard = webSudoBanner.dropWebSudo(DashboardPage.class);
        assertFalse(dashboard.getHeader().getWebSudoBanner().isShowing());
    }
}
