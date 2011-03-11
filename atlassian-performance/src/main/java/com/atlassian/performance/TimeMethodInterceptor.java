package com.atlassian.performance;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.concurrent.TimeoutException;

public class TimeMethodInterceptor implements MethodInterceptor
{

    Logger log = LoggerFactory.getLogger("com.atlassian.performance.");

    public Object invoke(MethodInvocation invocation) throws Throwable {
        TimeMethod tmAnnotation = invocation.getMethod().getAnnotation(TimeMethod.class);
        if((tmAnnotation != null) && (tmAnnotation.value() != null))
        {
            TimedMethodInvocation tmi = new TimedMethodInvocation(invocation);
            EventTime et = EventTime.timeEvent(tmAnnotation.value(), false, tmi);

            String className = invocation.getMethod().getClass().getName();
            String methodName = invocation.getMethod().getName();

            log.debug("{}, {}, {}", new Object[]{className + "." + methodName, tmAnnotation.value(), et.getTime()});

            if(tmi.throwable == null)
            {
                return tmi.val;
            }
            else
            {
                throw tmi.throwable;
            }

        }
        else
        {
            // This shouldn't usually happen but just in case...
            // No annotation to process or no key given proceed as usual
            return invocation.proceed();
        }
    }


    public class TimedMethodInvocation implements EventTime.TimedEvent
    {
        Object val;
        Throwable throwable;
        MethodInvocation invocation;

        TimedMethodInvocation(MethodInvocation invocation)
        {
            this.invocation = invocation;
        }

        public boolean run()
        {
            try
            {
                val = invocation.proceed();
            }
            catch (Throwable t)
            {
                throwable = t;
            }
            finally
            {
                return true;
            }
        }

    }

}
