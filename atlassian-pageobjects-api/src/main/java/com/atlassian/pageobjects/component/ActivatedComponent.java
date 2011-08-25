package com.atlassian.pageobjects.component;

/**
 * @since v2.1
 */
public interface ActivatedComponent<T>
{
    T open();
    T close();
    boolean isOpen();
}
