package com.atlassian.webdriver.greenhopper.page.license;

import com.atlassian.webdriver.jira.page.JiraAdminAbstractPage;
import com.atlassian.webdriver.greenhopper.page.GreenHopperPage;
import com.atlassian.webdriver.utils.ByJquery;
import com.atlassian.webdriver.utils.Check;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class GreenHopperLicenseDetailsPage extends JiraAdminAbstractPage
{

    private static final String URI = "/secure/GHLicense.jspa?decorator=admin";
    private static final String DATE_FORMAT = "dd/MMM/yy";

    @FindBy (name = "newLicense")
    private WebElement updateLicenseTextArea;

    @FindBy (name = "jiraform")
    private WebElement updateLicenseForm;

    @FindBy (id = "license_table")
    private WebElement licenseTable;

    private String organisation;
    private Date purchaseDate;
    private String licenseType;
    private Date expiryDate;
    private String serverId;
    private String supportEntitlmentNumber;

    private String errorMessage = "";

    private boolean licenseIsLoaded = false;
    

    public GreenHopperLicenseDetailsPage(WebDriver driver)
    {
        super(driver);
    }

    public GreenHopperLicenseDetailsPage get(final boolean activated)
    {
        get(URI, activated);

        readLicense();

        return this;
    }

    private void readLicense()
    {
        if(Check.elementExists(By.className("errMsg"), updateLicenseForm))
        {
            errorMessage = updateLicenseForm.findElement(By.className("errMsg")).getText();
        }

        List<WebElement> rows = licenseTable.findElements(By.tagName("tr"));

        organisation = rows.get(0).findElement(ByJquery.$("td ~ td")).getText();

        if (StringUtils.isNotEmpty(organisation))
        {
            licenseIsLoaded = true;

            purchaseDate = formatDate(rows.get(1).findElement(ByJquery.$("td ~ td strong")).getText());

            WebElement licenseTypeColumn = rows.get(2).findElement(ByJquery.$("td ~ td"));

            licenseType = licenseTypeColumn.findElement(By.tagName("strong")).getText();
            expiryDate = formatDate(extractExpiryDate(licenseTypeColumn.getText()));

            serverId = rows.get(3).findElement(ByJquery.$("td ~ td strong")).getText();

            supportEntitlmentNumber = rows.get(4).findElement(ByJquery.$("td ~ td strong")).getText();

        }

    }

    private String extractExpiryDate(String licenseTypeStr)
    {
        licenseTypeStr = licenseTypeStr.replaceAll("\\n", "");

        Pattern p = Pattern.compile("(\\d{2}/[A-Za-z]{3}/\\d{2})", Pattern.MULTILINE);
        Matcher m = p.matcher(licenseTypeStr);

        String expiryStr = null;

        if (m.find())
        {
            expiryStr = m.group(1);
        }

        return expiryStr;
    }

    private Date formatDate(String date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

        try
        {
            return sdf.parse(date);
        }
        catch (ParseException e)
        {
            // Do nothing
        }

        return null;

    }

    /**
     * Gets the error message in the update license form area.
     * @return a String of the error message or empty string if there is no error
     */
    public String getErrorMessage()
    {   
        return errorMessage;
    }

    public boolean isTrial()
    {
        if (licenseIsLoaded())
        {
            return supportEntitlmentNumber.equals("NONE/TRIAL");
        }

        return false;
    }

    public boolean licenseIsLoaded()
    {
        return licenseIsLoaded;
    }

    public String getOrganization()
    {
        return organisation;
    }

    public String getLicenseType()
    {
        return getLicenseType();
    }

    public String getServerId()
    {
        return serverId;
    }

    public String getSupportEntitlementNumber()
    {
        return supportEntitlmentNumber;
    }

    public Date getPurchasedDate()
    {
        return purchaseDate;
    }

    public Date getExpiryDate()
    {
        return expiryDate;
    }

    public GreenHopperLicenseDetailsPage updateLicense(String license)
    {
        updateLicenseTextArea.sendKeys(license);
        updateLicenseForm.submit();

        return GreenHopperPage.LICENSE_DETAILS_PAGE.get(driver, true);
    }
}
