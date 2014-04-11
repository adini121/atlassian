package com.atlassian.pageobjects.elements;

import com.atlassian.annotations.PublicApi;
import com.atlassian.pageobjects.PageBinder;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.ObjectArrays;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Predicates and functions for easier usage of {@link PageElement}s
 *
 * @since 2.3
 */
@PublicApi
public final class PageElements
{
    public static final String BODY = "body";
    public static final String TR = "tr";
    public static final String TD = "td";

    public static final String DATA_PREFIX = "data-";

    private PageElements()
    {
        throw new AssertionError("Do not instantiate the PageElements class");
    }

    @Nonnull
    public static Function<PageElement, String> getText()
    {
        return new Function<PageElement, String>()
        {
            @Override
            public String apply(PageElement input)
            {
                return StringUtils.stripToNull(input.getText());
            }
        };
    }

    @Nonnull
    public static Predicate<PageElement> isVisible()
    {
        return new Predicate<PageElement>()
        {
            @Override
            public boolean apply(PageElement input)
            {
                return input.isVisible();
            }
        };
    }

    @Nonnull
    public static Predicate<PageElement> hasClass(@Nonnull final String className)
    {
        checkNotNull(className, "className");

        return new Predicate<PageElement>()
        {
            @Override
            public boolean apply(PageElement input)
            {
                return input.hasClass(className);
            }
        };
    }

    @Nonnull
    public static Predicate<PageElement> hasDataAttribute(@Nonnull final String attribute)
    {
        checkNotNull(attribute, "attribute");

        return new Predicate<PageElement>()
        {
            @Override
            public boolean apply(PageElement input)
            {
                return input.getAttribute("data-" + attribute) != null;
            }
        };
    }

    @Nonnull
    public static Predicate<PageElement> hasDataAttribute(@Nonnull final String attribute, @Nonnull final String value)
    {
        checkNotNull(attribute, "attribute");
        checkNotNull(value, "value");

        return new Predicate<PageElement>()
        {
            @Override
            public boolean apply(PageElement input)
            {
                return input.hasAttribute("data-" + attribute, value);
            }
        };
    }

    @Nonnull
    public static Predicate<PageElement> hasValue(@Nonnull final String value)
    {
        checkNotNull(value, "value");

        return new Predicate<PageElement>()
        {
            @Override
            public boolean apply(PageElement input)
            {
                return value.equals(input.getValue());
            }
        };
    }

    @Nonnull
    public static Function<PageElement, String> getAttribute(@Nonnull final String attributeName)
    {
        checkNotNull(attributeName, "attributeName");

        return new Function<PageElement, String>()
        {
            @Override
            public String apply(PageElement input)
            {
                return input.getAttribute(attributeName);
            }
        };
    }

    /**
     * Binds 'simple' page objects that take one constructor parameter. Most useful for simple page objects wrapping
     * {@link PageElement}s, e.g. table rows.
     *
     * <p/>
     * Note: the wrapping type needs to have a constructor that accepts a single instance of the input type and all
     * the extra parameters as provided by {@code extraArgs}.
     *
     * @param binder page binder
     * @param pageObjectClass target page object class
     * @param extraArgs extra arguments to use when binding
     * @param <E> input object type
     * @param <EE> wrapping object type
     *
     * @return page binding function
     *
     * @see PageBinder#bind(Class, Object...)
     */
    @Nonnull
    public static <E, EE> Function<E, EE> bindTo(@Nonnull final PageBinder binder,
                                                 @Nonnull final Class<EE> pageObjectClass,
                                                 @Nonnull final Object... extraArgs)
    {
        checkNotNull(binder, "binder");
        checkNotNull(pageObjectClass, "pageObjectClass");
        checkNotNull(extraArgs, "extraArgs");

        return new Function<E, EE>()
        {
            @Override
            public EE apply(E input)
            {
                return binder.bind(pageObjectClass, ObjectArrays.concat(input, extraArgs));
            }
        };
    }

    /**
     * Transforms a list of objects into a list of page objects wrapping those objects. Most commonly the input list
     * will contain page elements of some sort.
     *
     * @param binder page binder
     * @param pageElements a list of page elements to transform
     * @param pageObjectClass target page object class
     * @param <E> input object type
     * @param <EE> wrapping object type
     *
     * @return a list of page objects wrapping the inputs
     *
     * @see #bindTo(PageBinder, Class, Object...)
     */
    @Nonnull
    public static <E, EE> Iterable<EE> bind(@Nonnull PageBinder binder,
                                                 @Nonnull Iterable<PageElement> pageElements,
                                                 @Nonnull Class<EE> pageObjectClass,
                                                 @Nonnull final Object... extraArgs)
    {
        return Iterables.transform(pageElements, bindTo(binder, pageObjectClass, extraArgs));
    }
}
