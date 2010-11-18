package com.atlassian.webdriver.component.menu;

import com.atlassian.webdriver.Activatable;
import com.atlassian.webdriver.component.Component;

/**
 * TODO: Document this class / interface here
 *
 * @since v1.0
 */
public interface DropdownMenu<T extends Component> extends Activatable
{
    public void open();
    public boolean isOpen();
    public void close();
}
