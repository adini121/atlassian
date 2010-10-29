package com.atlassian.webdriver.jira.page.user;

import com.atlassian.webdriver.jira.JiraTestedProduct;
import com.atlassian.webdriver.jira.page.JiraAdminAbstractPage;
import com.atlassian.webdriver.PageObject;
import com.atlassian.webdriver.utils.by.ByJquery;
import com.atlassian.webdriver.utils.Check;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.HashSet;
import java.util.Set;

/**
 * Page object implementation for the edit user's group page in JIRA. 
 *
 * @since v1.0
 */
public class  EditUserGroupsPage extends JiraAdminAbstractPage<EditUserGroupsPage>
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

    public EditUserGroupsPage(final JiraTestedProduct jiraTestedProduct)
    {
        super(jiraTestedProduct, URI);
    }

    public EditUserGroupsPage get(final boolean activated)
    {
        get(URI, activated);

        parsePage();

        return this;
    }

    private void parsePage()
    {

        if (Check.elementExists(ByJquery.$(ERROR_SELECTOR), getDriver()))
        {
            for (WebElement el : getDriver().findElements(ByJquery.$(ERROR_SELECTOR)))
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

        return getTestedProduct().gotoPage(ViewUserPage.class, true);
    }

    /**
     * Add to groups either redirects the user to another page or returns the user to
     * the EditUserGroupsPage.
     * @param groups
     * @return
     */
    public <T extends PageObject> T addToGroupsAndReturnToPage(Class<T> pageClass, String ... groups)
    {
        selectGroups(groupsToJoinSelect, groups);

        joinButton.click();

        return getTestedProduct().gotoPage(pageClass, true);
    }

    public EditUserGroupsPage addToGroupsExpectingError(String ... groups)
    {
        return addToGroupsAndReturnToPage(EditUserGroupsPage.class, groups);
    }

    public <T extends PageObject> T removeFromGroupsAndReturnToPage(Class<T> pageClass, String ... groups)
    {
        selectGroups(groupsToLeaveSelect, groups);

        leaveButton.click();

        return getTestedProduct().gotoPage(pageClass, true);
    }

    public EditUserGroupsPage removeFromGroupsExpectingError(String ... groups)
    {
        return removeFromGroupsAndReturnToPage(EditUserGroupsPage.class, groups);
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
