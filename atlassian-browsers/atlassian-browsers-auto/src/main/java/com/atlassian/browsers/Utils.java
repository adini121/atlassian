package com.atlassian.browsers;

import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.atlassian.browsers.ProcessRunner.runProcess;

/**
 * Utilities used by the BrowserAutoInstaller
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

    /**
     * Recursively searches for a file in a given path.
     * @param in the path to look in
     * @param file the file name to look for
     * @param exactMatch whether the filename must equal or contain the file name you are looking for
     * @return
     * @throws IOException
     */
    public static File findFile(File in, final String file, final boolean exactMatch) throws IOException
    {
        File[] files = in.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {

                boolean isDir = new File(dir, name).isDirectory();

                if (exactMatch)
                {
                    return !isDir && name.equals(file);
                }
                else
                {
                    return !isDir && name.contains(file);
                }
            }
        });

        if (files.length > 0)
        {
            return files[0];
        }

        File[] dirs = in.listFiles(new FileFilter() {
            public boolean accept(final File pathname)
            {
                return pathname.isDirectory();
            }
        });

        for(File dir : dirs)
        {
            File f = findFile(dir, file, exactMatch);
            if (f != null)
            {
                return f;
            }
        }

        return null;
    }

    /**
     * Checks that a resource exists for the given path.
     * @param path the path to check
     * @return true if the resource at the given path exists
     */
    public static boolean resourceExists(String path)
    {
        InputStream internalStream = Utils.class.getResourceAsStream(path);
        if (internalStream == null)
        {
            return false;
        }

        return true;
    }

    /**
     * Will extract a zip file given by the internal path into the destination dir given
     * @param destDir The File indicating the directory to unzip the file into. This will become the parent directory.
     * @param internalPath The path to the zip file.
     * @return The directory that the zip file was extracted into.
     * @throws IOException
     */
    public static File extractZip(File destDir, String internalPath) throws IOException
    {
        InputStream internalStream = null;

        File targetDir = new File(destDir, internalPath.substring(internalPath.lastIndexOf('/'), internalPath.length() - ".zip".length()));
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