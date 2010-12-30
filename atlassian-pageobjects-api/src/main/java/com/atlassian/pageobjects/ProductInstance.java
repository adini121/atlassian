package com.atlassian.pageobjects;

/**
 *
 */
public interface ProductInstance
{
    String getBaseUrl();

    int getHttpPort();

    String getContextPath();

    String getInstanceId();
}
