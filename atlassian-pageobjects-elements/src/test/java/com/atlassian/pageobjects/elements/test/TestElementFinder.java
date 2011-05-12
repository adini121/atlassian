package com.atlassian.pageobjects.elements.test;


import com.atlassian.pageobjects.elements.*;
import com.atlassian.pageobjects.elements.test.pageobjects.page.ElementsPage;
import com.atlassian.pageobjects.elements.test.pageobjects.page.SelectElementPage;
import org.junit.Test;
import org.openqa.selenium.By;

import java.util.List;

import static junit.framework.Assert.assertEquals;

public class TestElementFinder extends AbstractFileBasedServerTest
{
    @Test
    public void testFindAll()
    {
        ElementFinder elementFinder = product.getPageBinder().bind(ElementFinder.class);
        product.visit(ElementsPage.class);

        List<PageElement> items = elementFinder.findAll(By.className("test4_item"));
        assertEquals(3, items.size());
        assertEquals("Item 1", items.get(0).getText());
        assertEquals("Item 2", items.get(1).getText());
        assertEquals("Item 3", items.get(2).getText());
    }

    @Test
    public void testFindAll_WithCustomClass()
    {
        ElementFinder elementFinder = product.getPageBinder().bind(ElementFinder.class);
        product.visit(SelectElementPage.class);

        List<SelectElement> singleSelects = elementFinder.findAll(By.tagName("select"), SelectElement.class);
        assertEquals(6, singleSelects.size()); // Collection will contain select and multiselect

        SelectElement singleSelect = singleSelects.get(0);
        assertEquals("Option1_Text", singleSelect.getSelected().text());
        singleSelect.select(Options.text("Option2_Text"));
        assertEquals("Option2_Text", singleSelect.getSelected().text());

         List<MultiSelectElement> multiSelects = elementFinder.findAll(By.tagName("select"), MultiSelectElement.class);
        assertEquals(6, multiSelects.size()); // Collection will contain select and multiselect

        MultiSelectElement multiSelect = multiSelects.get(3);
        assertEquals(0, multiSelect.getSelected().size());
        multiSelect.select(Options.text("Option2_Text"));
        assertEquals(1, multiSelect.getSelected().size());
    }
}
