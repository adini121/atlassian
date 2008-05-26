package com.atlassian.selenium;

import com.thoughtworks.selenium.Selenium;

/**
 * Conditions used in waitTill... methods in the @class{SeleniumAssertions} 
 *
 * @since v3.12
 */
public interface Condition {
    boolean executeTest(Selenium selenium);
}
