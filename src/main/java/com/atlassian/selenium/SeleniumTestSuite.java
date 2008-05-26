package com.atlassian.selenium;

import junit.framework.TestResult;
import junit.framework.TestSuite;

/**
 * TODO: Document this class / interface here
 *
 * @since v3.12
 */
public abstract class SeleniumTestSuite extends TestSuite
{

    public abstract SeleniumConfiguration getSeleniumConfiguration();


    public final void run(TestResult testResult){
        SeleniumStarter.getInstance().getSeleniumClient(getSeleniumConfiguration()).start();
        super.run(testResult);
        SeleniumStarter.getInstance().stop();
    }


}
