package com.atlassian.selenium.visualcomparison;

import com.atlassian.selenium.visualcomparison.utils.ScreenResolution;

public interface VisualComparableClient
{

    public void captureEntirePageScreenshot (String filePath);

    public void evaluate (String command);

    public boolean resizeScreen (ScreenResolution resolution, boolean refreshAfterResize);

    public void refreshAndWait ();

    public boolean waitForJQuery (long waitTimeMillis);
}
