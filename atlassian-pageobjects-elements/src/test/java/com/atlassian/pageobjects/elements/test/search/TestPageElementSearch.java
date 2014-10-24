package com.atlassian.pageobjects.elements.test.search;

import com.atlassian.pageobjects.elements.CheckboxElement;
import com.atlassian.pageobjects.elements.PageElement;
import com.atlassian.pageobjects.elements.WebDriverElement;
import com.atlassian.pageobjects.elements.search.PageElementSearch;
import com.atlassian.pageobjects.elements.search.SearchQuery;
import com.atlassian.pageobjects.elements.test.AbstractPageElementBrowserTest;
import com.atlassian.pageobjects.elements.test.pageobjects.page.PageElementSearchPage;
import com.atlassian.pageobjects.elements.timeout.TimeoutType;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.junit.Test;

import javax.inject.Inject;

import static com.atlassian.pageobjects.elements.PageElements.*;
import static com.atlassian.pageobjects.elements.query.Poller.waitUntil;
import static com.atlassian.pageobjects.elements.testing.PageElementMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.openqa.selenium.By.*;

@SuppressWarnings("unchecked")
public class TestPageElementSearch extends AbstractPageElementBrowserTest
{
    @Inject
    private PageElementSearch page;

    public void goToElementSearchPage()
    {
        product.visit(PageElementSearchPage.class);
    }

    // TODO test search multiple levels from non-existing element - no wait, no exception

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
        SearchQuery.PageElementResult<PageElement> result = page.search()
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
    public void shouldFindRowsWithFilterAndTransformOnResult()
    {
        SearchQuery.AnyResult<String> result = page.search()
                .by(id("table-list"))
                .by(id("the-table"))
                .by(tagName("tr"))
                .find()
                .filter(hasClass("uneven-row"))
                .transform(getDataAttribute("name"));

        assertResult(result, contains("Uneven Row 1", "Uneven Row 2", "Uneven Row 3"));
    }


    @Test
    public void shouldFindRowsWithFilterOnResultUsingMatcher()
    {
        SearchQuery.AnyResult<String> result = page.search()
                .by(id("table-list"))
                .by(id("the-table"))
                .by(tagName("tr"))
                .find()
                .filter(withClass("uneven-row"))
                .transform(getDataAttribute("name"));

        assertResult(result, contains("Uneven Row 1", "Uneven Row 2", "Uneven Row 3"));
    }

    @Test
    public void shouldFindRowsWithFilterAndBindOnResult()
    {
        SearchQuery.AnyResult<RowWrapper> result = page.search()
                .by(id("table-list"))
                .by(id("the-table"))
                .by(tagName("tr"))
                .find()
                .filter(hasClass("even-row"))
                .bindTo(RowWrapper.class);

        assertResult(result, contains(
                rowWithName("Even Row 1"),
                rowWithName("Eeven Row 2"),
                rowWithName("Eeven Row 3")));
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

    // TODO more nested tests (incl. dynamic changing list)

    @Test
    public void shouldAssignCustomTimeoutType()
    {
        SearchQuery.PageElementResult<PageElement> result = page.search()
                .by(id("table-list"))
                .by(id("the-table"))
                .by(tagName("tr"), hasClass("even-row"))
                .find()
                .withTimeout(TimeoutType.PAGE_LOAD);

        assertResultStrict(result, everyItem(hasTimeoutType(TimeoutType.PAGE_LOAD)));
    }

    @Test
    public void shouldReturnResultAsCheckboxes()
    {
        SearchQuery.PageElementResult<CheckboxElement> result = page.search()
                .by(id("checkbox-parent"))
                .by(tagName("input"))
                .find()
                .as(CheckboxElement.class)
                .filter(not(isChecked()));

        // only unchecked checkboxes should be returned
        assertResult(result, contains(
                asMatcherOf(CheckboxElement.class, withId("checkbox-2")),
                asMatcherOf(CheckboxElement.class, withId("checkbox-3"))
        ));
    }

    private static <R> void assertResult(SearchQuery.Result<R, ?> result, Matcher<Iterable<? extends R>> matcher)
    {
        assertThat(result.all(), matcher);
        assertThat(result.supplier().get(), matcher);
        waitUntil(result.timed(), matcher);
    }

    private static <R> void assertResultStrict(SearchQuery.Result<R, ?> result, Matcher<Iterable<R>> matcher)
    {
        assertThat(result.all(), matcher);
        assertThat(result.supplier().get(), matcher);
        waitUntil(result.timed(), matcher);
    }

    private static Matcher<PageElement> hasTimeoutType(TimeoutType expectedTimeout)
    {
        return new FeatureMatcher<PageElement, TimeoutType>(is(expectedTimeout), "timeout type", "timeoutType")
        {
            @Override
            protected TimeoutType featureValueOf(PageElement pageElement)
            {
                return WebDriverElement.class.cast(pageElement).getDefaultTimeout();
            }
        };
    }

    private static Matcher<RowWrapper> rowWithName(final String name)
    {
        return new FeatureMatcher<RowWrapper, String>(equalTo(name), "row name", "name")
        {
            @Override
            protected String featureValueOf(RowWrapper rowWrapper)
            {
                return rowWrapper.getName();
            }
        };
    }

    public static class RowWrapper
    {
        private final PageElement row;

        public RowWrapper(PageElement element)
        {
            this.row = element;
        }

        String getName()
        {
            return getDataAttribute("name").apply(row);
        }
    }
}
