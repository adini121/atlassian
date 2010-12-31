package it.com.atlassian.webdriver.tests;

import com.atlassian.pageobjects.TestedProductFactory;
import it.com.atlassian.webdriver.pageobjects.RefappTestedProduct;
import it.com.atlassian.webdriver.pageobjects.page.RefAppAUIPage;
import it.com.atlassian.webdriver.pageobjects.page.RefAppJQueryPage;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Integration tests for pageobjects that are available in the atlassian-webdriver-core package
 */
public class TestPageObjects {
    private static final RefappTestedProduct REFAPP = TestedProductFactory.create(RefappTestedProduct.class);
    
    @Test
    public void testCustomMenus()
    {
        RefAppJQueryPage jquerypage = REFAPP.getPageBinder().navigateToAndBind(RefAppJQueryPage.class);

         // verify admin menu is present
        assertTrue(jquerypage.adminMenu().isRootPresent());

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
        auiPage = jquerypage.openAdminMenu().gotoAuiPageLink();
    }
}
