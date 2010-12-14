package com.atlassian.pageobjects;

import java.util.Map;

/**
 *
 */
public interface Tester
{
    Map<Class<?>,Object> getInjectables();

    void gotoUrl(String url);
}
