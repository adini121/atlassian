package com.atlassian.webdriver.utils;

import junit.framework.TestCase;
import org.openqa.selenium.remote.DesiredCapabilities;

public class WebDriverUtilTest extends TestCase
{

    public void testCreateCapabilitiesFromStringOneItem() throws Exception
    {
        assertEquals("osx", WebDriverUtil.createCapabilitiesFromString("so=osx").getCapability("so"));
    }

    public void testCreateCapabilitiesFromStringMoreThanOneItem() throws Exception
    {
        DesiredCapabilities capabilities = WebDriverUtil.createCapabilitiesFromString("so=osx;browser=safari");
        assertEquals("osx", capabilities.getCapability("so"));
        assertEquals("safari", capabilities.getCapability("browser"));
    }

    public void testCreateCapabilitiesFromStringDuplicatedItems() throws Exception
    {
        DesiredCapabilities capabilities = WebDriverUtil.createCapabilitiesFromString("so=osx;browser=safari;browser=firefox");
        assertEquals("osx", capabilities.getCapability("so"));
        assertEquals("firefox", capabilities.getCapability("browser")); // it will pick up the latest
    }

    public void testCreateCapabilitiesFromNullString() throws Exception
    {
        DesiredCapabilities capabilities = WebDriverUtil.createCapabilitiesFromString(null);
        assertEquals(0, capabilities.asMap().size());
    }

    public void testCreateCapabilitiesFromEmptyString() throws Exception
    {
        DesiredCapabilities capabilities = WebDriverUtil.createCapabilitiesFromString("");
        assertEquals(0, capabilities.asMap().size());
    }
}
