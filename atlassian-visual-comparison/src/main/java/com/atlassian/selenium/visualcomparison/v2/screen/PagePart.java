package com.atlassian.selenium.visualcomparison.v2.screen;

import com.atlassian.annotations.ExperimentalApi;

/**
 * TODO
 *
 * @since 2.3
 */
@ExperimentalApi
public interface PagePart
{
    public int getLeft();

    public int getTop();

    public int getRight();

    public int getBottom();
}
