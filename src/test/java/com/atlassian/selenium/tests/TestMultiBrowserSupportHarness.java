package com.atlassian.selenium.tests;

import com.atlassian.selenium.SeleniumMultiTestSuite;
import junit.framework.Test;
import junit.framework.TestSuite;

public class TestMultiBrowserSupportHarness
{
     public static Test suite()
     {
         final TestSuite suite = new SeleniumMultiTestSuite();
         suite.addTestSuite(TestMultiBrowserSupport.class);
         suite.addTestSuite(TestMultiBrowserSupport.class);
         suite.addTestSuite(TestMultiBrowserSupport.class);
         suite.addTestSuite(TestMultiBrowserSupport.class);
         suite.addTestSuite(TestMultiBrowserSupport.class);
         return suite;
     }
    
}
