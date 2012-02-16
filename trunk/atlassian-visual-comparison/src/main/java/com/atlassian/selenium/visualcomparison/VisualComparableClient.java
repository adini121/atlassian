package com.atlassian.selenium.visualcomparison;

public interface VisualComparableClient {

    public void captureEntirePageScreenshot (String filePath);

    public void evaluate (String command);

    public void refreshAndWait ();

    public boolean waitForJQuery (long waitTimeMillis);
}
