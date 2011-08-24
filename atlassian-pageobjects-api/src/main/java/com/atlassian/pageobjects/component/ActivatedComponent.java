package com.atlassian.pageobjects.component;

/**
 * @since v2.1
 */
public interface ActivatedComponent<T,E>
{
    E getTrigger();
    E getView();
    T open();
    T close();
    boolean isOpen();
}
