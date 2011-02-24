package com.atlassian.pageobjects.framework.test;

import com.atlassian.pageobjects.framework.test.pageobjects.page.AuiPage;
import com.atlassian.pageobjects.framework.test.pageobjects.page.ElementsPage;
import com.atlassian.pageobjects.framework.test.pageobjects.page.JQueryPage;
import org.junit.Test;

import java.util.List;

import static com.atlassian.pageobjects.framework.query.TimedAssertions.assertFalseByDefaultTimeout;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class TestComponents extends AbstractFileBasedServerTest
{
    @Test
    public void testAuiMenu()
    {
        AuiPage auipage = product.visit(AuiPage.class);

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
        AuiPage auipage = product.visit(AuiPage.class);

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
        AuiPage auipage = product.visit(AuiPage.class);

        // verify dialog is not.
        assertFalse(auipage.inlineDialog().view().isPresent());

        // Invoke dialog and verify contents and is visible
        assertEquals("AUI Inline Dialog", auipage.openInlineDialog().content());
        assertTrue(auipage.inlineDialog().view().isVisible());

        // click somewhere else and verify dialog is not visible.
        auipage.roleTabs().openUserTab();
        assertFalseByDefaultTimeout(auipage.inlineDialog().view().timed().isVisible());

    }

    @Test
    public void testJqueryMenu()
    {
        JQueryPage jquerypage = product.visit(JQueryPage.class);

        // verify admin menu is present
        assertTrue(jquerypage.jqueryMenu().trigger().isPresent());

        // verify admin menu is not opened
        assertFalse(jquerypage.jqueryMenu().isOpen());

        // open the admin menu, verify is open.
        assertTrue(jquerypage.jqueryMenu().open().isOpen());

        // close the admin menu, verify not open
        assertFalse(jquerypage.jqueryMenu().close().isOpen());


        // verify follow link. Note: ElementsPage has a @WaitFor to verify that it reached the right page.
        ElementsPage auiPage = jquerypage.jqueryMenu().open().gotoElementsPage();

        // verify high level interactions
        jquerypage = product.visit(JQueryPage.class);
        jquerypage.openJqueryMenu().gotoElementsPage();

    }
}