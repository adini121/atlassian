package com.atlassian.pageobjects.framework;

import com.atlassian.pageobjects.binder.PostInjectionProcessor;
import com.google.inject.Binder;
import com.google.inject.Module;

/**
 *
 */
public class ElementModule implements Module
{
    public void configure(Binder binder)
    {
        binder.bind(PostInjectionProcessor.class).to(ElementByPostInjectionProcessor.class);
    }
}
