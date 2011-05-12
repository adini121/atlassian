package com.atlassian.pageobjects.elements;

/**
 * Represents an input element with type=checkbox
 */
public interface CheckboxElement extends PageElement
{

    /**
     * Checks this Checkbox
     *
     * @return CheckboxElement
     */
    CheckboxElement check();

    /**
     * Unchecks this Checkbox
     *
     * @return CheckboxElement
     */
    CheckboxElement uncheck();
}
