package com.atlassian.pageobjects.framework.mock.clock;

import com.atlassian.pageobjects.framework.query.util.Clock;
import com.google.common.collect.Maps;

import java.util.NavigableMap;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Composite clock that returns answer from one of the clocks it builds upon, depending on the number of times it
 * was called already.
 *
 */
public class CompositeClock implements Clock
{
    private final NavigableMap<Integer, Clock> clocks = Maps.newTreeMap();
    private int currentCall = 1;


    public CompositeClock(Clock initialClock)
    {
        clocks.put(1, checkNotNull(initialClock, "initialClock"));
    }

    public CompositeClock addClock(int startFromCall, Clock clock)
    {
        checkArgument(startFromCall > clocks.lastKey(), "startFromCall must be greater than the current last clock");
        clocks.put(startFromCall, clock);
        return this;
    }

    public long currentTime()
    {
        long answer = findClock().currentTime();
        currentCall++;
        return answer;
    }

    private Clock findClock()
    {
        if (currentCall >= clocks.lastKey())
        {
            return clocks.lastEntry().getValue();
        }
        int clockKey = clocks.firstKey();
        for (int startFromCall : clocks.keySet())
        {
            if (currentCall < startFromCall)
            {
                break;
            }
            else
            {
                clockKey = startFromCall;
            }
        }
        return clocks.get(clockKey);
    }
}
