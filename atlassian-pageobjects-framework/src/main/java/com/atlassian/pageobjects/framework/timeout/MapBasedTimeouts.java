package com.atlassian.pageobjects.framework.timeout;

import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * <p>
 * {@link Timeouts} implementation based on simple mappings between timeout types and values.
 *
 * <p>
 * At the very least, the provided map is supposed to contain timeout value for the {@link TimeoutType#DEFAULT}
 * key. If it is not present, an exception will be raised from the constructor. This value will be used in place of
 * whatever other timeout type that does have corresponding value within the properties.
 *
 */
public class MapBasedTimeouts implements Timeouts
{
    private static final Logger log = LoggerFactory.getLogger(MapBasedTimeouts.class);

    private final Map<TimeoutType,Long> timeouts;
    private final long defaultValue;

    public MapBasedTimeouts(final Map<TimeoutType, Long> timeouts)
    {
        this.timeouts = ImmutableMap.copyOf(checkNotNull(timeouts));
        defaultValue = validateAndGetDefault();
    }

    private long validateAndGetDefault()
    {
        checkArgument(timeouts.containsKey(TimeoutType.DEFAULT), "Must contain default timeout value");
        checkArgument(timeouts.get(TimeoutType.DEFAULT) != null, "Must contain non-null default timeout value");
        return timeouts.get(TimeoutType.DEFAULT);
    }

    public long timeoutFor(final TimeoutType timeoutType)
    {
        Long timeout = timeouts.get(timeoutType);
        if (timeout != null)
        {
            return timeout;
        }
        else
        {
            return defaultValue;
        }
    }
}
