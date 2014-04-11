package com.atlassian.pageobjects.elements.testing;

import com.atlassian.annotations.PublicApi;
import com.atlassian.pageobjects.elements.PageElement;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.atlassian.pageobjects.elements.PageElements.DATA_PREFIX;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;
import static org.hamcrest.Matchers.is;

/**
 * Common matchers for {@link PageElement}s.
 *
 * @since 2.3
 */
@PublicApi
public final class PageElementMatchers
{
    private PageElementMatchers()
    {
        throw new AssertionError("Do not instantiate " + PageElementMatchers.class.getName());
    }

    @Nonnull
    public static Matcher<? super PageElement> withAttribute(@Nonnull final String name, @Nullable String expectedValue)
    {
        return withAttributeThat(name, is(expectedValue));
    }

    @Nonnull
    public static Matcher<? super PageElement> withAttributeThat(@Nonnull final String name,
                                                                 @Nonnull Matcher<String> valueMatcher)
    {
        checkNotNull(name, "name");
        checkNotNull(valueMatcher, "valueMatcher");
        String featureDescription = format("attribute %s", name);

        return new FeatureMatcher<PageElement, String>(valueMatcher, featureDescription, featureDescription)
        {
            @Override
            protected String featureValueOf(PageElement actual)
            {
                return actual.getAttribute(name);
            }
        };
    }

    @Nonnull
    public static Matcher<? super PageElement> withDataAttribute(@Nonnull final String name, @Nullable String expectedValue)
    {
        return withDataAttributeThat(name, is(expectedValue));
    }


    @Nonnull
    public static Matcher<? super PageElement> withDataAttributeThat(@Nonnull final String name,
                                                                     @Nonnull Matcher<String> valueMatcher)
    {
        checkNotNull(name, "name");

        return withAttributeThat(DATA_PREFIX + name, valueMatcher);
    }
}
