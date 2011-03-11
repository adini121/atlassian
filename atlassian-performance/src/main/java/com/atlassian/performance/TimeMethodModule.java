package com.atlassian.performance;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;

public class TimeMethodModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        bindInterceptor(Matchers.any(), Matchers.annotatedWith(TimeMethod.class),
                        new TimeMethodInterceptor());
    }
}
