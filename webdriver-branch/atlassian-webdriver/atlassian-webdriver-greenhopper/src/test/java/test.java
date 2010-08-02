import com.atlassian.webdriver.component.greenhopper.link.GreenHopperAdminSideMenuLinks;
import com.atlassian.webdriver.component.greenhopper.link.GreenHopperAdminSideMenuPageLinks;
import com.atlassian.webdriver.component.jira.link.AdminSideMenuLinks;
import com.atlassian.webdriver.component.jira.menu.AdminSideMenu;
import com.atlassian.webdriver.component.user.User;
import com.atlassian.webdriver.page.greenhopper.license.GreenHopperLicenseDetailsPage;
import com.atlassian.webdriver.page.jira.UserBrowserPage;
import com.atlassian.webdriver.test.jira.JiraWebDriverTest;
import junit.framework.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class test extends JiraWebDriverTest
{

    static {
        AdminSideMenu.addPageLinks(GreenHopperAdminSideMenuPageLinks.getPageLinks());
    }
    
    @Test
    public void testing() throws Exception
    {


        UserBrowserPage page = login(new User("admin", "admin", "Jira Administrator", "")).getAdminMenu().gotoUserBrowserPage();

        AdminSideMenu m = page.getAdminSideMenu();

        GreenHopperLicenseDetailsPage ldp = m.activateLink(GreenHopperAdminSideMenuLinks.LICENSE_DETAILS_LINK);

        Assert.assertTrue(ldp.licenseIsLoaded());

        Assert.assertTrue(ldp.getOrganization().equals("HOSTED TRIAL"));

        Assert.assertTrue(ldp.isTrial());


        SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yy");

        Assert.assertEquals(ldp.getExpiryDate(), sdf.parse("02/Sep/10"));


        ldp = ldp.updateLicense("asdasda");

        Assert.assertEquals(ldp.getErrorMessage(), "Invalid license key specified.");


    }

}
