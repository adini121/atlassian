package com.atlassian.pageobjects.framework.component;

import static org.junit.Assert.assertEquals;

/**
 *  Select usage example
 *
 */
public class SelectExample
{

    static void example()
    {
        Select select = null;
        select.select(Options.id("option-id"));
        select.selectDefault();
        assertEquals("-1", select.selected().value());
        assertEquals("Default value", select.selected().label());
    }
}
