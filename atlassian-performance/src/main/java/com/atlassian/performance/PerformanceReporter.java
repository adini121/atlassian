package com.atlassian.performance;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

public class PerformanceReporter
{
    private List<TimeRecorder> recorders = new ArrayList<TimeRecorder>();

    private static final String SEPARATOR = ", ";
    private static final String ENDLINE = "\n";

    public void addRecorder(TimeRecorder recorder)
    {
        recorders.add(recorder);
    }

    public void generateReport(Writer writer, boolean includeAutoGenEvents)  throws IOException
    {
        String[] columns = getColumnList(includeAutoGenEvents);

        writeColumns(writer, columns);

        for(TimeRecorder recorder : recorders)
        {
            writeRecord(writer, recorder.getTestName(), columns, recorder, includeAutoGenEvents);
        }
    }

    private void writeColumns(Writer writer, String[] columns) throws IOException
    {
        writer.write("Test");
        for(String col : columns)
        {
            writer.write(SEPARATOR + col);
        }
        writer.write(ENDLINE);
    }
    
    private void writeRecord(Writer writer, String testName, String[] columns, TimeRecorder recorder, boolean includeAutoGenEvents)
            throws IOException
    {
        for(EventTime event : recorder.getEventTimes())
        {
            if (includeAutoGenEvents || (!event.isAutoGenerated()))
            {
                writer.write(testName);

                int pos = Arrays.binarySearch(columns, event.getEvent());

                repeatWrite(writer, SEPARATOR, pos + 1);
                writer.write(Long.toString(event.getTime()));
                repeatWrite(writer, SEPARATOR, columns.length - pos -1);
                writer.write(ENDLINE);
            }
        }
    }

    private void repeatWrite(Writer writer, String str, int times) throws IOException
    {
        for(int i = 0; i < times; i++)
        {
             writer.write(str);
        }

    }

    public void writeReport(File dest, boolean includeAutoGenEvents) throws IOException
    {
        FileWriter fwriter = new FileWriter(dest);
        generateReport(fwriter, includeAutoGenEvents);
    }

    public String[] getColumnList(boolean includeAutoGenEvents)
    {
        Set<String> columns = new TreeSet<String>();
        for(TimeRecorder recorder : recorders)
        {
            for(EventTime event : recorder.getEventTimes())
            {
                if (includeAutoGenEvents || (!event.isAutoGenerated()))
                {
                    columns.add(event.getEvent());
                }
            }
        }
        String[] colArray = {};
        colArray= (String[])columns.toArray(colArray);
        Arrays.sort(colArray);
        return colArray;
    }

}
