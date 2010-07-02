package com.atlassian.browsers;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.atlassian.browsers.ProcessRunner.runProcess;
import static com.atlassian.browsers.ProcessRunner.runProcessInBackground;
import static java.util.Arrays.asList;

/**
 *
 */
class XvfbManager
{
    /**
     * The 'Xvfb' command to execute.
     */
    String xvfbExecutable = System.getProperty("xvfb.executable", "Xvfb");

    /**
     * Use 'xauth' to setup permissions for the Xvfb server.  This requires 'xauth' be installed
     * and may be required when an X server is already running.
     */
    boolean xauthEnabled = Boolean.parseBoolean(System.getProperty("xvfb.xauth.enable", "false"));

    /**
     * The 'xauth' command to execute.
     */
    String xauthExecutable = System.getProperty("xvfb.xauth.executable", "xauth");

    /**
     * The 'xauth' protocol.
     */
    String xauthProtocol = System.getProperty("xvfb.xauth.protocol", ".");

    /**
     * The default display to use.  SSH usualy eats up :10, so lets use :20.  That starts at port 6020.
     */
    static final int DEFAULT_DISPLAY_NUMBER = 20;

    /**
     * The X11 display to use.  Default value is <tt>:20</tt>.
     */
    String display = System.getProperty("xvfb.display");

    /**
     * A list of additional options to pass to the Xvfb process.
     */
    String[] options = System.getProperty("xvfb.options", "-once").split(",");

    /**
     * Enable logging mode.
     */
    boolean logOutput = Boolean.parseBoolean(System.getProperty("xvfb.log.enabled", "true"));


    File baseTmpDir;
    private File authenticationFile;
    private Process xfvbProcess;

    public XvfbManager(File baseTmpDir)
    {
        this.baseTmpDir = baseTmpDir;
    }

    /**
     * @return the display port
     */
    public void start()
    {
        System.out.println("Starting Xvfb...");

        // Figure out what the display number is, and generate the properties file
        if (display == null)
        {
            display = detectUsableDisplay();
        }
        else
        {
            if (isDisplayInUse(display))
            {
                throw new RuntimeException("It appears that the configured display is already in use: " + display);
            }
        }

        System.out.println("Using display: " + display);

        List<String> cmd = new ArrayList<String>();
        cmd.add(xvfbExecutable);
        cmd.add(display);
        cmd.addAll(asList(options));

        ProcessBuilder procBuilder = new ProcessBuilder(cmd);
        authenticationFile = null;
        if (xauthEnabled)
        {
            authenticationFile = setupXauthority();
            procBuilder.environment().put("XAUTHORITY", authenticationFile.getAbsolutePath());
        }

        writeDisplayProperties();
        File logFile = null;
        if (logOutput)
        {
            logFile = new File(baseTmpDir, "xvfb.log");
            System.out.println("Redirecting output to: " + logFile.getPath());
        }
        xfvbProcess = runProcessInBackground(procBuilder, logFile);
        System.out.println("Waiting till xvfb is up...");
        while (!isDisplayInUse(display))
        {
            try
            {
                System.out.println('.');
                System.out.flush();
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                // ignore
            }
        }
        System.out.println("xvfb is up!");
    }

    public File getXAuthenticationFile()
    {
        return authenticationFile;
    }

    public String getDisplay()
    {
        return display;
    }

    public void stop()
    {
        if (authenticationFile != null)
        {
            authenticationFile.delete();
        }
        if (xfvbProcess != null)
        {
            xfvbProcess.destroy();
        }
    }

    private void writeDisplayProperties()
    {
        try
        {
            String text = "DISPLAY='" + display + "'\n";
            // Write the xauth file so clients pick up the right perms
            if (xauthEnabled)
            {
                text += "XAUTHORITY='" + authenticationFile.getCanonicalPath() + "'\n";
            }
            File propFile = new File(baseTmpDir, "xvfb.env.properties");
            FileUtils.writeStringToFile(propFile, text);
        }
        catch (IOException e)
        {
            throw new RuntimeException("Unable to save display properties", e);
        }
    }


    /**
     * Generate a 128-bit random hexadecimal number for use with the X authority system.
     */
    private String createCookie()
    {
        byte[] bytes = new byte[16];
        new Random().nextBytes(bytes);
        BigInteger cookie = new BigInteger(bytes);

        String cookieHex = cookie.abs().toString(16);
        int padding = 32 - cookieHex.length();
        for (int i = 0; i < padding; i++)
        {
            cookieHex = "0" + cookieHex;
        }
        return cookieHex;
    }

    /**
     * Setup the X authentication file (Xauthority)
     */
    private File setupXauthority()
    {
        File authenticationFile = new File(baseTmpDir, "Xvfb.Xauthority");

        System.out.println("Using Xauthority file: " + authenticationFile.getPath());

        //
        // TODO: Really need a way to check if this will execute or not first
        //

        String cookie = createCookie();

        // Use xauth to configure authentication for the display using a generated cookie
        ProcessBuilder procBuilder = new ProcessBuilder(xauthExecutable,
                "add",
                display,
                xauthProtocol,
                cookie);
        procBuilder.environment().put("XAUTHORITY", authenticationFile.getAbsolutePath());
        runProcess(procBuilder);

        if (!authenticationFile.exists())
        {
            throw new RuntimeException("It appears that 'xauth' failed to create the Xauthority file: " + authenticationFile.getPath());
        }
        return authenticationFile;
    }

    /**
     * Detect which display is usable.
     */
    private String detectUsableDisplay()
    {
        boolean found = false;
        for (int n = DEFAULT_DISPLAY_NUMBER; n < DEFAULT_DISPLAY_NUMBER + 10; n++)
        {
            String d = ":" + n;

            if (!isDisplayInUse(d))
            {
                return d;
            }
        }
        throw new RuntimeException("Count not find a usable display");
    }

    /**
     * Decode the port number for the display.
     */
    private int decodeDisplayPort(String display)
    {
        //def m = display = ~ /[^:]*:([0 - 9] *)(\.([0 - 9] *))?/
        //def i = Integer.parseInt(m[0][1])

        //
        // Normally, the first X11 display is on port 6000, the next on port 6001,
        // which get abbreviated as :0, :1 and so on.
        //

        return 6000 + Integer.parseInt(display.substring(display.indexOf(":") + 1));
    }

    /**
     * Check if the given display is in use or not.
     */
    private boolean isDisplayInUse(String display)
    {
        int port = decodeDisplayPort(display);
        try
        {
            new Socket("localhost", port);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
}