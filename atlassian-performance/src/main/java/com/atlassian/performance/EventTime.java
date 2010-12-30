package com.atlassian.performance;

public class EventTime {

    private final String event;
    private final long time;
    private final boolean timedOut;
    private final boolean autoGenerated;

    public EventTime(String event, long time, boolean timedOut, boolean autoGenerated)
    {
        this.event = event;
        this.time = time;
        this.timedOut = timedOut;
        this.autoGenerated = autoGenerated;
    }




    public static long calculateTime(long startTime)
    {
        return System.currentTimeMillis() - startTime;
    }

    public EventTime(String event, long time, boolean autoGenerated)
    {
        this(event, time, false, autoGenerated);
    }

    public String getEvent()
    {
        return event;
    }

    public long getTime()
    {
        return time;
    }

    public boolean getTimedOut()
    {
        return timedOut;        
    }

    public boolean isAutoGenerated()
    {
        return autoGenerated;
    }

    public static EventTime timeEvent(String name, boolean autoGenerated, TimedEvent event)
    {
        long startTime = System.currentTimeMillis();
        boolean timedOut = event.run();
        EventTime et = new EventTime(name, calculateTime(startTime), timedOut, autoGenerated);
        return et;
    }


    public interface TimedEvent
    {
        /**
         * @return True if timed out, false otherwise
         */
        abstract public boolean run();
    }


}