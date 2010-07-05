package com.atlassian.webdriver.page.jira;

import com.atlassian.webdriver.component.jira.group.Group;
import com.atlassian.webdriver.component.jira.user.User;
import com.atlassian.webdriver.utils.table.Row;
import com.atlassian.webdriver.utils.table.Table;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * TODO: Handle pagination when there are more users
 */
public class UserBrowserPage extends JiraWebDriverPage
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

        return JiraPage.USERBROWSER.get(driver, true);
    }

    private void setUserFilterToShowAllUsers()
    {
        usersPerPageDropdown.findElement(By.cssSelector("option[value=\""+ MAX +"\"]")).setSelected();
        filterSubmit.click();
    }

    private void getUsers()
    {

        users.removeAll(users);

        Table userTable = new Table(By.id("user_browser_table"), driver);

        Iterator<Row> iter = userTable.iterator();

        // Skip over the table headings
        iter.next();

        while (iter.hasNext())
        {

            Row row = iter.next();
            String username = row.getColumn(0).findElement(By.tagName("span")).getText();
            String email = row.getColumn(1).findElement(By.tagName("span")).getText();
            String fullName = row.getColumn(2).findElement(By.tagName("span")).getText();

            Set<Group> groups = new HashSet<Group>();
            Iterator<WebElement> groupIter = row.getColumn(4).findElements(By.tagName("a")).iterator();

            while (groupIter.hasNext())
            {
                groups.add(new Group(groupIter.next().getText()));
            }

            users.add(new User(username, fullName, email, groups));

        }

    }
}