package com.atlassian.pageobjects;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The default values for a {@link com.atlassian.pageobjects.TestedProduct}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Defaults {

    /**
     * @return The default instance id
     */
    String instanceId();

    /**
     * @return The default context path
     */
    String contextPath();

    /**
     * @return The default http port
     */
    int httpPort();
}
