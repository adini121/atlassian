package com.atlassian.selenium;

import com.thoughtworks.selenium.DefaultSelenium;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

/**
 * Extends the {@link DefaultSelenium} client to provide a more sensible implementation
 * as well some extra utility methods such as keypress.
 */
public class SeleniumClient extends DefaultSelenium
{
    public enum Browser
    {
        FIREFOX("firefox"), OPERA("opera"), SAFARI("safari"), UNKNOWN("unkown"), IE("ie");

        private final String name;

        Browser(String name)
        {
            this.name = name;
        }

        public String getName()
        {
            return name;
        }

        public static Browser typeOf(String browserStartString)
        {
            for (Browser browser : Browser.values())
            {
                if(browserStartString.contains(browser.getName()))
                {
                    return browser;
                }
            }
            return null;
        }

        
    }

    private Browser browser;

    /**
     * The maximum page load wait time used by Selenium. This value is set with
     * {@link SeleniumConfiguration#getPageLoadWait()}.
     */
    protected final long PAGE_LOAD_WAIT;

    /**
     * The maximum wait time for actions that don't require page loads. This value is set with
     * {@link SeleniumConfiguration#getActionWait()}.
     */
    protected final long ACTION_WAIT;

    public SeleniumClient(SeleniumConfiguration config)
    {
        super(new HtmlDumpingHttpCommandProcessor(config.getServerLocation(), config.getServerPort(), config.getBrowserStartString(), config.getBaseUrl()));

        this.PAGE_LOAD_WAIT = config.getPageLoadWait();
        this.ACTION_WAIT = config.getActionWait();

        browser = Browser.typeOf(config.getBrowserStartString());
        
        SeleniumStarter.getInstance().setUserAgent(browser.getName());
    }

    public Browser getBrowser()
    {
        return browser;
    }
    
    /**
     * Unlike {@link DefaultSelenium#open}, this opens the provided URL relative to the application context path.
     * It also waits for the page to load -- a maximum of {@link #PAGE_LOAD_WAIT} before returning.
     */
    public void open(String url)
    {
        open(url, PAGE_LOAD_WAIT);
    }

    /**
     * Wait for page to load doesn't work the case of non-HTML based resources (like images).
     * So sometimes you really do want to open a url without waiting.
     * @param url
     */
    public void openNoWait(String url)
    {
        super.open(url);
    }

    /**
     * Opens the given URL and waits a maximum of timeoutMillis for the page to load completely.
     */
    public void open(String url, long timeoutMillis)
    {
        super.open(url);
        super.waitForPageToLoad(String.valueOf(timeoutMillis));
    }

    /**
     * Overloads {@link #waitForPageToLoad(String)} to take in a long.
     */
    public void waitForPageToLoad(long timeoutMillis)
    {
        super.waitForPageToLoad(String.valueOf(timeoutMillis));
    }

    /**
     * Waits for the page to load with the default timeout configured in {@link SeleniumConfiguration}.
     */
    public void waitForPageToLoad()
    {
        waitForPageToLoad(PAGE_LOAD_WAIT);
    }

    /**
     * Executes the given Javascript in the context of the text page and waits for it to evaluate to true
     * for a maximum of {@link #ACTION_WAIT} milliseconds.
     * @see #waitForCondition(String, long) if you would like to specify your own timeout.
     */
    public void waitForCondition(String javascript)
    {
        waitForCondition(javascript, ACTION_WAIT);
    }

    /**
     * Executes the given Javascript in the context of the text page and waits for it to evaluate to true
     * for a maximum of timeoutMillis.
     */
    public void waitForCondition(String javascript, long timeoutMillis)
    {
        waitForCondition(javascript, Long.toString(timeoutMillis));
    }

    /**
     * Click the element with the given locator and optionally wait for the page to load, using {@link #PAGE_LOAD_WAIT}.
     *
     * @param locator the element to click, specified using Selenium selector syntax
     * @param waitForPageToLoad whether to wait for the page to reload. Don't use this unless the page is completely
     * reloaded.
     * @see #click(String, long) if you would like to specify your own timeout.
     */
    public void click(String locator, boolean waitForPageToLoad)
    {
        super.click(locator);
        if (waitForPageToLoad)
            super.waitForPageToLoad(String.valueOf(PAGE_LOAD_WAIT));
    }

    /**
     * Submit the named form locator and optionally wait for the page to load, using {@link #PAGE_LOAD_WAIT}.
     *
     * @param form to click, specified using Selenium selector syntax
     * @param waitForPageToLoad whether to wait for the page to reload. Don't use this unless the page is completely
     * reloaded.
     * @see #submit(String, long) if you would like to specify your own timeout.
     */
    public void submit(String form, boolean waitForPageToLoad)
    {
        super.submit(form);
        if (waitForPageToLoad)
            super.waitForPageToLoad(String.valueOf(PAGE_LOAD_WAIT));
    }

    /**
     * Click the element with the given locator and wait for the page to load, for a maximum of timeoutMillis.
     * <p/>
     * Do not use this method if the page does not reload.
     *
     * @param locator the element to click, specified using Selenium selector syntax
     * @param timeoutMillis the maximum number of milliseconds to wait for the page to load. Polling takes place
     * more frequently.
     * @see #click(String, boolean) if you would like to use the default timeout
     */
    public void click(String locator, long timeoutMillis)
    {
        super.click(locator);
        super.waitForPageToLoad(Long.toString(timeoutMillis));
    }

