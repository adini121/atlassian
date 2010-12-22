package com.atlassian.webdriver.pageobjects.components;


/**
 * TODO: Document this class / interface here
 *
 * @since v1.0
 */
public interface DropdownMenu<D extends DropdownMenu>
{
    public D open();
    public boolean isOpen();
    public D close();
}
