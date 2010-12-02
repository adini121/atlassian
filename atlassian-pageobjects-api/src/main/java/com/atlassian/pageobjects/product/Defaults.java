package com.atlassian.pageobjects.product;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Defaults {
    String instanceId();
    String contextPath();
    int httpPort();
}
