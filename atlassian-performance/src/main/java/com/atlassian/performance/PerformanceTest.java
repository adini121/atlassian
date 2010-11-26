package com.atlassian.performance;

import junit.framework.Test;

public interface PerformanceTest extends Test {

    public TimeRecorder getRecorder();
}
