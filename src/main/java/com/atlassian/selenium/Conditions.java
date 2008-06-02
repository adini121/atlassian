package com.atlassian.selenium;

import com.thoughtworks.selenium.Selenium;

/**
 * A set of conditions used in waitTill... methods in the @class{SeleniumAssertions}
 *
 * @since v3.12
 */
public class Conditions {

    public static Condition isVisible(final String element)
    {
        return new Condition() {
            public boolean executeTest(Selenium selenium)
            {
                return (selenium.isElementPresent(element) && selenium.isVisible(element));
            }
        };
    }

    public static Condition isNotVisible(final String element)
    {
        return new Condition() {
            public boolean executeTest(Selenium selenium)
            {
                return (!selenium.isVisible(element));
            }
        };
    }

    public static Condition isPresent(final String element)
    {
        return new Condition() {
            public boolean executeTest(Selenium selenium)
            {
                return (selenium.isElementPresent(element));
            }
        };
    }

    public static Condition isNotPresent(final String element)
    {
        return new Condition() {
            public boolean executeTest(Selenium selenium)
            {
                return (!selenium.isElementPresent(element));
            }
        };
    }
}