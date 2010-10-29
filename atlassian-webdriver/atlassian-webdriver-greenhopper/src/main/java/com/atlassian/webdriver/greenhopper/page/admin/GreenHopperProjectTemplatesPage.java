package com.atlassian.webdriver.greenhopper.page.admin;

import com.atlassian.webdriver.jira.JiraTestedProduct;
import com.atlassian.webdriver.jira.page.JiraAdminAbstractPage;

/**
 * TODO: Document this class / interface here
 *
 * @since v1.0
 */
public class GreenHopperProjectTemplatesPage
        extends JiraAdminAbstractPage<GreenHopperProjectTemplatesPage>
{
    private static final String URI = "/secure/GHConfigurations.jspa?decorator=admin";

    public GreenHopperProjectTemplatesPage(JiraTestedProduct testedProduct, String uri)
    {
        super(testedProduct, uri);
    }
}
