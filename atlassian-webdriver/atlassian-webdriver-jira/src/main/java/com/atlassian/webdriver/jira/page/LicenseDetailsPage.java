package com.atlassian.webdriver.jira.page;

import com.atlassian.webdriver.jira.JiraTestedProduct;
import com.atlassian.webdriver.utils.by.ByJquery;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Page object implementation for the License details page in JIRA.
 */
public class LicenseDetailsPage extends JiraAdminAbstractPage<LicenseDetailsPage>
{

    private static final String URI = "/secure/admin/ViewLicense!default.jspa";

    @FindBy (id = "license_table")
    WebElement licenseTable;

    @FindBy (name = "license")
    WebElement updateLicenseTextArea;

    @FindBy (id = "add_submit")
    WebElement addLicenseButton;

    public LicenseDetailsPage(JiraTestedProduct testedProduct)
    {
        super(testedProduct, URI);
    }

    public String getOrganisation()
    {
        return licenseTable.findElement(By.cssSelector("tr:nth-child(1) td:nth-child(2) b")).getText();
    }

    public Date getDatePurchased()
    {

        String datePurchased = licenseTable.findElement(By.cssSelector("tr:nth-child(2) td:nth-child(2) b")).getText();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");

        Date date;
        try
        {
            date = format.parse(datePurchased);
        }
        catch (ParseException e)
        {
            throw new RuntimeException(e);
        }


        return date;
    }

    // TODO: fix this
    public boolean isEvaluation() {
        return false;
    }

    public String getLicenseDescription()
    {
        return licenseTable.findElement(By.cssSelector("tr:nth-child(3) td:nth-child(2) b")).getText();
    }

    public String getServerId()
    {
        return licenseTable.findElement(By.cssSelector("tr:nth-child(4) td:nth-child(2) b")).getText();
    }

    public String getSupportEntitlementNumber()
    {
        return licenseTable.findElement(By.cssSelector("tr:nth-child(5) td:nth-child(2) b")).getText();
    }

    //TODO: handle unlimited.
    public int getUserLimit()
    {
        return Integer.valueOf(licenseTable.findElement(By.cssSelector("tr:nth-child(6) td:nth-child(2) b")).getText());
    }

    public int getActiveUsers()
    {
        String userLimit = licenseTable.findElement(By.cssSelector("tr:nth-child(6) td:nth-child(2)")).getText();

        Pattern re = Pattern.compile("[(]([0-9]+) currently active[)]");
        Matcher m = re.matcher(userLimit);

        if (m.find())
        {
            String activeUSers = m.group(1);
            return Integer.valueOf(activeUSers);
        }

        return -1;
    }

    public LicenseDetailsPage updateLicense(String license)
    {
        updateLicenseTextArea.sendKeys(license);

        addLicenseButton.click();

        return new LicenseDetailsPage(getTestedProduct()).get(true);
    }

    @Override
    public void doWait()
    {
        getDriver().waitUntilElementIsLocated(By.id("license_table"));
    }
}