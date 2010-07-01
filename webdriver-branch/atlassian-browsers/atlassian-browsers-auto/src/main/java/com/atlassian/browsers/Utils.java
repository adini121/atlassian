package com.atlassian.browsers;

import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.atlassian.browsers.ProcessRunner.runProcess;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class Utils
{

    private static final int BUFFER = 2048;

    private Utils(){}

    /** runs "chmod 755 " on the specified File */
    public static void make755(File file) throws IOException
    {
        runProcess(new ProcessBuilder("chmod", "755", file.getCanonicalPath()));
    }

    public static boolean resourceExists(String path)
    {
        InputStream internalStream = Utils.class.getResourceAsStream(path);
        if (internalStream == null)
        {
            return false;
        }

        return true;
    }

    public static File extractZip(File tmpDir, String internalPath) throws IOException
    {
        InputStream internalStream = null;

        File targetDir = new File(tmpDir, internalPath.substring(internalPath.lastIndexOf('/'), internalPath.length() - ".zip".length()));
        if (targetDir.exists())
        {
            return targetDir;
        }
        try
        {
            System.out.println("unzipping " + internalPath);
            targetDir.mkdirs();

            internalStream = Utils.class.getResourceAsStream(internalPath);
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

}