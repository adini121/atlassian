package com.atlassian.pageobjects.framework;

import com.google.inject.Binder;
import com.google.inject.Module;

/**
 * Guice module that adds bindings for classes required by the framework
 */
public class ElementModule implements Module
{
    public void configure(Binder binder)
    {
        binder.bind(ElementByPostInjectionProcessor.class);
    }
}
