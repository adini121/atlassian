package com.atlassian.pageobjects;

import com.atlassian.pageobjects.page.PageObject;

/**
 * TODO: Document this class / interface here
 *
 * @since v1.0
 */
public interface Activatable
{
    public <T extends PageObject> T activate(Link<T> link);
}
