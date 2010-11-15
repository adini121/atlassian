package it.com.atlassian.webdriver.confluence.test;

import com.atlassian.webdriver.confluence.page.PeopleDirectoryPage;
import com.atlassian.webdriver.utils.user.User;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * TODO: Document this class / interface here
 *
 * @since v1.0
 */
public class TestPeopleDirectoryPage extends AbstractConfluenceAutoBrowserWebTest
{

    private final static User ADMIN = new User("admin", "admin", "fullname", "email");

    @Test
    public void testPeopleDirectoryPage()
    {
        dashboard.gotoPage(PeopleDirectoryPage.class);
    }

    @Test
    public void testUserMacro()
    {
        PeopleDirectoryPage peopleDirectory = dashboard.gotoPage(PeopleDirectoryPage.class);
        assertNotNull(peopleDirectory.getUserMacro(ADMIN.getUsername()));
    }

}
