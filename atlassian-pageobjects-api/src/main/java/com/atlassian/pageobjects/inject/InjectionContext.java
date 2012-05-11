package com.atlassian.pageobjects.inject;

import com.atlassian.annotations.ExperimentalApi;

/**
 * Simple interface for framework components capable of injection of components as described by JSR-330.
 *
 * @since 2.1
 */
@ExperimentalApi
public interface InjectionContext
{

    /**
     * Execute injection of static fields on given <tt>targetClass</tt>.
     *
     * @param targetClass class to inject into
     */
    public void injectStatic(Class<?> targetClass);


    /**
     * Execute injection of fields on given <tt>targetInstance</tt>
     *
     * @param targetInstance instance to inject into
     */
    public void injectMembers(Object targetInstance);
}
