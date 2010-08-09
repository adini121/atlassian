package com.atlassian.webdriver.confluence.page;

import com.atlassian.webdriver.confluence.component.macro.UserMacro;
import com.atlassian.webdriver.utils.ByJquery;
import com.atlassian.webdriver.utils.Check;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class PeopleDirectoryPage extends ConfluenceWebDriverPage
{
    private static final String URI = "/peopledirectory.action";

    @FindBy (linkText = "All People")
    private WebElement allPeopleLink;

    @FindBy (linkText = "People with Personal Spaces")
    private WebElement peopleWithPersonalSpacesLink;

    private Map<String, UserMacro> users = new HashMap<String, UserMacro>();

    public PeopleDirectoryPage(WebDriver driver)
    {
        super(driver);
    }

    public PeopleDirectoryPage get(final boolean activated)
    {
        get(URI, activated);

        parseUsers();

        return this;
    }

    private void parseUsers()
    {
        for (WebElement profile : driver.findElements(By.className("profile-macro")))
        {
            UserMacro userMacro = new UserMacro(profile.findElement(By.className("vcard")));
            users.put(userMacro.getUsername(), userMacro);
        }
    }

    /**
     * @return a set which contains all the usernames on the page.
     */
    public Set<String> getAllUsernames()
    {
        return users.keySet();
    }

    public PeopleDirectoryPage showAllPeople()
    {
        if (!isShowingAllPeople())
        {
            allPeopleLink.click();
        }

        return ConfluencePage.PEOPLE_DIRECTORY_PAGE.get(driver, true);
    }

    public PeopleDirectoryPage showAllPeopleWithPersonalSpaces()
    {

        if (!isShowingPeopleWithPersonalSpaces())
        {
            peopleWithPersonalSpacesLink.click();
        }

        return ConfluencePage.PEOPLE_DIRECTORY_PAGE.get(driver, true);

    }

    public boolean hasUser(String username)
    {
        return users.containsKey(username);
    }

    public UserMacro getUserMacro(String username)
    {
        return hasUser(username) ? users.get(username) : null;
    }

    public boolean isShowingAllPeople()
    {
        return Check.elementExists(ByJquery.$("div.greybox p strong:contains(All People)"));
    }

    public boolean isShowingPeopleWithPersonalSpaces()
    {
        return Check.elementExists(ByJquery.$("div.greybox p strong:contains(People with Personal Spaces)"));
    }
}
