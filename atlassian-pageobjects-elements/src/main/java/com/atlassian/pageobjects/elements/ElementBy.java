package com.atlassian.pageobjects.elements;

import com.atlassian.pageobjects.elements.timeout.TimeoutType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Injects an Element into the field. Only one of the selector types should be specified.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ElementBy
{
    String id() default "";

    String className() default "";

    String linkText() default "";

    String partialLinkText() default "";

    String cssSelector() default "";

    String name() default "";

    String xpath() default "";

    String tagName() default "";

    TimeoutType timeoutType() default TimeoutType.DEFAULT;

    /**
     * Type of the PageElement to inject. Used with Iterator<? extends PageElement>
     * Defaults to the class of the annotated field.
     * <p/>
     * This is not a selector, so you still need to specify a
     * selector.
     * <p/>
     * This attribute doesn't make sense for injecting PageElements fields,
     * but it would still work. It makes sense for erased types:
     * {@code @ElementBy(name="checkbox1", pageElementClass=CheckboxElement.class) 
     * private Iterator<CheckboxElement> checkbox1;
     * }
     * @since 2.1
     */
    Class<? extends PageElement> pageElementClass() default PageElement.class;
}
