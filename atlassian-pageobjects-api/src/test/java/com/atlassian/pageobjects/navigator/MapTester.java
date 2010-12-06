package com.atlassian.pageobjects.navigator;

import com.atlassian.pageobjects.Tester;

import java.util.HashMap;
import java.util.Map;

/**
*
*/
class MapTester implements Tester
{
    private final Map<Class<?>,Object> injectables = new HashMap<Class<?>, Object>();

    public Map<Class<?>, Object> getInjectables()
    {
        return injectables;
    }

    public MapTester add(Class<?> key, Object value)
    {
        injectables.put(key, value);
        return this;
    }
}
