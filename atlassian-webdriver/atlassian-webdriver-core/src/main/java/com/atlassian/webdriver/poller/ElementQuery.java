package com.atlassian.webdriver.poller;

import com.atlassian.annotations.ExperimentalApi;

/**
 * WARNING: This API is still experimental and may be changed between versions.
 *
 * @since 2.1.0
 */
@ExperimentalApi
public interface ElementQuery
{
    ExecutablePollerQuery isVisible();
    ExecutablePollerQuery isNotVisible();

    ExecutablePollerQuery exists();
    ExecutablePollerQuery doesNotExist();

    StringValueQuery getAttribute(String attributeName);

    ExecutablePollerQuery isSelected();
    ExecutablePollerQuery isNotSelected();

    ExecutablePollerQuery isEnabled();
    ExecutablePollerQuery isNotEnabled();

    ExecutablePollerQuery hasClass(String className);
    ExecutablePollerQuery doesNotHaveClass(String className);

    StringValueQuery getText();
}
