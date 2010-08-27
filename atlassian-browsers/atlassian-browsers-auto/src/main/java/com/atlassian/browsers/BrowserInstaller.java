package com.atlassian.browsers;

import java.io.File;
import java.io.IOException;

/**
 * Defaines the browser version, OS and the executable path for each browser version.
 */
enum BrowserInstaller
{
    FIREFOX_WINDOWS_3_5(BrowserVersion.FIREFOX_3_5, OS.WINDOWS, "firefox.exe"),
    FIREFOX_LINUX_3_5(BrowserVersion.FIREFOX_3_5, OS.LINUX, "firefox-bin"),
    FIREFOX_OSX_3_5(BrowserVersion.FIREFOX_3_5, OS.OSX, "Contents/MacOS/firefox-bin"),
    FIREFOX_OSX_3_6(BrowserVersion.FIREFOX_3_6, OS.OSX, "Contents/MacOS/firefox-bin"),
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

    /**
     * Determins the BrowserInstaller based on the browserStr passed and the current OS.
     * @param browserStr the browserStr that defines the BrowserInstaller that is needed.
     * @return The browser installer if it exists otherwise null.
     */
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

    /**
     * Installs the current browser into the destination directory specified by
     * extracting the browser zip file.
     * If there is a profile zip for the browser it will also extract this.
     * Then the correct permissions are applied to the required files.
     * @param destDir The location to extract the browser into. This is the parent directory for the browser.
     * @param installConfigurator 
     */
    public void install(File destDir, DefaultBrowserInstallConfigurator installConfigurator)
    {
        String browserName = browser.getBrowserName();
        String binaryPath = getBinaryPath();
        String osDirName = getOsDirName();
        String profileName = browserName + "-profile";

        String browserResource = "/" + osDirName + "/" + browserName + ".zip";
        String profileResource = "/" + osDirName + "/" + profileName + ".zip";

        File browserProfile = null;

        try {
            File browserDir = Utils.extractZip(destDir, browserResource);

            if (Utils.resourceExists(profileResource))
            {
                browserProfile = Utils.extractZip(destDir, profileResource);
            }

            File browserBinary = new File(browserDir, binaryPath);

            Utils.make755(browserBinary);

            BrowserConfig browserConfig = new BrowserConfig(browserDir, browserBinary, browserProfile);

            installConfigurator.setupBrowser(browser, browserConfig);

        } catch(IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}

