package com.atlassian.performance;

import com.google.inject.AbstractModule;
import static com.google.inject.matcher.Matchers.*;
import com.google.inject.Provider;
import java.lang.reflect.Constructor;
import org.apache.commons.lang.ClassUtils;

import static java.util.Arrays.asList;

public class TimeMethodModule extends AbstractModule
{
    @Override
    protected void configure()
    {
	bindInterceptor(any(), any(), new TimeMethodInterceptor());
    }
}
