package com.atlassian.selenium.visualcomparison.v2.screen;

import com.atlassian.annotations.ExperimentalApi;

import javax.annotation.concurrent.Immutable;

/**
 * TODO
 *
 * @since 2.3
 */
@Immutable
@ExperimentalApi
public final class SimplePagePart implements PagePart
{
    private final int left;
    private final int top;
    private final int right;
    private final int bottom;

    public SimplePagePart(int left, int top, int right, int bottom)
    {
        // TODO check arguments
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public int getLeft()
    {
        return left;
    }

    public int getTop()
    {
        return top;
    }

    public int getRight()
    {
        return right;
    }

    public int getBottom()
    {
        return bottom;
    }
}
