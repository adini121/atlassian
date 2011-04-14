package it.com.atlassian.webdriver.confluence.test;

import com.atlassian.pageobjects.page.WebSudoPage;
import com.atlassian.webdriver.confluence.page.PeopleDirectoryPage;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

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
}
