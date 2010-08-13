package com.atlassian.webdriver.jira.page.user;

import com.atlassian.webdriver.jira.page.JiraAdminWebDriverPage;
import com.atlassian.webdriver.jira.page.JiraPages;
import com.atlassian.webdriver.page.PageObject;
import com.atlassian.webdriver.utils.ByJquery;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * TODO: Document this class / interface here
 *
 * @since v1.0
 */
public class DeleteUserPage extends JiraAdminWebDriverPage
{
    private static String URI = "/secure/admin/user/DeleteUser!default.jspa";

    @FindBy (id = "numberOfFilters")
    private WebElement numberOfSharedFiltersElement;

    @FindBy (id = "numberOfOtherFavouritedFilters")
    private WebElement numberOfOtherSharedFiltersElement;

    @FindBy (id = "numberOfNonPrivatePortalPages")
    private WebElement numberOfSharedDashboardsElement;

    @FindBy (id = "numberOfOtherFavouritedPortalPages")
    private WebElement numberOfOtherFavoriteDashboardsElement;

    @FindBy (id = "delete_submit")
    private WebElement deleteUserButton;

    @FindBy (id = "cancelButton")
    private WebElement cancelDeleteUserButton;

    private WebElement numberOfAssignedIssuesElement;
    private WebElement numberOfReportedIssuesElement;

    public DeleteUserPage(final WebDriver driver)
    {
        super(driver);
    }

    public DeleteUserPage get(final boolean activated)
    {
        get(URI, activated);

        parseIssues();

        return this;
    }

    private void parseIssues()
    {
        numberOfAssignedIssuesElement = driver.findElement(ByJquery.$("('td.fieldLabelArea:contains(Assigned Issues)').siblings('td').get(0)"));
        numberOfReportedIssuesElement = driver.findElement(ByJquery.$("('td.fieldLabelArea:contains(Reported Issues)').siblings('td').get(0)"));
    }

    public UserBrowserPage deleteUser()
    {
        deleteUserButton.click();

        return JiraPages.USERBROWSERPAGE.get(driver, true);
    }

    public int getNumberOfSharedFiltersElement()
    {
        return getIntValue(numberOfSharedFiltersElement);
    }

    public int getNumberOfOtherSharedFiltersElement()
    {
        return getIntValue(numberOfOtherSharedFiltersElement);
    }

    public int getNumberOfSharedDashboardsElement()
    {
        return getIntValue(numberOfSharedDashboardsElement);
    }

    public int getNumberOfOtherFavoriteDashboardsElement()
    {
        return getIntValue(numberOfOtherFavoriteDashboardsElement);
    }

    public int getDeleteUserButton()
    {
        return getIntValue(deleteUserButton);
    }

    public int getCancelDeleteUserButton()
    {
        return getIntValue(cancelDeleteUserButton);
    }

    public int getNumberOfAssignedIssuesElement()
    {
        return getIntValue(numberOfAssignedIssuesElement);
    }

    public int getNumberOfReportedIssuesElement()
    {
        return getIntValue(numberOfReportedIssuesElement);
    }

    private int getIntValue(WebElement element)
    {
        return Integer.parseInt(element.getText());
    }
}
