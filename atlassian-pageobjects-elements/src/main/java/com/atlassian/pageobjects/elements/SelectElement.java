package com.atlassian.pageobjects.elements;

import java.util.List;

/**
 * Represents a standard select HTML components.
 */
public interface SelectElement
{
    /**
     * All options
     *
     * @return all options of this select
     */
    List<Option> all();

    /**
     * Selected option of this select.
     *
     * @return selected option of this select
     */
    Option selected();

    /**
     * Select given <tt>option</tt>.
     *
     * @param option option to select
     * @return this select instance
     */
    public SelectElement select(Option option);
    
}
