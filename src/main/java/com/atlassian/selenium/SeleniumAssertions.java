package com.atlassian.selenium;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.SeleniumException;
import junit.framework.Assert;
import org.apache.log4j.Logger;

/**
 * This provides helper methods for selenium assertions.  This
 * mostly means waiting for events to occur (i.e. a dropdown to
 * appear after a certain timeout, etc)
 */
public class SeleniumAssertions extends AbstractSeleniumUtil
{
    private static final Logger log = Logger.getLogger(SeleniumAssertions.class);

    public SeleniumAssertions(Selenium selenium, String pageLoadWait)
    {
        super(selenium, pageLoadWait);
    }


    /**
     * This will wait until an element is visible.  If it doesnt become visible in
     * maxMillis fail.
     *
     * @param locator   the selenium element locator
     * @param maxMillis how long to wait as most in milliseconds
     */
    public void visibleByTimeout(String locator, int maxMillis)
    {
        int maxSleeps = Math.max(maxMillis, 500) / 500;
        byTimeout(Conditions.isVisible(locator), 500, maxSleeps, "Element : " + locator + " did not become visible in " + maxMillis + " ms");
    }

    public void notVisibleByTimeout(String locator, int maxMillis)
    {
        int maxSleeps = Math.max(maxMillis, 500) / 500;
        byTimeout(Conditions.isNotVisible(locator), 500, maxSleeps, "Element : " + locator + " did not become invisible in " + maxMillis + " ms");
    }

    /**
     * This will wait until an element is not present.  If it doesnt become not present in
     * maxMillis
     *
     * @param locator   the selenium element locator
     * @param maxMillis how long to wait as most in milliseconds
     */
    public void elementNotPresentByTimeout(String locator, int maxMillis)
    {
        int maxSleeps = Math.max(maxMillis, 500) / 500;
        byTimeout(Conditions.isNotPresent(locator), 500, maxSleeps, "Element : " + locator + " did not become not present in " + maxMillis + " ms");
    }


    /**
     * This will wait until an element is visible.  If it doesnt become visible in
     * maxMillis it will simply return
     *
     * @param locator   the selenium element locator
     * @param maxMillis how long to wait as most in milliseconds
     */
    public void waitTillVisibleQuietly(String locator, int maxMillis)
    {
        try
        {
            int maxSleeps = Math.max(maxMillis, 500) / 500;
            byTimeout(Conditions.isVisible(locator), 500, maxSleeps, "Quiet wait! This failure message should not appear", true);
        }
        catch (SeleniumException se)
        {
            log.info("Exception thrown while waiting for element to become visible. Ignoring this error: " + se);
        }
    }

    public void byTimeout(Condition condition, long sleep, long maxSleeps)
    {
        byTimeout(condition, sleep, maxSleeps, "timeout occured after '" + maxSleeps + "' sleeps of '" + sleep + "' ms long");
    }

    /**
     * Utility method for asserting that some condition is fulfilled by (sleep X maxSleeps)
     * @param condition The condition to be fulfilled by the timeout
     * @param sleep The lenght of time to sleep between executions of the condition
     * @param maxSleeps The maximum number of times the condition will be executed before an assertion is made
     * @param failMsg The assertion message to be displayed if the condition is not fulfilled
     */
    public void byTimeout(Condition condition, long sleep, long maxSleeps, String failMsg)
    {
        byTimeout(condition, sleep, maxSleeps, failMsg, false);
    }

    /**
     * @see{byTimeout}
     * @param quietlyReturn boolean flag, whether an assertion should be made in the failure condition
     */
    private void byTimeout(Condition condition, long sleep, long maxSleeps, String failMsg, boolean quietlyReturn)
    {
        for (int second = 0; ; second++)
        {
            if (second >= maxSleeps)
            {

                if(!quietlyReturn)
                {
                    log.error("Dumping HTML Source [\n\n\n" + selenium.getHtmlSource() + "\n\n\n]");
                    Assert.assertTrue(failMsg, false);
                }
                return;
            }

            try
            {
                if (condition.executeTest(selenium))
                {
                    break;
                }
            }
            catch (Exception e)
            {
                // it may throw SeleniumExceptions so we catch them and wait till timeout since
                // an exception may be ok
                log.info("Exception thrown while waiting for condition " + e);
            }
            try
            {
                Thread.sleep(sleep);
            }
            catch (InterruptedException e)
            {
                throw new RuntimeException("Thread was interupted", e);
            }
        }

    }

    /**
     * @param text Asserts that text is present in the current page
     */
    public void textPresent(String text)
    {
        Assert.assertTrue("Expected text not found in response: '" + text + "'", selenium.isTextPresent(text));
    }

    /**
     * @param text Asserts that text is not present in the current page
     */
    public void textNotPresent(String text)
    {
        Assert.assertFalse("Un-expected text found in response: '" + text + "'", selenium.isTextPresent(text));
    }