    /**
     * Submit the given form and wait for the page to load, for a maximum of timeoutMillis.
     * <p/>
     * Do not use this method if the page does not reload.
     *
     * @param form the form to submit
     * @param timeoutMillis the maximum number of milliseconds to wait for the page to load. Polling takes place
     * more frequently.
     * @see #click(String, boolean) if you would like to use the default timeout
     */
    public void submit(String form, long timeoutMillis)
    {
        super.submit(form);
        super.waitForPageToLoad(Long.toString(timeoutMillis));
    }

    /**
     * This will type into a field by sending key down / key press / key up events.
     * @param locator Uses the Selenium locator syntax
     * @param key The key to be pressed
     */
    public void keyPress(String locator, String key)
    {
        super.keyDown(locator, key);
        super.keyPress(locator, key);
        super.keyUp(locator, key);
    }

    /**
     * This will type into a field by first blanking it out and then sending key down / key press / key up
     * events.
     *
     * @param locator the Selenium locator
     * @param string  the string to type
     * @param reset   Should the field be reset first?
     */
    public void typeWithFullKeyEvents(String locator, String string, boolean reset)
    {
        super.focus(locator);
        if (reset)
        {
            super.type(locator, "");
        }

        // The typeKeys method doesn't work properly in Firefox
        if (Browser.FIREFOX.equals(browser))
        {
            char[] chars = string.toCharArray();
            for (char aChar : chars)
            {
                super.focus(locator);
                //Using codes because the methhod doesn't worki n  
                keyPress(locator, "\\" + (int) aChar);
            }
        }
        else
        {
            if(!reset)
            {
                string = super.getValue(locator) + string;
            }
            super.type(locator, string);
            super.typeKeys(locator, string);
        }
    }

    /**
     * This will type into a field by first blanking it out and then sending key down / key press / key up
     * events. This really only calls {@link #typeWithFullKeyEvents(String,String,boolean)})}
     *
     * @param locator - the usual Selenium locator
     * @param string  the string to type into a field
     */
    public void typeWithFullKeyEvents(String locator, String string)
    {
        typeWithFullKeyEvents(locator, string, true);
    }

    public void selectOption(String selectName, String label)
    {
        // In some browsers (i.e. Safari) the select items have funny padding
        // so we need to use this funny method to find how the select item is
        // padded so that it can be matched
        String[] options = super.getSelectOptions(selectName);
        int i = 0;
        for(; i < options.length; i++)
        {
            if(options[i].trim().equals(label))
            {
                break;
            }
        }
        if(i < options.length)
        {
            super.select(selectName, options[i]);
        }
    }

    /**
     * Checks a checkbox given a name and value.
     */
    public void check(String name, String value)
    {
        check("name=" + name + " value=" + value);
    }

    public void clickLinkWithText(String text, boolean waitForPageToLoad)
    {
        super.click("link=" + text);
        if (waitForPageToLoad) waitForPageToLoad();
    }

    public void clickButton(String buttonText, boolean waitForPageToLoad)
    {
        clickElementWithXpath("//input[@value = '" + buttonText + "']");
        if (waitForPageToLoad) waitForPageToLoad();
    }

    public void clickButtonWithName(String buttonName, boolean waitForPageToLoad)
    {
        clickElementWithXpath("//input[@name = '" + buttonName + "']");
        if (waitForPageToLoad) waitForPageToLoad();
    }

    public void clickElementWithTitle(String title)
    {
        super.click("xpath=//*[@title='" + title + "']");
    }

    public void clickElementWithClass(String className)
    {
        super.click("css=." + className);
    }

    public void clickElementWithCss(String cssSelector)
    {
        super.click("css=" + cssSelector);
    }

    public void clickElementWithXpath(String xpath)
    {
        super.click("xpath=" + xpath);
    }

    public void typeInElementWithName(String elementName, String text)
    {
        super.type("name=" + elementName, text);
    }

    public void typeInElementWithCss(String cssSelector, String text)
    {
        super.type("css=" + cssSelector, text);
    }



    private void addJqueryLocator() throws IOException
    {
        String jqueryImpl = readFile("jquery-1.3.1.min.js");
        String jqueryLocStrategy = readFile("jquery-locationStrategy.js");
        //client.setExtensionJs(jqueryImpl);
        addScript(jqueryImpl, "jquery");
        addLocationStrategy("jquery", jqueryLocStrategy );
    }

    private static String readFile(String file) throws IOException
    {
        BufferedReader reader =  new BufferedReader(new InputStreamReader(ClassLoader.getSystemClassLoader().getResourceAsStream(file)));

        String line = reader.readLine();
        StringBuffer contents = new StringBuffer();

        while(line != null)
        {
            contents.append(line + "\n");
            line = reader.readLine();
        }

        return contents.toString();
    }

    public void start()
    {
        super.start();
        try {
            addJqueryLocator();
        }
        catch (IOException ioe)
        {
            System.err.println("Unable to load JQuery locator strategy: " + ioe);
            ioe.printStackTrace(System.err);
        }

    }



}
