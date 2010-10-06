package com.atlassian.webdriver.jira.page.user;

import com.atlassian.webdriver.jira.page.JiraAdminAbstractPage;
import com.atlassian.webdriver.jira.page.JiraPages;
import com.atlassian.webdriver.page.Page;
import com.atlassian.webdriver.PageObject;
import com.atlassian.webdriver.utils.ByJquery;
import com.atlassian.webdriver.utils.Check;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.HashSet;
import java.util.Set;

/**
 * Page object implementation for the edit user's group page in JIRA. 
 *
 * @since v1.0
 */
public class  EditUserGroupsPage extends JiraAdminAbstractPage
{

    private static final String URI = "/secure/admin/user/EditUserGroups.jspa";
    private static String ERROR_SELECTOR = ".formErrors ul li";

    private Set<String> errors = new HashSet<String>();

    @FindBy (id = "return_link")
    private WebElement returnLink;

    @FindBy (name = "join")
    private WebElement joinButton;

    @FindBy (name = "leave")
    private WebElement leaveButton;

    @FindBy (name = "groupsToJoin")
    private WebElement groupsToJoinSelect;

    @FindBy (name = "groupsToLeave")
    private WebElement groupsToLeaveSelect;

    @FindBy (name = "jiraform")
    private WebElement editGroupsForm;

    public EditUserGroupsPage(final WebDriver driver)
    {
        super(driver);
    }

    public EditUserGroupsPage get(final boolean activated)
    {
        get(URI, activated);

        parsePage();

        return this;
    }

    private void parsePage()
    {

        if (Check.elementExists(ByJquery.$(ERROR_SELECTOR)))
        {
            for (WebElement el : driver.findElements(ByJquery.$(ERROR_SELECTOR)))
            {
                errors.add(el.getText());
            }
        }


    }

    public boolean hasErrors()
    {
        return !errors.isEmpty();
    }

    public boolean hasError(String errorStr)
    {
        return errors.contains(errorStr);
    }

    public ViewUserPage returnToUserView()
    {
        returnLink.click();

        return JiraPages.VIEW_USER_PAGE.get(driver, true);
    }

    /**
     * Add to groups either redirects the user to another page or returns the user to
     * the EditUserGroupsPage.
     * @param groups
     * @return
     */
    public <T extends PageObject> T addToGroupsAndReturnToPage(Page<T> page, String ... groups)
    {
        selectGroups(groupsToJoinSelect, groups);

        joinButton.click();

        return page.get(driver, true);
    }

    public EditUserGroupsPage addToGroupsExpectingError(String ... groups)
    {
        selectGroups(groupsToLeaveSelect, groups);

        leaveButton.click();

        return JiraPages.EDIT_USER_GROUPS_PAGE.get(driver, true);
    }

    public <T extends PageObject> T removeFromGroupsAndReturnToPage(Page<T> page, String ... groups)
    {
        selectGroups(groupsToLeaveSelect, groups);

        leaveButton.click();

        return page.get(driver, true);
    }

    public EditUserGroupsPage removeFromGroupsExpectingError(String ... groups)
    {
        selectGroups(groupsToLeaveSelect, groups);

        leaveButton.click();

        return JiraPages.EDIT_USER_GROUPS_PAGE.get(driver, true);
    }

    private void selectGroups(WebElement select, String ... groups)
    {
        for (String group : groups)
        {
            String groupSelector = "option[value=" + group + "]";

            if (Check.elementExists(ByJquery.$(groupSelector), select))
            {
                select.findElement(ByJquery.$(groupSelector)).setSelected();
            }
        }

    }
}
