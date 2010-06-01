package com.atlassian.selenium.browsers;

import com.atlassian.selenium.AbstractSeleniumConfiguration;
import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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
    private static final AutoInstallConfiguration INSTANCE = new AutoInstallConfiguration();

    private String firefoxProfileTemplate;
    private String baseUrl;
    private String browser = BROWSER;
    private static final int BUFFER = 2048;

    private AutoInstallConfiguration()
    {
        File seleniumDir = createSeleniumTmpDir();
        try
        {
            if (BROWSER.startsWith("firefox"))
            {
                if (OsValidator.isUnix())
                {
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
        browserBin.setExecutable(true);
        browser = "*chrome " + browserBin.getAbsolutePath();
    }

    private File createSeleniumTmpDir()
    {
        File targetDir = new File("target");
        File seleniumDir = new File(targetDir, "seleniumTmp");
        seleniumDir.mkdirs();
        return seleniumDir;
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

    static AutoInstallConfiguration getInstance()
    {
        return INSTANCE;
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
        return true;
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
