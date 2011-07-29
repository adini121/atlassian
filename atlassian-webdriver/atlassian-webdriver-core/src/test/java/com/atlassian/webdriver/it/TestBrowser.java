package com.atlassian.webdriver.it;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This allows tests classes to be annotated with the a browser string
 * that will be set by the test runner so that when the test runs it runs with a
 * specific browser.
 *
 * eg. @TestBrowser("firefox-3.5")
 */
@Retention (RetentionPolicy.RUNTIME)
@Target ({ElementType.TYPE, ElementType.PACKAGE, ElementType.METHOD})
public @interface TestBrowser
{
    String value();
}
