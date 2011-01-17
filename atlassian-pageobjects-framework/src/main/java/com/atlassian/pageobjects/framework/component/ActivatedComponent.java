package com.atlassian.pageobjects.framework.component;

import com.atlassian.pageobjects.framework.element.Element;

/**
 * Represents a component that needs to be activated to interact with.
 */
public interface ActivatedComponent<T>
{
    /**
     * Element that when activated opens the view of this component.
     */
    Element trigger();

    /**
     * The view Element, hidden or not present until activated.
     */
    Element view();

    /**
     * Opens the view and waits until UI is ready for interaction.
     */
    T open();

    /**
     * Whether the view is currently opened.
     */
    boolean isOpen();
}
