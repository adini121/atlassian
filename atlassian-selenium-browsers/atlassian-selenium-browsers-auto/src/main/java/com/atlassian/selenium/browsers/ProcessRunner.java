package com.atlassian.selenium.browsers;

import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

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
            System.out.println("Executing " + procBuilder.command());
            proc = procBuilder.start();
            if (outputFile != null)
            {
                procBuilder.redirectErrorStream(true);
                StreamReader reader = new StreamReader(proc.getInputStream(), new FileOutputStream(outputFile));
                reader.start();
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

    static class StreamReader extends Thread
    {
        private final InputStream in;
        private final OutputStream out;

        public StreamReader(InputStream in, OutputStream out)
        {
            this.in = in;
            this.out = out;
        }

        @Override
        public void run()
        {
            int len;
            byte[] buffer = new byte[512];
            try
            {
                while ((len = in.read(buffer)) > 0)
                {
                    out.write(buffer, 0, len);
                    out.flush();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
                // ignore
            }
            finally
            {
                IOUtils.closeQuietly(in);
                IOUtils.closeQuietly(out);
            }
        }
    }
}
