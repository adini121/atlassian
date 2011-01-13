package com.atlassian.webdriver.pageobjects.element;

import javax.swing.text.AbstractDocument;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Injects an Element into the field.
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
}