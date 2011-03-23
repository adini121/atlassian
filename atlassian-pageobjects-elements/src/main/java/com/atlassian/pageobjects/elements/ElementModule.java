package com.atlassian.pageobjects.elements;

import com.google.inject.Binder;
import com.google.inject.Module;

/**
 * Guice module that adds bindings for classes required by the elements
 */
public class ElementModule implements Module
{
    public void configure(Binder binder)
    {
        binder.bind(ElementByPostInjectionProcessor.class);
    }
}
