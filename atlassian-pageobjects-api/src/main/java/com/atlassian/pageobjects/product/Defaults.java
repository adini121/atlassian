package com.atlassian.pageobjects.product;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * The default values for a {@link TestedProduct}
 */
@Retention(RetentionPolicy.RUNTIME)
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
