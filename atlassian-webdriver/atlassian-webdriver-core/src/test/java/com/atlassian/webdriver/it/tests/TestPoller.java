package com.atlassian.webdriver.it.tests;

import com.atlassian.webdriver.it.AbstractFileBasedServerTest;
import com.atlassian.webdriver.it.pageobjects.page.PollerPage;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 *
 * @since 2.1
 */
public class TestPoller extends AbstractFileBasedServerTest
{

    PollerPage pollerPage;

    @Before
    public void setup()
    {
        pollerPage = product.visit(PollerPage.class);
    }

    @Test
    public void testFunctionPollerIsTrue()
    {
        assertFalse(pollerPage.dialogOneShowing());
        pollerPage.showFunctionOneElement();
        assertTrue(pollerPage.dialogOneShowing());
    }

}
