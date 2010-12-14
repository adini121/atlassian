package com.atlassian.pageobjects;

import java.net.URL;
import java.util.Map;

/**
 *
 */
public interface Tester
{
    Map<Class<?>,Object> getInjectables();

    void gotoUrl(String url);
}
