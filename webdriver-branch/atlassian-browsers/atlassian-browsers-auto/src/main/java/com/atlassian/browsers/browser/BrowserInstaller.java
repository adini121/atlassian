package com.atlassian.browsers.browser;

import com.atlassian.browsers.InstallConfigurator;
import com.atlassian.browsers.OS;
import com.atlassian.browsers.Utils;

import java.io.File;
import java.io.IOException;

/**
 * TODO: Document this file here
 */
public enum BrowserInstaller
{
    FIREFOX_WINDOWS_3_5(BrowserVersion.FIREFOX_3_5, OS.WINDOWS, "firefox.exe"),
    FIREFOX_LINUX_3_5(BrowserVersion.FIREFOX_3_5, OS.LINUX, "firefox-bin"),
    FIREFOX_OSX_3_5(BrowserVersion.FIREFOX_3_5, OS.OSX, "Contents/MacOS/firefox-bin"),
    FIREFOX_OSX_3_6(BrowserVersion.FIREFOX_3_6, OS.OSX, "Contents/MacOS/firefox-bin"),
//    CHROME_WINDOWS_5(BrowserVersion.CHROME_5, "windows", "chrome.exe"),
//    CHROME_LINUX_5("linux", "chrome-bin"),
    CHROME_MAC_5(BrowserVersion.CHROME_5, OS.OSX, "Contents/MacOS/chrome-bin");

    private final BrowserVersion browser;
    private final OS os;
    private final String binaryPath;

    BrowserInstaller(BrowserVersion browser, OS os, String binaryPath)
    {
        this.browser = browser;
        this.os = os;
        this.binaryPath = binaryPath;
    }

    public BrowserVersion getBrowser()
    {
        return browser;
    }

    public OS getOS()
    {
        return os;
    }

    public String getOsDirName()
    {
        return os.getName();
    }

    public String getBinaryPath()
    {
        return binaryPath;
    }

    public static BrowserInstaller typeOf(String browserStr)
    {
        OS os = OS.getType();
        BrowserVersion browserVer = BrowserVersion.typeOf(browserStr);

        for(BrowserInstaller browserInstaller : BrowserInstaller.values())
        {
            if (browserInstaller.browser.equals(browserVer) && browserInstaller.os.equals(os))
            {
                return browserInstaller;
            }
        }

        return null;
    }

    public void install(File tmpDir, InstallConfigurator installConfigurator)
    {
        String browserName = browser.getBrowserName();
        String binaryPath = getBinaryPath();
        String osDirName = getOsDirName();
        String profileName = browserName + "-profile";

        String browserResource = "/" + osDirName + "/" + browserName + ".zip";
        String profileResource = "/" + osDirName + "/" + profileName + ".zip";

        File browserProfile = null;

        try {
            File browserDir = Utils.extractZip(tmpDir, browserResource);

            if (Utils.resourceExists(profileResource))
            {
                browserProfile = Utils.extractZip(tmpDir, profileResource);
            }

            File browserBinary = new File(browserDir, binaryPath);

            if (this.equals(CHROME_MAC_5))
            {
                Utils.make755(new File(browserDir, "Contents/Versions/5.0.375.70/Google Chrome Helper.app/Contents/MacOS/Google Chrome Helper"));
            }

            Utils.make755(browserBinary);

            BrowserConfig browserConfig = new BrowserConfig(browserBinary, browserProfile);


            switch (browser)
            {
                case FIREFOX_3_5:
                case FIREFOX_3_6:
                    installConfigurator.setupFirefoxBrowser(browserConfig);
                    break;
                case CHROME_5:
                    installConfigurator.setupChromeBrowser(browserConfig);
                    break;

                default:
                    throw new RuntimeException("BrowserInstaller does not handle browser: " + browserName);
            }


        } catch(IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}

