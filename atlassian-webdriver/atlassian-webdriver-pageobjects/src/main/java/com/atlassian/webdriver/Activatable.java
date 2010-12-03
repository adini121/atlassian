package com.atlassian.webdriver;

import com.atlassian.webdriver.Link;
import com.atlassian.webdriver.PageObject;

/**
 * TODO: Document this class / interface here
 *
 * @since v1.0
 */
public interface Activatable
{
    public <T extends PageObject> T activate(Link<T> link);
}
