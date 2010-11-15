package com.atlassian.webdriver.confluence.page;

import com.atlassian.webdriver.confluence.ConfluenceTestedProduct;
import com.atlassian.webdriver.confluence.component.macro.UserMacro;
import com.atlassian.webdriver.utils.by.ByJquery;
import com.atlassian.webdriver.utils.Check;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 
 */
public class PeopleDirectoryPage extends ConfluenceAbstractPage<PeopleDirectoryPage>
{
    private static final String URI = "/browsepeople.action";

    @FindBy (linkText = "All People")
    private WebElement allPeopleLink;

    @FindBy (linkText = "People with Personal Spaces")
    private WebElement peopleWithPersonalSpacesLink;

    private Map<String, UserMacro> users = new HashMap<String, UserMacro>();

    public PeopleDirectoryPage(ConfluenceTestedProduct testedProduct)
    {
        super(testedProduct, URI);
    }

    public PeopleDirectoryPage get(final boolean activated)
    {
        get(URI, activated);

        parseUsers();

        return this;
    }

    private void parseUsers()
    {
        for (WebElement profile : getDriver().findElements(By.className("profile-macro")))
        {
            UserMacro userMacro = getTestedProduct().getComponent(By.className("vcard"), profile, UserMacro.class);
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

        return getTestedProduct().gotoPage(PeopleDirectoryPage.class, true);
    }

    public PeopleDirectoryPage showAllPeopleWithPersonalSpaces()
    {

        if (!isShowingPeopleWithPersonalSpaces())
        {
            peopleWithPersonalSpacesLink.click();
        }

        return getTestedProduct().gotoPage(PeopleDirectoryPage.class, true);

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
        return getDriver().elementExists(ByJquery.$("div.greybox p strong:contains(All People)"));
    }

    public boolean isShowingPeopleWithPersonalSpaces()
    {
        return getDriver().elementExists(ByJquery.$("div.greybox p strong:contains(People with Personal Spaces)"));
    }
}
