package com.atlassian.webdriver.jira.page;

import com.atlassian.webdriver.utils.ByJquery;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 */
public class ViewAttachmentsSettingsPage extends JiraAdminAbstractPage
{

    private static final String URI = "/secure/admin/jira/ViewAttachmentSettings.jspa";

    private static final String ENABLED = "ON";

    @FindBy (id = "AttachmentSettings")
    private WebElement attachmentSettingsTable;

    private boolean attachmentsEnabled;
    private boolean thumnailsEnabled;
    private boolean zipSupportEnabled;

    private String attachmentPath;
    private String attachmentSize;

    public ViewAttachmentsSettingsPage(WebDriver driver)
    {
        super(driver);
    }

    public ViewAttachmentsSettingsPage get(final boolean activated)
    {
        get(URI, activated);

        readAttachmentSettings();

        return this;
    }

    private void readAttachmentSettings()
    {

        By colLocator = ByJquery.$("td ~ td");
        List<WebElement> attachSettingsRows = attachmentSettingsTable.findElements(By.tagName("tr"));

        attachmentsEnabled = attachSettingsRows.get(1).findElement(colLocator).getText().equals(ENABLED);

        attachmentPath = parseAttachmentPath(attachSettingsRows.get(2).findElement(colLocator).getText());
        attachmentSize = attachSettingsRows.get(3).findElement(colLocator).getText();

        thumnailsEnabled = attachSettingsRows.get(4).findElement(colLocator).getText().equals(ENABLED);
        zipSupportEnabled = attachSettingsRows.get(5).findElement(colLocator).getText().equals(ENABLED);

    }

    /**
     * parses the path to the attachments directory from the attachment path row
     * @param columnText the text to parse to extract the path
     * @return the path as a String otherwise the empty string
     */
    private String parseAttachmentPath(String columnText)
    {
        //Match text inbetween square brackets
        Pattern p = Pattern.compile("\\[([^\\]]+)\\]");
        Matcher m = p.matcher(columnText);

        if (m.find())
        {
            return m.group(1);
        }

        return "";
    }

    public boolean isAttachmentsEnabled()
    {
        return attachmentsEnabled;
    }

    public boolean isThumnailsEnabled()
    {
        return thumnailsEnabled;
    }

    public boolean isZipSupportEnabled()
    {
        return zipSupportEnabled;
    }

    public String getAttachmentPath()
    {
        return attachmentPath;
    }

    public String getAttachmentSize()
    {
        return attachmentSize;
    }
}
