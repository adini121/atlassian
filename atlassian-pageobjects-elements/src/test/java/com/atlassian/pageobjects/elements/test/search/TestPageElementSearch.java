package com.atlassian.pageobjects.elements.test.search;

import com.atlassian.pageobjects.elements.PageElement;
import com.atlassian.pageobjects.elements.search.PageElementSearch;
import com.atlassian.pageobjects.elements.search.SearchQuery;
import com.atlassian.pageobjects.elements.test.AbstractPageElementBrowserTest;
import org.hamcrest.Matcher;
import org.junit.Test;

import javax.inject.Inject;

import static com.atlassian.pageobjects.elements.PageElements.hasClass;
import static com.atlassian.pageobjects.elements.PageElements.hasDataAttribute;
import static com.atlassian.pageobjects.elements.query.Poller.waitUntil;
import static com.atlassian.pageobjects.elements.testing.PageElementMatchers.withAttribute;
import static com.atlassian.pageobjects.elements.testing.PageElementMatchers.withDataAttribute;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.By.className;
import static org.openqa.selenium.By.id;
import static org.openqa.selenium.By.tagName;

public class TestPageElementSearch extends AbstractPageElementBrowserTest
{
    @Inject
    private PageElementSearch page;

    @Test
    public void shouldFindSinglePageElementById()
    {
        PageElement result = page.search()
                .by(id("single-element"))
                .find().first();

        assertTrue(result.isPresent());
        assertEquals("single-element", result.getAttribute("id"));
    }

    @Test
    public void shouldFindSinglePageElementByIdAsIterable()
    {
        assertResult(page.search().by(id("single-element")).find(), contains(withAttribute("id", "single-element")));
    }

    @Test
    public void shouldFindRowsWithFilter()
    {
        SearchQuery.Result<PageElement> result = page.search()
                .by(id("table-list"))
                .by(id("the-table"))
                .by(tagName("tr"), hasClass("even-row"))
                .find();

        assertResult(result, contains(
                withDataAttribute("name", "Even Row 1"),
                withDataAttribute("name", "Even Row 2"),
                withDataAttribute("name", "Even Row 3")
        ));
    }

    @Test
    public void findFirstNestedElement()
    {
        PageElement result = page.search()
                .by(id("parent-1"))
                .by(className("parent-2-class")).filter(hasDataAttribute("find-me"))
                .by(tagName("ul"))
                .by(tagName("li")).filter(withDataAttribute("pick-me", "yes"))
                .find().first();

        assertTrue(result.isPresent());
        assertEquals("Yes-1", result.getText());
    }

    private <R> void assertResult(SearchQuery.Result<R> result, Matcher<Iterable<? extends R>> matcher)
    {
        assertThat(result.all(), matcher);
        assertThat(result.supplier().get(), matcher);
        waitUntil(result.timed(), matcher);
    }
}
