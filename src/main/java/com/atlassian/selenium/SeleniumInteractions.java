package com.atlassian.selenium;

import com.thoughtworks.selenium.Selenium;

/**
 * A utility class which collects common browser interaction tasks
 */

public class SeleniumInteractions extends AbstractSeleniumUtil
{
    private final int ACTION_WAIT;

    public SeleniumInteractions(Selenium selenium, String pageLoadWait, int actionWait)
    {
        super(selenium, pageLoadWait);
        ACTION_WAIT = actionWait;
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
        selenium.keyDown(locator, key);
        selenium.keyPress(locator, key);
        selenium.keyUp(locator, key);
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
            selenium.type(locator, "");
        }
        char[] chars = string.toCharArray();

        // Some browsers clear the text field when we start typing,
        // so we need to pre-populate this string with the existing contents
        StringBuffer sb = new StringBuffer(selenium.getValue(locator));
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
                selenium.type(locator, sb.toString());
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

    /**
     * Will tell selenium to mouseOver the locator and wait. The Selenium implementation
     * doesn't wait and this sometimes causes problems
     * @param locator String describing the element to mouseOver. Uses the standard Selenium
     * conventions
     */
    public void mouseOver(String locator)
    {
        selenium.mouseOver(locator);
        sleep(ACTION_WAIT);
    }

    /**
     * Will tell selenium to mouseDown on the locator and wait. The Selenium implementation
     * doesn't wait and this sometimes causes problems
     * @param locator String describing the element to mouseDown. Uses the standard Selenium
     * conventions
     */
    public void mouseDown(String locator)
    {
        selenium.mouseDown(locator);
        sleep(ACTION_WAIT);
    }

    private void sleep(int millis)
    {
        try
        {
            Thread.sleep(millis);
        }
        catch (InterruptedException e)
        {
            throw new RuntimeException("Thread was interrupted", e);
        }
    }

    public void selectOption(String selectName, String label) {
        // In some browsers (i.e. Safari) the select items have funny padding
        // so we need to use this funny method to find how the select item is
        // padded so that it can be matched
        String[] options = selenium.getSelectOptions(selectName);
        int i = 0;
        for(; i < options.length; i++)
        {
            if(options[i].trim().equals(label))
            {
                break;
            }
        }
        if(i < options.length){
            selenium.select(selectName, options[i]);
        }
    }

    
}
