package com.atlassian.webdriver.testing.annotation;

import com.atlassian.webdriver.utils.Browser;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Similar to the JUnit {@link org.junit.Ignore} but allows tests
 * to be ignored based on specific browsers.
 * Annotate a test method with this type if using the
 * {@link com.atlassian.webdriver.it.WebDriverBrowserRunner} the test method will be
 * skipped.
 */
@Retention (RetentionPolicy.RUNTIME)
@Target (ElementType.METHOD)
public @interface IgnoreBrowser
{
    Browser[] value() default {Browser.ALL};
    String reason() default ("No reason given");
}
