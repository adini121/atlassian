package com.atlassian.pageobjects;

/**
 * A delayed binder that gives the caller full control over the creation and lifecycle of the page object.
 */
public interface DelayedBinder<T>
{
    /**
     * Instantiates, injects, and initialises the page object, but doesn't execute its lifecycle methods
     * @return The binder for chaining
     */
    DelayedBinder<T> inject();

    /**
     * Builds the page object and executes its waitfor lifecycle methods
     *
     * @return The binder for chaining
     */
    DelayedBinder<T> waitUntil();

    /**
     * Builds, waits for, and validates the state of the page object
     * @return The binder for chaining
     */
    DelayedBinder<T> validateState();

    /**
     * Goes through the full binding, including lifecycle methods, to determine whether the page object can be bound.
     * @return True if the binding was successful
     */
    boolean canBind();

    /**
     * @return The current page object, building if necessary.  If called before any other methods are called, it will
     * return the instantiated object but with no injections or lifecycle methods called.
     */
    T get();

    /**
     * Builds, waits for, validates the state of, and returns the page object
     * @return The fully bound page object
     */
    T bind();
}
