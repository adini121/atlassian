package com.atlassian.webdriver.jira.test;

import com.atlassian.webdriver.test.WebDriverTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import static org.junit.Assert.assertTrue;

/**
 * This is the Base class that all Jira tests should extend.
 * It exposes useful behaviour that a test might take advantage of.
 */
@Deprecated
public class JiraWebDriverTest extends WebDriverTest
{

    @BeforeClass
    public static void setupTestedProduct()
    {



    }

    @AfterClass
    public static void teardownTestedProduct()
    {

    }

}
