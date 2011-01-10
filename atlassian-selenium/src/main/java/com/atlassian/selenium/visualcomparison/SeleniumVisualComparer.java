package com.atlassian.selenium.visualcomparison;

import com.atlassian.selenium.SeleniumClient;
import com.atlassian.selenium.visualcomparison.utils.ScreenResolution;
import com.atlassian.selenium.visualcomparison.utils.Screenshot;
import com.atlassian.selenium.visualcomparison.utils.ScreenshotDiff;
import junit.framework.Assert;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class SeleniumVisualComparer
{
    private ScreenResolution[] resolutions = new ScreenResolution[]
            {
                    new ScreenResolution(1280, 1024)
            };
    private SeleniumClient client;
    private boolean refreshAfterResize = false;
    private boolean reportingEnabled = false;
    private String reportOutputPath;
    private String imageSubDirName = "report_images";
    private String tempPath = System.getProperty("java.io.tmpdir");
    private Map<String, String> uiStringReplacements = null;

    public SeleniumVisualComparer(SeleniumClient client)
    {
        this.client = client;
    }

    public void setScreenResolutions(ScreenResolution[] resolutions)
    {
        this.resolutions = resolutions;
    }

    public ScreenResolution[] getScreenResolutions()
    {
        return this.resolutions;
    }

    public void setRefreshAfterResize(boolean refreshAfterResize)
    {
        this.refreshAfterResize = refreshAfterResize;
    }

    public boolean getRefreshAfterResize()
    {
        return this.refreshAfterResize;
    }

    public void setUIStringReplacements(Map<String,String> uiStringReplacements)
    {
        this.uiStringReplacements = uiStringReplacements;
    }

    public Map<String,String> getUIStringReplacements()
    {
        return this.uiStringReplacements;
    }

    public void enableReportGeneration(String reportOutputPath)
    {
        this.reportingEnabled = true;
        this.reportOutputPath = reportOutputPath;
        
        File file = new File(reportOutputPath + "/" + imageSubDirName);
        file.mkdirs();
    }

    public void disableReportGeneration()
    {
        this.reportingEnabled = false;
    }

    public void setTempPath(String tempPath)
    {
        File file = new File(tempPath);
        file.mkdirs();
        this.tempPath = tempPath;
    }

    public String getTempPath()
    {
        return this.tempPath;
    }

    public void assertUIMatches(String id, String baselineImagePath)
    {
        try
        {
            Assert.assertTrue("Screenshots were not equal", uiMatches(id, baselineImagePath));
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public boolean uiMatches(final String id, final String baselineImagePath) throws Exception
    {
        ArrayList<Screenshot> currentScreenshots = takeScreenshots(id);
        ArrayList<Screenshot> baselineScreenshots = loadBaselineScreenshots(id, baselineImagePath);
        return compareScreenshots(baselineScreenshots, currentScreenshots);
    }

    public ArrayList<Screenshot> takeScreenshots(final String id) throws IOException
    {
        // Capture a series of screenshots in all the valid screen resolutions.
        ArrayList<Screenshot> screenshots = new ArrayList<Screenshot>();
        for (ScreenResolution res : resolutions)
        {
            res.resize(client, refreshAfterResize);
            if (uiStringReplacements != null)
            {
                // Remove strings from the UI that we are expecting will change
                // (such as the build number in the JIRA footer)
                for (String key : uiStringReplacements.keySet())
                {
                    replaceUIHtml(key, uiStringReplacements.get(key));
                }
            }
            screenshots.add(new Screenshot(client, id, tempPath, res));
        }
        Collections.sort(screenshots);
        return screenshots;
    }

    public ArrayList<Screenshot> loadBaselineScreenshots(final String id, final String baselineImagePath) throws IOException
    {
        File screenshotDir = new File(baselineImagePath);
        File[] screenshotFiles = screenshotDir.listFiles(
                new FileFilter()
                {
                    public boolean accept(File file) { return file.getName().startsWith(id);}
                });

        ArrayList<Screenshot> screenshots = new ArrayList<Screenshot>();
        for (File screenshotFile : screenshotFiles)
        {
            screenshots.add(new Screenshot(screenshotFile));
        }
        Collections.sort(screenshots);
        return screenshots;
    }

    protected void replaceUIHtml(String id, String newContent)
    {
        client.getEval("window.document.getElementById('" + id + "').innerHTML = \"" + newContent + "\"");
    }

    public boolean compareScreenshots(ArrayList<Screenshot> oldScreenshots, ArrayList<Screenshot> newScreenshots)
            throws Exception
    {
        if (oldScreenshots.size() != newScreenshots.size())
        {
            throw new IllegalArgumentException("Did not find correct number of baseline images");
        }

        boolean matches = true;
        for (int i = 0; i < oldScreenshots.size(); i++)
        {
            ScreenshotDiff diff = getScreenshotDiff(oldScreenshots.get(i), newScreenshots.get(i));
            if (reportingEnabled)
            {
                diff.writeDiffReport(reportOutputPath, imageSubDirName);
            }
            matches = !diff.hasDifferences() && matches;
        }

        return matches;
    }

    public ScreenshotDiff getScreenshotDiff(Screenshot oldScreenshot, Screenshot newScreenshot) throws Exception
    {
        return oldScreenshot.getDiff(newScreenshot);
    }

}
