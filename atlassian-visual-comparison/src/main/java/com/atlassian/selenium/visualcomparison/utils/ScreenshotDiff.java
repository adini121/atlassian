package com.atlassian.selenium.visualcomparison.utils;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.velocity.VelocityContext;

import javax.imageio.ImageIO;
import java.awt.*;
import java.util.List;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class ScreenshotDiff
{
    private Screenshot oldScreenshot;
    private Screenshot newScreenshot;
    private String id;
    private ScreenResolution resolution;
    private BufferedImage diffImage;
    private ArrayList<BoundingBox> boxes;
    private List<BoundingBox> ignoreAreas;
    private List<PageElementInfo> pageElements;

    public ScreenshotDiff(Screenshot oldScreenshot, Screenshot newScreenshot,
            String id, ScreenResolution resolution,
            BufferedImage diffImage, ArrayList<BoundingBox> boxes, List<BoundingBox> ignoreAreas)
    {
        this.oldScreenshot = oldScreenshot;
        this.newScreenshot = newScreenshot;
        this.id = id;
        this.resolution = resolution;
        this.diffImage = diffImage;
        this.boxes = boxes;
        this.ignoreAreas = ignoreAreas;
        this.pageElements = new ArrayList<PageElementInfo>();
    }
    
    public List<BoundingBox> getDiffAreas()
    {
    	return this.boxes;
    }

    public List<PageElementInfo> getPageElements()
    {
        return this.pageElements;
    }

    public static class PageElementInfo
    {
        public String htmlContent;
        public Dimension size;
        public Point position;

        public String getHtmlContent()
        {
            return htmlContent;
        }

        public String getEscapedHtmlString()
        {
            return StringEscapeUtils.escapeHtml(htmlContent);
        }

        public Dimension getSize()
        {
            return size;
        }

        public int getOffsetLeft()
        {
            return (null == position) ? -1 : position.x;
        }

        public int getOffsetTop()
        {
            return (null == position) ? -1 : position.y;
        }

        public int getElementWidth()
        {
            return (null == size) ? -1 : size.width;
        }

        public int getElementHeight()
        {
            return (null == size) ? -1 : size.height;
        }
    }

    public static class PageDifferenceImages
    {
        private String oldImageFile;
        private String newImageFile;
        private String diffImageFile;

        public PageDifferenceImages(String oldImageFile, String newImageFile, String diffImageFile)
        {
            this.oldImageFile = oldImageFile;
            this.newImageFile = newImageFile;
            this.diffImageFile = diffImageFile;
        }

        public String getOldImageFile()
        {
            return oldImageFile;
        }

        public String getNewImageFile()
        {
            return newImageFile;
        }

        public String getDiffImageFile()
        {
            return diffImageFile;
        }
    }

    public boolean hasDifferences()
    {
        return boxes.size() > 0;
    }

    public static String getImageOutputDir(String outputDir, String imageSubDir)
    {
        String imageOutputDir = outputDir + "/";
        if (imageSubDir != null && !imageSubDir.equals(""))
        {
            imageOutputDir = imageOutputDir + imageSubDir + "/";
        }
        return imageOutputDir;
    }

    public void writeDiffReport(String outputDir, String imageSubDir) throws Exception
    {
        if (!hasDifferences())
        {
            return;
        }

        String imageOutputDir = getImageOutputDir(outputDir, imageSubDir);

        ArrayList<PageDifferenceImages> reportDiffs = new ArrayList<PageDifferenceImages>();
        int i = 0;
        for (BoundingBox box : boxes)
        {
            Graphics2D graphics = diffImage.createGraphics();

            String oldImageFile = "boxold" + i + "-" + id + "." + resolution + ".png";
            String newImageFile = "boxnew" + i + "-" + id + "." + resolution + ".png";
            String diffImageFile = "boxdiff" + i + "-" + id + "." + resolution + ".png";
            i++;

            writeSubImage(oldScreenshot.getImage(), box, imageOutputDir + oldImageFile);
            writeSubImage(newScreenshot.getImage(), box, imageOutputDir + newImageFile);
            writeSubImage(diffImage, box, imageOutputDir + diffImageFile);

            // Once we've written the sub-images, draw a black box around each of the bounding boxes on the diff.
            graphics.setColor(Color.BLACK);
            BasicStroke stroke = new BasicStroke(2.0f,
                    BasicStroke.CAP_SQUARE,
                    BasicStroke.JOIN_MITER,
                    10.0f, new float[] { 10.0f }, 0.0f);
            graphics.setStroke(stroke);
            graphics.drawRect(box.getMarginLeft(), box.getMarginTop(), box.getMarginWidth(diffImage.getWidth() - 1), box.getMarginHeight(diffImage.getHeight() - 1));

            reportDiffs.add(new PageDifferenceImages(imageSubDir + "/" + oldImageFile, imageSubDir + "/" + newImageFile,
                    imageSubDir + "/" + diffImageFile));
        }
        if (ignoreAreas != null)
        {
            for (BoundingBox ignoreArea : ignoreAreas)
            {
                Graphics2D graphics = diffImage.createGraphics();
                // Draw an orange box around each of the ignored areas on the diff.
                graphics.setColor(Color.ORANGE);
                BasicStroke stroke = new BasicStroke(2.0f,
                        BasicStroke.CAP_SQUARE,
                        BasicStroke.JOIN_MITER,
                        10.0f, new float[] { 10.0f }, 0.0f);
                graphics.setStroke(stroke);
                graphics.drawRect(ignoreArea.getLeft(), ignoreArea.getTop(), ignoreArea.getWidth(), ignoreArea.getHeight());
            }
        }

        // Write the full diff image to the output directory.
        String diffImageFile = "diff-" + id + "." + resolution + ".png";
        ImageIO.write(diffImage, "png", new File(imageOutputDir + diffImageFile));
        diffImage.flush();

        // Copy the full baseline image to the output directory.
        String oldImageFile = "old-" + oldScreenshot.getFileName();
        ImageIO.write(oldScreenshot.getImage(), "png", new File(imageOutputDir + oldImageFile));

        // Copy the new image to the output directory.
        final String newImageFile = newScreenshot.getFileName();
        ImageIO.write(newScreenshot.getImage(), "png", new File(imageOutputDir + newImageFile));

        VelocityContext context = ReportRenderer.createContext();
        context.put("id", id);
        context.put("resolution", resolution);
        context.put("diffs", reportDiffs);
        context.put("pageElements", pageElements);
        context.put("oldImageFile", imageSubDir + "/" + oldImageFile);
        context.put("newImageFile", imageSubDir + "/" + newImageFile);
        context.put("diffImageFile", imageSubDir + "/" + diffImageFile);
        String report = ReportRenderer.render(context, "visual-regression-report-single.vm");

        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(new File(outputDir + "/report-" + id + "-" + resolution + ".html")));
        writer.append(report);
        writer.close();
    }

    private void writeSubImage(BufferedImage image, BoundingBox box, String outputPath) throws IOException
    {
        BufferedImage boxImage = image.getSubimage(box.getMarginLeft(), box.getMarginTop(), box.getMarginWidth(image.getWidth() - 1), box.getMarginHeight(image.getHeight() - 1));
        ImageIO.write(boxImage, "png", new File(outputPath));
    }
}