    /**
     * Asserts that a given element has a specified value
     * @param locator Locator for element using the standard selenium locator syntax
     * @param value The value the element is expected to contain
     */
    public void formElementEquals(String locator, String value)
    {
        Assert.assertEquals("Element with id '" + locator + "' did not have the expected value '" + value + "'", value, selenium.getValue(locator));
    }

    /**
     * Asserts that a given element is present
     * @param locator Locator for the element that should be present given using the standard selenium locator syntax
     */
    public void elementPresent(String locator)
    {
        Assert.assertTrue("Expected element not found in response: '" + locator + "'", selenium.isElementPresent(locator));
    }

    /**
     * Asserts that a given element is not present on the current page
     * @param locator Locator for the element that should not be present given using the standard selenium locator syntax
     */
    public void elementNotPresent(String locator)
    {
        Assert.assertFalse("Un-expected element found in response: '" + locator + "'", selenium.isElementPresent(locator));
    }

    /**
     * Asserts that a given element is present and is visible. Under some browsers just calling the seleinium.isVisible method
     * on an element that doesn't exist causes selenium to throw an exception.
     * @param locator Locator for the element that should be visible specified in the standard selenium syntax
     */
    public void elementVisible(String locator)
    {
        Assert.assertTrue("Expected element not visible in response: '" + locator + "'", selenium.isElementPresent(locator) && selenium.isVisible(locator));
    }

    /**
     * Asserts that a given element is not present and visible. Calling selenium's native selenium.isVisible method on
     * an element that doesn't exist causes selenium to throw an exception
     * @param locator Locator for the element that should not be visible specified in the standard selenium syntax
     */
    public void elementNotVisible(String locator)
    {
        Assert.assertFalse("Un-expected element visible in response: '" + locator + "'", selenium.isElementPresent(locator) && selenium.isVisible(locator));
    }

    /**
     * Asserts that a particular piece of HTML is present in the HTML source. It is recommended that the elementPresent, elementHasText or some other method
     * be used because browsers idiosyncratically add white space to the HTML source
     * @param html Lower case representation of HTML string that should not be present
     */
    public void assertHtmlPresent(String html)
    {
        Assert.assertTrue("Expected HTML not found in response: '" + html + "'", selenium.getHtmlSource().toLowerCase().indexOf(html) >= 0);
    }

    /**
     * Asserts that a particular piece of HTML is not present in the HTML source. It is recommended that the elementNotPresent, elementDoesntHaveText or
     * some other method be used because browsers idiosyncratically add white space to the HTML source
     * @param html Lower case representation of HTML string that should not be present
     */
    public void assertHtmlNotPresent(String html)
    {
        Assert.assertFalse("Unexpected HTML found in response: '" + html + "'", selenium.getHtmlSource().toLowerCase().indexOf(html) >= 0);
    }

    /**
     * Asserts that the element specified by the locator contains the specified text
     * @param locator Locator given in standard selenium syntax
     * @param text The text that the element designated by the locator should contain
     */
    public void elementHasText(String locator, String text)
    {
        Assert.assertTrue("Element(s) with locator '" + locator +"' did not contain text '"+ text + "'", (selenium.getText(locator).indexOf(text) >= 0));
    }

    /**
     * Asserts that the element specified by the locator does not contain the specified text
     * @param locator Locator given in standard selenium syntax
     * @param text The text that the element designated by the locator should not contain
     */
    public void elementDoesntHaveText(String locator, String text)
    {
        Assert.assertFalse("Element(s) with locator '" + locator +"' did contained text '"+ text + "'", (selenium.getText(locator).indexOf(text) >= 0));
    }

    /**
     * Asserts that the element given by the locator has an attribute which contains the required value.
     * @param locator Locator given in standard selenium syntax
     * @param attribute The element attribute
     * @param value The value expected to be found in the element's attribute
     */
    public void attributeContainsValue(String locator, String attribute, String value)
    {
        String attributeValue = selenium.getAttribute(locator + "@" + attribute);
        Assert.assertTrue("Element with locator '" + locator + "' did not contain value '" + value + "' in attribute '" + attribute + "=" + attributeValue + "'", (attributeValue.indexOf(value) >= 0));
    }

    /**
     * Asserts that the element given by the locator has an attribute which does not contain the given value.
     * @param locator Locator given in standard selenium syntax
     * @param attribute The element attribute
     * @param value The value expected to be found in the element's attribute
     */
    public void attributeDoesntContainValue(String locator, String attribute, String value)
    {
        String attributeValue = selenium.getAttribute(locator + "@" + attribute);
        Assert.assertFalse("Element with locator '" + locator + "' did not contain value '" + value + "' in attribute '" + attribute + "'", (attributeValue.indexOf(value) >= 0));
    }
}
