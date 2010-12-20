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
    KEYDOWN,
    KEYPRESS,
    KEYUP;

    public static final Set<KeyEventType> ALL = Collections.unmodifiableSet(EnumSet.allOf(KeyEventType.class));  
}
