package com.atlassian.pageobjects.framework.test;

import com.atlassian.pageobjects.framework.element.MultiSelectElement;
import com.atlassian.pageobjects.framework.element.Option;
import com.atlassian.pageobjects.framework.element.Options;
import com.atlassian.pageobjects.framework.element.SelectElement;
import com.atlassian.pageobjects.framework.test.pageobjects.page.SelectElementPage;
import org.junit.Test;
import org.openqa.selenium.By;

import java.util.List;

import static junit.framework.Assert.assertEquals;

public class TestSelectElemet extends AbstractFileBasedServerTest
{
    @Test
    public void testUseAnnotationToLocateSelectElements()
    {
        SelectElementPage page = product.visit(SelectElementPage.class);

        assertEquals(3, page.getSelectElement1().all().size());

    }

    @Test
    public void test_SingleSelect_All()
    {
       SelectElementPage page = product.visit(SelectElementPage.class);

       List<Option> options = page.findSelect(By.id("test1_Select")).all();
       assertEquals(3, options.size());

       assertEquals("test1_Option1", options.get(0).id());
       assertEquals("option1_Value", options.get(0).value());
       assertEquals("Option1_Text", options.get(0).text());

       assertEquals("test1_Option2", options.get(1).id());
       assertEquals("option2_Value", options.get(1).value());
       assertEquals("Option2_Text", options.get(1).text());

       assertEquals("test1_Option3", options.get(2).id());
       assertEquals("option3_Value", options.get(2).value());
       assertEquals("Option3_Text", options.get(2).text());

    }

    @Test
    public void test_SingleSelect_Selected()
    {
        SelectElementPage page =  product.visit(SelectElementPage.class);

        // verify that by default the selected option is the first one.
        assertEquals("option1_Value", page.findSelect(By.id("test1_Select")).selected().value());

        // verify selected when element is loaded with an option selected by default
        assertEquals("option2_Value", page.findSelect(By.id("test2_Select")).selected().value());
    }

    @Test
    public void test_SingleSelect_SelectingOption()
    {
        SelectElementPage page =  product.visit(SelectElementPage.class);

        SelectElement select = page.findSelect(By.id("test1_Select"));

        //select the 3rd option and verify
        select.select(Options.id("test1_Option3"));
        assertEquals("option3_Value", select.selected().value());

         //select the 2nd option and verify
        select.select(Options.id("test1_Option2"));
        assertEquals("option2_Value", select.selected().value());
    }

    @Test
    public void test_SingleSelect_SelectingOptionsWhenOnlyValueAndTextArePresent()
    {
        SelectElementPage page =  product.visit(SelectElementPage.class);

        SelectElement select = page.findSelect(By.id("test3_Select"));

        //verify default
        assertEquals("Option1_Text", select.selected().text());

        //select select third option by text
        select.select(Options.text("Option3_Text"));
        assertEquals("option3_Value", select.selected().value());
    }

    @Test
    public void test_MultiSelect_AllSelected()
    {
        SelectElementPage page =  product.visit(SelectElementPage.class);

         //verify select with no default
        assertEquals(0, page.findMultiSelect(By.id("test4_Select")).selected().size());

        //verify select with 1 default
        List<Option> defaultOptions = page.findMultiSelect(By.id("test5_Select")).selected();
        assertEquals(1, defaultOptions.size());
        assertEquals("option2_Value",defaultOptions.get(0).value());

        //verify select with multiple defaults
        defaultOptions = page.findMultiSelect(By.id("test6_Select")).selected();
        assertEquals(3, defaultOptions.size());
        assertEquals("option2_Value",defaultOptions.get(0).value());
        assertEquals("option4_Value",defaultOptions.get(1).value());
        assertEquals("option6_Value",defaultOptions.get(2).value());
    }

    @Test
    public void test_MultiSelect_SelectAll()
    {
        SelectElementPage page =  product.visit(SelectElementPage.class);

        //select with no defaults
        MultiSelectElement select = page.findMultiSelect(By.id("test4_Select"));
        select.selectAll();
        assertEquals(3, select.selected().size());

        //select with one defaults
        select = page.findMultiSelect(By.id("test5_Select"));
        select.selectAll();
        assertEquals(3, select.selected().size());
    }

    @Test
    public void test_MultiSelect_SelectingOptions()
    {
        SelectElementPage page =  product.visit(SelectElementPage.class);

        // select options when there was none by default
        MultiSelectElement select = page.findMultiSelect(By.id("test4_Select"));
        select.select(Options.id("test4_Option2"));
        assertEquals(1, select.selected().size());

        select.select(Options.id("test4_Option3"));
        assertEquals(2, select.selected().size());

        // select options when there where some by default
        select = page.findMultiSelect(By.id("test5_Select"));
        select.select(Options.id("test5_Option1")).select(Options.id("test5_Option3"));
        assertEquals(3, select.selected().size());
    }

    @Test
    public void test_MultiSelect_SelectingAlreadySelectedOptions()
    {
        SelectElementPage page =  product.visit(SelectElementPage.class);

        MultiSelectElement select = page.findMultiSelect(By.id("test5_Select"));
        select.select(Options.id("test5_Option2"));
        assertEquals(1, select.selected().size());

        //select same item 2 times
        select.select(Options.id("test5_Option3"));
        select.select(Options.id("test5_Option3"));
        assertEquals(2, select.selected().size());
    }

    @Test
    public void test_MultiSelect_UnselectingOptions()
    {
        SelectElementPage page =  product.visit(SelectElementPage.class);

        MultiSelectElement select = page.findMultiSelect(By.id("test5_Select"));
        select.unselect(Options.id("test5_Option2"));
        assertEquals(0, select.selected().size());

        select = page.findMultiSelect(By.id("test6_Select"));
        select.unselect(Options.id("test6_Option2")).unselect(Options.id("test6_Option4"));
        assertEquals(1, select.selected().size());

    }

    @Test
    public void test_MultiSelect_UnselectingAlreadyUnselectedOptions()
    {
        SelectElementPage page =  product.visit(SelectElementPage.class);

        MultiSelectElement select = page.findMultiSelect(By.id("test5_Select"));
        select.unselect(Options.id("test5_Option1"));
        assertEquals(1, select.selected().size());

        select = page.findMultiSelect(By.id("test6_Select"));
        select.unselect(Options.id("test6_Option1")).unselect(Options.id("test6_Option3"));
        assertEquals(3, select.selected().size());

    }
}
