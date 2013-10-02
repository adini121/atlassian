package com.atlassian.selenium.visualcomparison.v2;

import javax.annotation.Nonnull;

/**
 * TODO
 *
 * @since 2.3
 */
public interface Comparer
{

    /**
     * TODO
     *
     * @param id
     */
    void compare(@Nonnull String id);

    /**
     *
     * @param id
     * @param settings
     */
    void compare(@Nonnull String id, @Nonnull ComparisonSettings settings);
}
