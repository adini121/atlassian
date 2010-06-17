package com.atlassian.selenium.browsers;

import com.atlassian.selenium.AbstractSeleniumConfiguration;
import com.atlassian.selenium.browsers.firefox.DisplayAwareFirefoxChromeLauncher;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.ExecTask;
import org.openqa.selenium.server.browserlaunchers.BrowserLauncherFactory;
import org.openqa.selenium.server.browserlaunchers.UnixUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.atlassian.selenium.browsers.ProcessRunner.runProcess;

/**
 * Configures Selenium by detecting if installation of a browser is required, and if so, installs it for this session.
 *
 * @since 2.0
 */
class AutoInstallConfiguration extends AbstractSeleniumConfiguration
{

    private static final String LOCATION = System.getProperty("selenium.location", "localhost");
    private static final int PORT = Integer.getInteger("selenium.port", pickFreePort());
    private static final String BROWSER = System.getProperty("selenium.browser", "firefox-3.5");
    private static final String BASE_URL = System.getProperty("baseurl", "http://localhost:8080/");

    private static final long MAX_WAIT_TIME = 10000;
    private static final long CONDITION_CHECK_INTERVAL = 100;

    private String firefoxProfileTemplate;
    private String baseUrl;
    private String browser = BROWSER;
    private static final int BUFFER = 2048;
    public static final String CHROME_XVFB = "chromeXvfb";

    AutoInstallConfiguration(final File seleniumDir, String display)
    {
        try
        {
            if (BROWSER.startsWith("firefox"))
            {
                if (OsValidator.isUnix())
                {
                    // We use a custom browser launcher that sets the display env variable
                    if (display != null)
                    {
                        System.setProperty("DISPLAY", display);
                    }
                    BrowserLauncherFactory.addBrowserLauncher(CHROME_XVFB, DisplayAwareFirefoxChromeLauncher.class);
                    setupFirefoxBrowser(seleniumDir, "linux", "firefox-bin");
                }
                else if (OsValidator.isMac())
                {
                    setupFirefoxBrowser(seleniumDir, "osx", "Contents/MacOS/firefox-bin");
                }
                else if (OsValidator.isWindows())
                {
                    setupFirefoxBrowser(seleniumDir, "windows", "firefox.exe");
                }
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        if (browser == null)
        {
            browser = BROWSER;
        }
        if (!BASE_URL.endsWith("/"))
        {
            baseUrl = BASE_URL + "/";
        }
        else
        {
            baseUrl = BASE_URL;
        }
    }

    private void setupFirefoxBrowser(File seleniumDir, String osDirName, String binaryPath)
            throws IOException
    {
        File browserDir = extractZip(seleniumDir, "/" + osDirName + "/" + BROWSER + ".zip");
        String profileName = BROWSER + "-profile";
        File profileDir = extractZip(seleniumDir, "/" + osDirName + "/" + profileName + ".zip");
        firefoxProfileTemplate = profileDir.getAbsolutePath();
        File browserBin = new File(browserDir, binaryPath);
        make755(browserBin);
        browser = "*" + CHROME_XVFB + " " + browserBin.getAbsolutePath();
    }

    private File extractZip(File seleniumDir, String internalPath) throws IOException
    {
        InputStream internalStream = null;

        File targetDir = new File(seleniumDir, internalPath.substring(internalPath.lastIndexOf('/'), internalPath.length() - ".zip".length()));
        if (targetDir.exists())
        {
            return targetDir;
        }
        try
        {
            System.out.println("unzipping " + internalPath);
            targetDir.mkdirs();

            internalStream = getClass().getResourceAsStream(internalPath);
            if (internalStream == null)
            {
                throw new IOException("Zip file not found: " + internalPath);
            }
            BufferedOutputStream dest = null;
            ZipInputStream zis = new
                    ZipInputStream(new BufferedInputStream(internalStream));
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null)
            {
                //System.out.println("Extracting: " + entry + " to " + targetDir.getName());
                int count;
                byte data[] = new byte[BUFFER];
                // write the files to the disk
                if (entry.getName().endsWith("/"))
                {
                    new File(targetDir, entry.getName()).mkdirs();
                }
                else
                {
                    FileOutputStream fos = new
                            FileOutputStream(new File(targetDir, entry.getName()));
                    dest = new
                            BufferedOutputStream(fos, BUFFER);
                    while ((count = zis.read(data, 0, BUFFER))
                            != -1)
                    {
                        dest.write(data, 0, count);
                    }
                    dest.flush();
                    dest.close();
                }

            }
            zis.close();
        }
        finally
        {
            IOUtils.closeQuietly(internalStream);
        }
        return targetDir;
    }

    public String getServerLocation()
    {
        return LOCATION;
    }

    public int getServerPort()
    {
        return PORT;
    }

    public String getBrowserStartString()
    {
        return browser;
    }

    public String getBaseUrl()
    {
        return baseUrl;
    }

    public boolean getStartSeleniumServer()
    {
        return !isPortInUse();
    }

    private boolean isPortInUse()
    {
        try
        {
            new Socket("localhost", getServerPort());
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public long getActionWait()
    {
        return MAX_WAIT_TIME;
    }

    public long getPageLoadWait()
    {
        return MAX_WAIT_TIME;
    }

    public long getConditionCheckInterval()
    {
        return CONDITION_CHECK_INTERVAL;
    }

    @Override
    public String getFirefoxProfileTemplate()
    {
        return firefoxProfileTemplate;
    }

    /** runs "chmod 755 " on the specified File */
    public static void make755(File file) throws IOException
    {
        runProcess(new ProcessBuilder("chmod", "755", file.getCanonicalPath()));
    }

    static int pickFreePort()
    {
        ServerSocket socket = null;
        try
        {
            socket = new ServerSocket(0);
            return socket.getLocalPort();
        }
        catch (IOException e)
        {
            throw new RuntimeException("Error opening socket", e);
        }
        finally
        {
            if (socket != null)
            {
                try
                {
                    socket.close();
                }
                catch (IOException e)
                {
                    throw new RuntimeException("Error closing socket", e);
                }
            }
        }
    }



}
