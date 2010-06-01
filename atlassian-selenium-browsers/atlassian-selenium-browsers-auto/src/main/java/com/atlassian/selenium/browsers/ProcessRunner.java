package com.atlassian.selenium.browsers;

import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 */
public class ProcessRunner
{
    public static void runProcess(ProcessBuilder procBuilder)
    {
        runProcess(procBuilder, null, false);
    }

    public static Process runProcessInBackground(ProcessBuilder procBuilder, File outputFile)
    {
        return runProcess(procBuilder, outputFile, true);
    }


    public static Process runProcess(ProcessBuilder procBuilder, File outputFile, boolean background)
    {
        Process proc = null;
        try
        {
            proc = procBuilder.start();
            if (outputFile != null)
            {
                IOUtils.copy(proc.getInputStream(), new FileOutputStream(outputFile));
            }
            if (!background)
            {
                proc.waitFor();
                if (proc.exitValue() != 0)
                {
                    throw new RuntimeException("Unable to execute " + procBuilder.toString() + " as returned error code " + proc.exitValue());
                }
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException("Unable to start process", e);
        }
        catch (InterruptedException e)
        {
            // swallow
        }
        return proc;
    }
}
