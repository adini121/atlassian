package it.com.atlassian.webdriver.confluence.test;

import com.atlassian.webdriver.User;
import com.atlassian.webdriver.confluence.page.PeopleDirectoryPage;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * TODO: Document this class / interface here
 *
 * @since v1.0
 */
public class TestPeopleDirectoryPage extends AbstractConfluenceWebTest
{

    private final static User ADMIN = new User("admin", "admin", "fullname", "email");

    @Test
    public void testPeopleDirectoryPage()
    {
        pageBinder.navigateToAndBind(PeopleDirectoryPage.class);
    }

    @Test
    public void testUserMacro()
    {
        PeopleDirectoryPage peopleDirectory = pageBinder.navigateToAndBind(PeopleDirectoryPage.class);
        assertNotNull(peopleDirectory.getUserMacro(ADMIN.getUsername()));
    }

}
