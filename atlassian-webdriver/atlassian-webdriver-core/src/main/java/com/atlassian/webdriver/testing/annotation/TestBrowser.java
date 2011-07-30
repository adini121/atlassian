package com.atlassian.webdriver.testing.annotation;

import com.atlassian.webdriver.testing.rule.TestBrowserRule;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This allows tests methods, classes or packages to be annotated with
 * the browser string that will be set if using the  {@link TestBrowserRule}
 * so that when the test runs it runs with a specific browser.
 *
 * eg. @TestBrowser("firefox-3.5")
 * @see {@link TestBrowserRule}
 */
@Retention (RetentionPolicy.RUNTIME)
@Target ({ElementType.TYPE, ElementType.PACKAGE, ElementType.METHOD})
public @interface TestBrowser
{
    String value();
}
