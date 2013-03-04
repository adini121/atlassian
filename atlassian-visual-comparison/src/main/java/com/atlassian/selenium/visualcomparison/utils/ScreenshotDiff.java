package com.atlassian.selenium.visualcomparison.utils;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.velocity.VelocityContext;

import javax.imageio.ImageIO;
import java.awt.*;
import java.util.Collection;
import java.util.Iterator;
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
    private List<BoundingBox> ignoreAreas;
    private Collection<PageDifference> differences;

    public ScreenshotDiff(Screenshot oldScreenshot, Screenshot newScreenshot,
            String id, ScreenResolution resolution,
            BufferedImage diffImage, ArrayList<BoundingBox> boxes, List<BoundingBox> ignoreAreas)
    {
        this.oldScreenshot = oldScreenshot;
        this.newScreenshot = newScreenshot;
        this.id = id;
        this.resolution = resolution;
        this.diffImage = diffImage;
        this.ignoreAreas = ignoreAreas;
        this.differences = new ArrayList<PageDifference>();
        for(BoundingBox box : boxes)
        {
            differences.add(new PageDifference(box));
        }
    }
    
    public Collection<PageDifference> getDifferences()
    {
        return differences;
    }

    /**
     * For tests.
     * @return a list of {@link BoundingBox} elements.
     */
    List<BoundingBox> getDiffAreas()
    {
        List<BoundingBox> boxes = new ArrayList<BoundingBox>();
        for (PageDifference difference : getDifferences())
        {
            boxes.add(difference.getBoundingBox());
        }
        return boxes;
    }

    public static class PageDifference
    {
        private final BoundingBox box;
        private PageDifferenceImages images;
        private final List<PageElementInfo> pageElements;

        public PageDifference(BoundingBox box)
        {
            this.box = box;
            this.pageElements = new ArrayList<PageElementInfo>();
        }

        public BoundingBox getBoundingBox()
        {
            return this.box;
        }

        public void setImages(PageDifferenceImages images)
        {
            this.images = images;
        }

        public PageDifferenceImages getImages()
        {
            return images;
        }

        public void addPageElement(PageElementInfo el)
        {
            getPageElements().add(el);
        }

        public List<PageElementInfo> getPageElements()
        {
            return this.pageElements;
        }
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
        private File oldImageFile;
        private File newImageFile;
        private File diffImageFile;
        private String outputDir;

        public PageDifferenceImages(File oldImageFile, File newImageFile, File diffImageFile, String outputDir)
        {
            this.oldImageFile = oldImageFile;
            this.newImageFile = newImageFile;
            this.diffImageFile = diffImageFile;
            this.outputDir = outputDir;
        }

        public String getOldImageFile()
        {
            return relativePath(oldImageFile, outputDir);
        }

        public String getNewImageFile()
        {
            return relativePath(newImageFile, outputDir);
        }

        public String getDiffImageFile()
        {
            return relativePath(diffImageFile, outputDir);
        }
    }

    public boolean hasDifferences()
    {
        return getDifferences().size() > 0;
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

        int i = 0;
        for (PageDifference difference : getDifferences())
        {
            final BoundingBox box = difference.getBoundingBox();
            Graphics2D graphics = diffImage.createGraphics();

            String oldImagePath = imageOutputDir + "boxold" + i + "-" + id + "." + resolution + ".png";
            String newImagePath = imageOutputDir + "boxnew" + i + "-" + id + "." + resolution + ".png";
            String diffImagePath = imageOutputDir + "boxdiff" + i + "-" + id + "." + resolution + ".png";

            File oldImageFile = writeSubImage(oldScreenshot.getImage(), box, oldImagePath);
            File newImageFile = writeSubImage(newScreenshot.getImage(), box, newImagePath);
            File diffImageFile = writeSubImage(diffImage, box, diffImagePath);
            i++;

            // Once we've written the sub-images, draw a black box around each of the bounding boxes on the diff.
            graphics.setColor(Color.BLACK);
            BasicStroke stroke = new BasicStroke(2.0f,
                    BasicStroke.CAP_SQUARE,
                    BasicStroke.JOIN_MITER,
                    10.0f, new float[] { 10.0f }, 0.0f);
            graphics.setStroke(stroke);
            graphics.drawRect(box.getMarginLeft(), box.getMarginTop(), box.getMarginWidth(diffImage.getWidth() - 1), box.getMarginHeight(diffImage.getHeight() - 1));

            difference.setImages(
                new PageDifferenceImages(oldImageFile,
                    newImageFile,
                    diffImageFile,
                    outputDir)
            );
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
        File diffImageFile = new File(imageOutputDir + "diff-" + id + "." + resolution + ".png");
        ImageIO.write(diffImage, "png", diffImageFile);
        diffImage.flush();

        // Copy the full baseline image to the output directory.
        File oldImageFile = new File(imageOutputDir + "old-" + oldScreenshot.getFileName());
        ImageIO.write(oldScreenshot.getImage(), "png", oldImageFile);

        // Copy the new image to the output directory.
        final File newImageFile = new File(imageOutputDir + newScreenshot.getFileName());
        ImageIO.write(newScreenshot.getImage(), "png", newImageFile);

        VelocityContext context = ReportRenderer.createContext();
        context.put("id", id);
        context.put("resolution", resolution);
        context.put("differences", differences);
        context.put("oldImageFile", relativePath(oldImageFile, outputDir));
        context.put("newImageFile", relativePath(newImageFile, outputDir));
        context.put("diffImageFile", relativePath(diffImageFile, outputDir));
        String report = ReportRenderer.render(context, "visual-regression-report-single.vm");

        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(new File(outputDir + "/report-" + id + "-" + resolution + ".html")));
        writer.append(report);
        writer.close();
    }

    private File writeSubImage(BufferedImage image, BoundingBox box, String outputPath) throws IOException
    {
        BufferedImage boxImage = image.getSubimage(box.getMarginLeft(), box.getMarginTop(), box.getMarginWidth(image.getWidth() - 1), box.getMarginHeight(image.getHeight() - 1));
        File outputFile = new File(outputPath);
        ImageIO.write(boxImage, "png", outputFile);
        return outputFile;
    }

    private static String relativePath(final File file, final String relativeRoot)
    {
        final String fullPath = file.getAbsolutePath();
        String relativePath = fullPath.substring(fullPath.indexOf(relativeRoot)+relativeRoot.length());
        if (relativePath.startsWith("/")) relativePath = "." + relativePath;
        return relativePath;
    }
}
