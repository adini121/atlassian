package com.atlassian.selenium;

import com.thoughtworks.selenium.DefaultSelenium;

/**
 * A utility class which collects common browser interaction tasks
 */
public class SeleniumClient extends DefaultSelenium
{
    /**
     * The maximum page load wait time used by Selenium. Should be used
     * by tests whenever they wait for pages to load.
     */
    protected final long PAGE_LOAD_WAIT;
    protected final long ACTION_WAIT;


    public SeleniumClient(SeleniumConfiguration config)
    {
        super(new HtmlDumpingHttpCommandProcessor(config.getServerLocation(), config.getServerPort(), config.getBrowserStartString(), config.getBaseUrl()));

        // todo do we need this?
        this.PAGE_LOAD_WAIT = Long.valueOf(config.getPageLoadWait());
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

    public void waitForCondition(String javascript, long timeoutMillis)
    {
        waitForCondition(javascript, Long.toString(timeoutMillis));
    }

    /**
     * Executes the given Javascript in the context of the text page and waits for it to evaluate to true
     * for a maximum of {@link #ACTION_WAIT} milliseconds.
     */
    public void waitForCondition(String javascript)
    {
        waitForCondition(javascript, ACTION_WAIT);
    }

    /**
     * Click the element with the given ID and optionally wait for the page to load, using the default timeout.
     *
     * @param target the element to click, specified using Selenium selector syntax
     * @param waitForPageToLoad whether to wait for the page to reload. Don't use this unless the page is completely
     * reloaded.
     * @see #click(String, boolean)
     */
    public void click(String target, boolean waitForPageToLoad)
    {
        super.click(target);
        if (waitForPageToLoad)
            super.waitForPageToLoad(String.valueOf(PAGE_LOAD_WAIT));
    }

    /**
     * Click the element with the given ID and wait for the page to load, for a maximum of timeoutMillis.
     * <p/>
     * Do not use this method if the page does not reload.
     *
     * @param target the element to click, specified using Selenium selector syntax
     * @param timeoutMillis the maximum number of milliseconds to wait for the page to load. Polling takes place
     * more frequently.
     * @see #click(String, boolean)
     */
    public void click(String target, long timeoutMillis)
    {
        super.click(target);
        super.waitForPageToLoad(Long.toString(timeoutMillis));
    }

    /**
     * This will type into a field by sending key down / key press / key up
     * events.
     * @param locator Uses the Selenium locator syntax
     * @param key The key to be pressed
     *
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

            keyPress(locator, key);

            // some browser don't actually input any characters on these events
            // supposedly to prevent JS spoof attacks. So we type for them
            if (!SeleniumStarter.getInstance().getUserAgent().equals("firefox"))
            {
                super.type(locator, sb.toString());
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
