package com.atlassian.selenium;

import com.thoughtworks.selenium.DefaultSelenium;

/**
 * Extends the {@link DefaultSelenium} client to provide a more sensible implementation
 * as well some extra utility methods such as keypress.
 */
public class SeleniumClient extends DefaultSelenium
{
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
        if (reset)
        {
            super.type(locator, "");
        }
        char[] chars = string.toCharArray();

        // Some browsers clear the text field when we start typing,
        // so we need to pre-populate this string with the existing contents
        StringBuffer sb = new StringBuffer(super.getValue(locator));
        for (int i = 0; i < chars.length; i++)
        {
            char aChar = chars[i];
            String key = Character.toString(aChar);
            sb.append(aChar);

            super.keyDown(locator, key);
            // some browser don't actually input any characters on these events
            // supposedly to prevent JS spoof attacks. So we type for them
            if (!SeleniumStarter.getInstance().getUserAgent().equals("firefox"))
            {
                super.type(locator, sb.toString());
            }

            super.keyPress(locator, key);
            super.keyUp(locator, key);
            
            try{
                Thread.sleep(ACTION_WAIT);
            }
            catch(InterruptedException ie)
            {
                throw new RuntimeException(ie);
            }
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
}
