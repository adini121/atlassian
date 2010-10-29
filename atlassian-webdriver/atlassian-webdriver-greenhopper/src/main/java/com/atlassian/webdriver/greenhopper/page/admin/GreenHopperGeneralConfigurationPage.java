package com.atlassian.webdriver.greenhopper.page.admin;

import com.atlassian.webdriver.jira.JiraTestedProduct;
import com.atlassian.webdriver.jira.page.JiraAdminAbstractPage;

/**
 * TODO: Document this class / interface here
 *
 * @since v1.0
 */
public class GreenHopperGeneralConfigurationPage
        extends JiraAdminAbstractPage<GreenHopperGeneralConfigurationPage>
{
    private static final String URI = "/secure/GHGeneralConfiguration.jspa?decorator=admin";

    public GreenHopperGeneralConfigurationPage(JiraTestedProduct testedProduct, String uri)
    {
        super(testedProduct, uri);
    }
}
