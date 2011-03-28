package com.atlassian.pageobjects.elements.timeout;


import java.util.HashMap;

/**
 * {@link Timeouts} implementation that reads values from a default properties file or loads default timeouts if file
 * is not found.
 */
public class DefaultTimeouts implements Timeouts
{
    private static final String PAGE_OBJECTS_TIMEOUTS_PROPERTIES = "com/atlassian/timeout/pageobjects-timeouts.properties";

    private final Timeouts timeouts;

    public DefaultTimeouts()
    {
         if(this.getClass().getClassLoader().getResource(PAGE_OBJECTS_TIMEOUTS_PROPERTIES) != null)
         {
             timeouts = PropertiesBasedTimeouts.fromClassPath(PAGE_OBJECTS_TIMEOUTS_PROPERTIES);
         }
        else
         {
             HashMap<TimeoutType, Long> defaultTimeouts = new HashMap<TimeoutType, Long>();
             defaultTimeouts.put(TimeoutType.DEFAULT, 5000L);
             defaultTimeouts.put(TimeoutType.EVALUATION_INTERVAL, 100L);
             defaultTimeouts.put(TimeoutType.UI_ACTION, 1000L);
             defaultTimeouts.put(TimeoutType.PAGE_LOAD, 20000L);
             defaultTimeouts.put(TimeoutType.SLOW_PAGE_LOAD, 50000L);
             defaultTimeouts.put(TimeoutType.DIALOG_LOAD, 5000L);
             defaultTimeouts.put(TimeoutType.COMPONENT_LOAD, 3000L);
             defaultTimeouts.put(TimeoutType.AJAX_ACTION, 5000L);
             timeouts = new MapBasedTimeouts(defaultTimeouts);
         }
    }

    public long timeoutFor(TimeoutType timeoutType)
    {
        return timeouts.timeoutFor(timeoutType);
    }
}