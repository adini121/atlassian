package com.atlassian.webdriver.matchers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * More matchers for core Java thingies.
 *
 * @since 2.1
 */
public final class LangMatchers
{

    private LangMatchers()
    {
        throw new AssertionError("Don't instantiate me");
    }

    public static Matcher<String> containsInOrder(final CharSequence... substrings)
    {
        return new TypeSafeMatcher<String>()
        {
            @Override
            protected boolean matchesSafely(String item)
            {
                int index = -1;
                for (CharSequence substring : substrings)
                {
                    index = item.indexOf(substring.toString(), index);
                    if (index < 0)
                    {
                        return false;
                    }
                }
                return true;
            }

            @Override
            public void describeTo(Description description)
            {
                description.appendText("a string that contains (in order): ").appendValueList("(", ",", ")", substrings);
            }
        };
    }
}
