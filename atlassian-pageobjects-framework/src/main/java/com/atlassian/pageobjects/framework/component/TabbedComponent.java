package com.atlassian.pageobjects.framework.component;


import com.atlassian.pageobjects.framework.element.Element;

import java.util.List;

/**
 * Represents a component that has multiple tabs with content.
 */
public interface TabbedComponent
{
    /**
     * Returns element that represents the selected tab.
     */
    Element selectedTab();

    /**
     * Returns element that represents the selected tab's view.
     */
    Element selectedView();

    /**
     * Returns all elements that represent the tabs.
     */
    List<Element> tabs();

    /**
     * Open's the given tab's view.
     */
    Element openTab(Element tab);
}