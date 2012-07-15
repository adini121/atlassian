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
     * Get an instance of given <tt>type</tt> from context.
     *
     * @param type type of the requested instance
     * @param <T> type param
     * @return an instance of requested type. An exception may be raised if the context is unable to instantiate
     * given <tt>type</tt>.
     * @throws IllegalArgumentException if instantiating given class according to JSR-330 rules was impossible
     */
    <T> T getInstance(Class<T> type);

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
