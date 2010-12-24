package com.atlassian.webtest.ui.keys;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

/**
* Represent key events that occur in browsers upon typing. 
*
* @since v4.3
*/
public enum KeyEventType
{
    KEYDOWN("keydown"),
    KEYPRESS("keypress"),
    KEYUP("keyup");

    public static final Set<KeyEventType> ALL = Collections.unmodifiableSet(EnumSet.allOf(KeyEventType.class));

    private final String eventString;

    KeyEventType(String eventString) {
        this.eventString = eventString;
    }

    public String getEventString() {
        return eventString;
    }
}
