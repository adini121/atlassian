package com.atlassian.pageobjects.elements;

import java.util.List;

/**
 * Represents a standard multi-select HTML element.
 *
 */
public interface MultiSelectElement extends Element
{
    /**
     * All options
     *
     * @return all options of this multi-select
     */
    List<Option> all();

    /**
     * Selected options.
     *
     * @return selected options of this multi-select
     */
    List<Option> selected();

    /**
     * Add given option to the current selection.
     *
     * @param option option to add
     * @return this multi-select instance
     */
    public MultiSelectElement select(Option option);

    /**
     * Remove given option from the current selection
     *
     * @param option option to remove
     * @return this multi-select instance
     */
    public MultiSelectElement unselect(Option option);

    /**
     * Add all options to the current selection.
     *
     * @return this multi-select instance
     */
    public MultiSelectElement selectAll();

    /**
     * Remove all options from the current selection
     *
     * @return this multi-select instance
     */
    public MultiSelectElement unselectAll();

}
