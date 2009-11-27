package com.atlassian.selenium;

import java.lang.reflect.Method;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class MethodHandlerThread extends Thread
{
    protected final SeleniumClient client;
    protected final Method method;
    protected Object ret = null;
    protected final Object[] args;
    protected final CyclicBarrier barrier;
    protected Exception ex = null;

    public MethodHandlerThread(Method method, SeleniumClient client, Object[] args, CyclicBarrier barrier)
    {
        this.client = client;
        this.method = method;
        this.args = args;
        this.barrier = barrier;
    }

    public Object getReturnValue()
    {
        return ret;
    }

    public Exception getException()
    {
        return ex;
    }

    public void run()
    {
        try{
            ret = method.invoke(client, args);
        }
        catch (Exception e)
        {
            ex = e;
        }
        try {
            barrier.await();
        }
        // In either of these cases the main thread will
        // also receive an exception so we deal with it there.
        catch (InterruptedException ex) {
        }
        catch (BrokenBarrierException ex) {
        }
    }

    public SeleniumClient.Browser getBrowser()
    {
        return client.getBrowser();
    }
}