package com.atlassian.webdriver.page.jira;

import com.atlassian.webdriver.component.group.Group;
import com.atlassian.webdriver.component.user.User;
import com.atlassian.webdriver.utils.Check;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Page object implementation for the User browser page in JIRA. TODO: Handle pagination when there are more users
 */
public class UserBrowserPage extends JiraAdminWebDriverPage
{
    private static final String URI = "/secure/admin/user/UserBrowser.jspa";

    private String MAX = "1000000";
    private String TEN = "10";
    private String TWENTY = "20";
    private String FIFTY = "50";
    private String ONE_HUNDRED = "100";

    private final Set<User> users;
    private WebElement filterSubmit;

    @FindBy (id = "add_user")
    WebElement addUserLink;

    @FindBy (xpath = "/html/body/table/tbody/tr/td[3]/table/tbody/tr/td/form/table/tbody/tr[2]/td/p[3]/strong[3]")
    WebElement numUsers;

    @FindBy (name = "max")
    WebElement usersPerPageDropdown;

    public UserBrowserPage(WebDriver driver)
    {
        super(driver);
        users = new HashSet<User>();
    }

    public UserBrowserPage get(boolean activated)
    {
        get(URI, activated);

        filterSubmit = driver.findElement(By.cssSelector("form[name=\"jiraform\"] input[type=\"submit\"]"));

        setUserFilterToShowAllUsers();

        getUsers();

        return this;
    }

    public boolean hasUser(User user)
    {
        return users.contains(user);
    }

    public int getNumberOfUsers()
    {
        return Integer.valueOf(numUsers.getText());
    }

    public UserBrowserPage filterByUserName(String username)
    {
        driver.findElement(By.name("userNameFilter")).sendKeys(username);
        filterSubmit.click();

        return JiraPages.USERBROWSERPAGE.get(driver, true);
    }

    private void setUserFilterToShowAllUsers()
    {
        usersPerPageDropdown.findElement(By.cssSelector("option[value=\"" + MAX + "\"]")).setSelected();
        filterSubmit.click();
    }

    private void getUsers()
    {

        users.removeAll(users);

        WebElement userTable = driver.findElement(By.id("user_browser_table"));

        List<WebElement> rows = userTable.findElements(By.tagName("tr"));

        for (WebElement row : rows)
        {
            // Check it's not the headings (th) tags.
            if (Check.elementExists(By.tagName("td"), row))
            {
                List<WebElement> cols = row.findElements(By.tagName("td"));

                String username = cols.get(0).getText();
                String email = cols.get(1).getText();
                String fullName = cols.get(2).getText();

                Set<Group> groups = new HashSet<Group>();

                for (WebElement group : cols.get(4).findElements(By.tagName("a")))
                {
                    groups.add(new Group(group.getText()));
                }

                users.add(new User(username, fullName, email, groups));
            }
        }

    }
}