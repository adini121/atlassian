package com.atlassian.selenium.visualcomparison.utils;

import org.apache.velocity.VelocityContext;

import javax.imageio.ImageIO;
import java.awt.*;
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

    public ScreenshotDiff(Screenshot oldScreenshot, Screenshot newScreenshot,
            String id, ScreenResolution resolution,
            BufferedImage diffImage, ArrayList<BoundingBox> boxes)
    {
        this.oldScreenshot = oldScreenshot;
        this.newScreenshot = newScreenshot;
        this.id = id;
        this.resolution = resolution;
        this.diffImage = diffImage;
        this.boxes = boxes;
    }

    public static class ReportDiffInfo
    {
        private String oldImageFile;
        private String newImageFile;
        private String diffImageFile;

        public ReportDiffInfo(String oldImageFile, String newImageFile, String diffImageFile)
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

    public void writeDiffReport(String outputDir, String imageSubDir) throws Exception
    {
        if (!hasDifferences())
        {
            return;
        }

        String imageOutputDir = outputDir + "/";
        if (imageSubDir != null && !imageSubDir.equals(""))
        {
            imageOutputDir = imageOutputDir + imageSubDir + "/";
        }
        
        ArrayList<ReportDiffInfo> reportDiffs = new ArrayList<ReportDiffInfo>();
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

            reportDiffs.add(new ReportDiffInfo(imageSubDir + "/" + oldImageFile, imageSubDir + "/" + newImageFile,
                    imageSubDir + "/" + diffImageFile));
        }
        // Write the full diff image to the output directory.
        String diffImageFile = "diff-" + id + "." + resolution + ".png";
        ImageIO.write(diffImage, "png", new File(imageOutputDir + diffImageFile));
        diffImage.flush();

        // Copy the full baseline image to the output directory.
        String oldImageFile = "old-" + oldScreenshot.getFileName();
        ImageIO.write(oldScreenshot.getImage(), "png", new File(imageOutputDir + oldImageFile));

        // Copy the new image to the output directory.
        ImageIO.write(newScreenshot.getImage(), "png", new File(imageOutputDir + newScreenshot.getFileName()));

        VelocityContext context = ReportRenderer.createContext();
        context.put("id", id);
        context.put("resolution", resolution);
        context.put("diffs", reportDiffs);
        context.put("oldImageFile", imageSubDir + "/" + oldImageFile);
        context.put("newImageFile", imageSubDir + "/" + newScreenshot.getFileName());
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