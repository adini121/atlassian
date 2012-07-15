package com.atlassian.pageobjects.binder;

import com.atlassian.pageobjects.Browser;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a method that should only be called if the current test browser matches any of the given browsers.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequireBrowser
{
    Browser[] value() default {Browser.ALL};
}