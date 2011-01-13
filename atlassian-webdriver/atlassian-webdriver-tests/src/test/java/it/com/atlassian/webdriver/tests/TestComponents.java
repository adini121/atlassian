package it.com.atlassian.webdriver.tests;

import com.atlassian.pageobjects.TestedProductFactory;
import it.com.atlassian.webdriver.pageobjects.RefappTestedProduct;
import it.com.atlassian.webdriver.pageobjects.page.RefAppAUIPage;
import it.com.atlassian.webdriver.pageobjects.page.RefAppJQueryPage;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class TestComponents
{
    private static final RefappTestedProduct REFAPP = TestedProductFactory.create(RefappTestedProduct.class);

    @Test
    public void testAuiMenu()
    {
        RefAppAUIPage auipage = REFAPP.getPageBinder().navigateToAndBind(RefAppAUIPage.class);

        //verify items in menu
        List<String> items = auipage.openLinksMenu().getItems();
        assertEquals(6, items.size());
        assertEquals("JQuery Page", items.get(0));
        for(int i = 1; i <6; i++)
        {
            assertEquals("Item " + i, items.get(i));
        }

        // click on an item in the menu
        auipage.openLinksMenu().gotoJQueryPage();
    }

    @Test
    public void testsAuiTab()
    {
        RefAppAUIPage auipage = REFAPP.getPageBinder().navigateToAndBind(RefAppAUIPage.class);

        // verify default selections
        assertTrue(auipage.roleTabs().adminTab().isOpen());
        assertFalse(auipage.roleTabs().userTab().isOpen());

        // open user tab and verify header and selections
        assertEquals("This is User Tab", auipage.roleTabs().openUserTab().header());
        assertFalse(auipage.roleTabs().adminTab().isOpen());
        assertTrue(auipage.roleTabs().userTab().isOpen());

        // Open admin and verify header and selections
        assertEquals("This is Admin Tab", auipage.roleTabs().openAdminTab().header());
        assertTrue(auipage.roleTabs().adminTab().isOpen());
        assertFalse(auipage.roleTabs().userTab().isOpen());
    }

    @Test
    public void testsInlineDialog()
    {
        RefAppAUIPage auipage = REFAPP.getPageBinder().navigateToAndBind(RefAppAUIPage.class);

        // verify dialog is not.
        assertFalse(auipage.inlineDialog().view().isPresent());

        // Invoke dialog and verify contents and is visible
        assertEquals("AUI Inline Dialog", auipage.openInlineDialog().content());
        assertTrue(auipage.inlineDialog().view().isVisible());

        // click somewhere else and verify dialog is not visible.
        auipage.roleTabs().openUserTab();
        assertTrue(auipage.inlineDialog().view().timed().isVisible().checkFor(false));        

    }

    @Test
    public void testJqueryMenu()
    {
        RefAppJQueryPage jquerypage = REFAPP.getPageBinder().navigateToAndBind(RefAppJQueryPage.class);

         // verify admin menu is present
        assertTrue(jquerypage.adminMenu().trigger().isPresent());

        // verify admin menu is not opened
        assertFalse(jquerypage.adminMenu().isOpen());

        // open the admin menu, verify is open.
        assertTrue(jquerypage.adminMenu().open().isOpen());

        // close the admin menu, verify not open
        assertFalse(jquerypage.adminMenu().close().isOpen());

        // verify follow aui link. Note: AUIPage has a @WaitFor to verify that it reached the right page.
        RefAppAUIPage auiPage = jquerypage.adminMenu().open().gotoAuiPageLink();

        // verify high level interactions
        jquerypage = REFAPP.getPageBinder().navigateToAndBind(RefAppJQueryPage.class);
        jquerypage.openAdminMenu().gotoAuiPageLink();
    }
}
