package com.atlassian.webdriver.testing.annotation;

import com.atlassian.pageobjects.Browser;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Similar to the JUnit {@link org.junit.Ignore} but allows tests
 * to be ignored based on specific browsers.
 * Annotate a test method or class with this type and, when using
 * {@link com.atlassian.webdriver.testing.rule.IgnoreBrowserRule}, the test method will be skipped.
 *
 * @since 2.1.0
 */
@Retention (RetentionPolicy.RUNTIME)
@Target ({ElementType.METHOD, ElementType.TYPE})
@Inherited
public @interface IgnoreBrowser
{
    Browser[] value() default {Browser.ALL};
    String reason() default ("No reason given");
}