package com.atlassian.pageobjects.elements;

import com.atlassian.pageobjects.elements.*;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * Maps element interfaces to web driver implementations.
 *
 * @since v4.4
 */
public class WebDriverElementMappings {

    private static final Map<Class<? extends Element>, Class<? extends Element>> MAPPINGS = ImmutableMap.<Class<? extends Element>, Class<? extends Element>>builder()
            .put(SelectElement.class, WebDriverSelectElement.class)
            .put(MultiSelectElement.class, WebDriverMultiSelectElement.class)
            .build();


    @SuppressWarnings({"unchecked"})
    public static <T extends Element> Class<T> findMapping(Class<T> input)
    {
        if (!input.isInterface())
        {
            // concrete class - just return it
            return input;
        }
        Class<T> answer = (Class<T>) MAPPINGS.get(input);
        if (answer == null)
        {
            answer = (Class<T>) WebDriverElement.class;
        }
        return answer;
    }
}
