package com.atlassian.selenium.visualcomparison;

import com.atlassian.selenium.visualcomparison.utils.ScreenResolution;

public interface VisualComparableClient
{

    public void captureEntirePageScreenshot (String filePath);

    /**
     * Execute a script in the client.
     * @param command a string of javascript to send to the client.
     * @deprecated You should use {@link VisualComparableClient#execute(String, Object...)} instead.
     */
    @Deprecated
    public void evaluate (String command);

    /**
     * Execute a script in the client and return the evaluated result.
     * @param command a string of javascript to send to the client.
     * @param arguments additional arguments to provide to the script.
     * @return the evaluated result of the javascript.
     */
    public Object execute (String command, Object... arguments);

    public boolean resizeScreen (ScreenResolution resolution, boolean refreshAfterResize);

    public void refreshAndWait ();

    public boolean waitForJQuery (long waitTimeMillis);
}
