package com.atlassian.pageobjects.elements;

/**
 * <p/>
 * Encapsulates Javascript functionality of the {@link com.atlassian.pageobjects.elements.PageElement}.
 *
 * <p/>
 * {@link PageElement#isPresent()} of the corresponding page element must return <code>true</code>
 * before any of the methods of this interface are invoked, otherwise {@link org.openqa.selenium.NoSuchElementException}
 * will be raised.
 *
 * @since 2.1
 */
public interface PageElementJavascript
{

    /**
     * Access to mouse events for the associated page element.
     *
     * @return mouse events for the element
     */
    PageElementMouseJavascript mouse();


    /**
     * Access to form events for the associated page element.
     *
     * @return form events for the element
     */
    PageElementFormJavascript form();


    // TODO NOT including this. This is low-priority and tricky to implement
//    /**
//     * Access to keyboard events for the associated page element.
//     *
//     * @return keyboard events for the element
//     */
//    PageElementKeyboardJavascript keyboard();

    /**
     * <p/>
     * Executes custom script on this element.
     *
     * <p/>
     * The corresponding HTML element will be available in the executing script under <tt>arguments[0]</tt> entry.
     * The provided <tt>arguments</tt> will be available under subsequents entries in the 'arguments' magic variable, as
     * stated in {@link org.openqa.selenium.JavascriptExecutor#executeScript(String, Object...)}.
     *
     * <p/>
     * The arguments and return type are as in
     * {@link org.openqa.selenium.JavascriptExecutor#executeScript(String, Object...)} with one exception - when a
     * DOM element is return from the script, a corresponding {@link com.atlassian.pageobjects.elements.PageElement}
     * instance will be returned from this method
     *
     * @param script javascript to execute
     * @param arguments custom arguments to the script. a number, a boolean, a String,
     * a {@link com.atlassian.pageobjects.elements.PageElement}, a {@link org.openqa.selenium.WebElement} or a List of
     * any combination of the above
     * @return One of Boolean, Long, String, List or {@link com.atlassian.pageobjects.elements.PageElement}. Or null.
     * @see org.openqa.selenium.JavascriptExecutor#executeScript(String, Object...)
     */
    public Object execute(String script, Object... arguments);

    /**
     * <p/>
     * Executes custom script on this element asynchronously.
     *
     * <p/>
     * All rules and conditions of {@link #execute(String, Object...)} apply, except the last argument in the
     * 'arguments' magic variable in the script is a callback that has to be invoked and given result of the script
     * so that this method returns. See {@link org.openqa.selenium.JavascriptExecutor#executeAsyncScript(String, Object...)}
     * for details.
     *
     * @param script javascript to execute
     * @param  arguments custom arguments to the script. a number, a boolean, a String,
     * a {@link com.atlassian.pageobjects.elements.PageElement}, a {@link org.openqa.selenium.WebElement} or a List of
     * any combination of the above
     * @return One of Boolean, Long, String, List or {@link com.atlassian.pageobjects.elements.PageElement}. Or null.
     * @see org.openqa.selenium.JavascriptExecutor#executeAsyncScript(String, Object...) (String, Object...)
     * @see #execute(String, Object...)
     */
    public Object executeAsync(String script, Object... arguments);


}