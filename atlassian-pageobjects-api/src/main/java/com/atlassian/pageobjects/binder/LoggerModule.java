package com.atlassian.pageobjects.binder;

import com.google.inject.AbstractModule;
import org.slf4j.Logger;

/**
 * Adds a common SLF4J logger to the injection context.
 *
 * @since 2.1
 */
public class LoggerModule extends AbstractModule
{
    private final Logger logger;

    public LoggerModule(Logger logger)
    {
        this.logger = logger;
    }

    @Override
    protected void configure()
    {
        bind(Logger.class).toInstance(logger);
    }
}
