package com.atlassian.pageobjects.browser;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks elements that should only be called if the current test browser matches any of the given browsers.
 *
 * @since 2.1
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE, ElementType.PACKAGE })
public @interface RequireBrowser
{
    /**
     * @return list of browsers that the current browser should match
     */
    Browser[] value() default { Browser.ALL };

    /**
     * Since 2.2.1
     *
     * @return reason for requiring the browser(s)
     */
    String reason() default ("No reason given");
}
