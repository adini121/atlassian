package com.atlassian.selenium.visualcomparison.utils;

import com.atlassian.selenium.visualcomparison.VisualComparableClient;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Screenshot implements Comparable<Screenshot>
{
    private ScreenResolution resolution;
    private String id;
    private File file;

    public Screenshot(VisualComparableClient client, String id, String imageDir, ScreenResolution resolution) throws IOException
    {
        final String filePath = imageDir + "/" + id + "." + resolution + ".png";
        client.captureEntirePageScreenshot(filePath);
        init(filePath, id, resolution);
    }

    public Screenshot(File file) throws IOException
    {
        String[] fileNameParts = file.getName().split("\\.");
        if (fileNameParts.length != 3)
        {
            throw new IOException("Invalid screenshot name - " + file.getName());
        }
        init(file.getAbsolutePath(), fileNameParts[0], new ScreenResolution(fileNameParts[1]));
    }

    public static String generateFileName(String id, ScreenResolution resolution)
    {
        return id.replace('.', '-') + "." + resolution + ".png";
    }

    private void init(String filePath, String id, ScreenResolution resolution) throws IOException
    {
        this.id = id;
        this.resolution = resolution;
        this.file = new File(filePath);
    }

    public String getFileName()
    {
        return file.getName();
    }

    public BufferedImage getImage() throws IOException
    {
        return ImageIO.read(file);
    }

    public int compareTo(Screenshot other)
    {
        int result = this.id.compareTo(other.id);
        if (result != 0) {
            return result;
        }
        return this.resolution.compareTo(other.resolution);
    }

    public ScreenshotDiff getDiff(Screenshot other) throws IOException
    {
        return getDiff (other, null, false);
    }

    public ScreenshotDiff getDiff(Screenshot other, List<BoundingBox> ignoreAreas, boolean ignoreSingleLines) throws IOException
    {
        final BufferedImage thisImage = getImage();
        final BufferedImage otherImage = other.getImage();
        final int thisWidth = thisImage.getWidth();
        final int thisHeight = thisImage.getHeight();
        final int otherWidth = otherImage.getWidth();
        final int otherHeight = otherImage.getHeight();
        final int maxWidth = Math.max(thisWidth, otherWidth);
        final int maxHeight = Math.max(thisHeight, otherHeight);

        // Iterate through the pixels in both images and create a diff image.
        BufferedImage diffImage = new BufferedImage(maxWidth, maxHeight, BufferedImage.TYPE_INT_RGB);
        ArrayList<BoundingBox> boxes = new ArrayList<BoundingBox>();
        for (int x = 0; x < maxWidth; x++)
        {
            for (int y = 0; y < maxHeight; y++)
            {
                // The images aren't necessarily the same size, only compare the pixels if they're
                // present in both.
                if ((x < thisWidth) && (x < otherWidth) && (y < thisHeight) && (y < otherHeight))
                {
                    int thisPixel = thisImage.getRGB(x, y);
                    int otherPixel = otherImage.getRGB(x, y);
                    if (shouldIgnorePixel (x, y, ignoreAreas) || thisPixel == otherPixel)
                    {
                        // The pixels are the same or we don't care if they're different, draw it as-is in the diff.
                        diffImage.setRGB(x, y, thisPixel);
                    }
                    else
                    {
                        // The pixels are different, set the pixel to red in the diff.
                        diffImage.setRGB(x, y, 0xFF0000);

                        // If this pixel is near any bounding boxes, expand them to include it.
                        boolean foundBox = false;
                        for (BoundingBox box : boxes)
                        {
                            if (box.isNear(x, y))
                            {
                                box.merge(x, y);
                                foundBox = true;
                            }
                        }
                        // Otherwise, start a new bounding box.
                        if (!foundBox)
                        {
                            boxes.add(new BoundingBox(x, y));
                        }
                    }
                }
                else
                {
                    // The two images are different sizes and this pixel is out of bounds in one of them.
                    // Set the pixel to black in the diff.
                    diffImage.setRGB(x, y, 0x000000);
                }
            }
        }
        if (ignoreSingleLines)
        {
            BoundingBox.deleteSingleLineBoxes(boxes);
        }

        BoundingBox.mergeOverlappingBoxes(boxes);

        thisImage.flush();
        otherImage.flush();

        return new ScreenshotDiff(this, other, this.id, this.resolution, diffImage, boxes, ignoreAreas);
    }

    private boolean shouldIgnorePixel (int x, int y, List<BoundingBox> ignoreAreas)
    {
        if (ignoreAreas == null)
        {
            return false;
        }
        for (BoundingBox ignoreArea : ignoreAreas)
        {
            if (ignoreArea.contains (x,y))
            {
                return true;
            }
        }
        return false;
    }
}
