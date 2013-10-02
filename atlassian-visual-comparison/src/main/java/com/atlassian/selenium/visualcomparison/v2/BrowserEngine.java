package com.atlassian.selenium.visualcomparison.v2;

import com.atlassian.selenium.visualcomparison.ScreenElement;
import com.atlassian.selenium.visualcomparison.v2.screen.Resolution;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;

/**
 * TODO
 *
 * @since 2.3
 */
public interface BrowserEngine
{
    @Nonnull
    ScreenElement getElementAt(int x, int y);

    @Nonnull
    BrowserEngine resizeTo(@Nonnull Resolution resolution);

    @Nonnull
    BrowserEngine captureScreenshotTo(@Nonnull File path);

    @Nonnull
    BrowserEngine reloadPage();

    @Nullable
    <T> T executeScript(@Nonnull Class<T> returnType, @Nonnull String script, @Nonnull Object... args);
}
