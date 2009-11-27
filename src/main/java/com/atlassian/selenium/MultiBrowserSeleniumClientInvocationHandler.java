package com.atlassian.selenium;

import com.thoughtworks.selenium.SeleniumCommandTimedOutException;
import com.thoughtworks.selenium.SeleniumException;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

// TODO: According to Jed, this class should be refactored to use the Executor class
// See this blog post or talk to Jed for more information.
// https://extranet.atlassian.com/display/~kellingburg/2008/06/20/java.util.concurrent.*+-+how+HAMS+is+supporting+new+Confluence+Hosted+architecture?showComments=true#comments
public class MultiBrowserSeleniumClientInvocationHandler implements InvocationHandler
{
    private List<SeleniumClient> clients;
    private final long MAX_WAIT;
    private final boolean VERIFY_RETURN_VALUES;

    public MultiBrowserSeleniumClientInvocationHandler(List<SeleniumConfiguration> configs, long maxWait,
                                                       boolean verifyReturnValues)
    {
        clients = new LinkedList<SeleniumClient>();

        for(SeleniumConfiguration config: configs)
        {
            SeleniumClient client = new SingleBrowserSeleniumClient(config);
            client.start();
            clients.add(client);
        }
        this.MAX_WAIT = maxWait;
        this.VERIFY_RETURN_VALUES = verifyReturnValues;
    }



    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        CyclicBarrier barrier = new CyclicBarrier(clients.size() + 1);

        List<MethodHandlerThread> handlers = new LinkedList<MethodHandlerThread>();
        for(final SeleniumClient client : clients)
        {
            MethodHandlerThread handler = new MethodHandlerThread(method, client, args, barrier);
            handler.start();
            handlers.add(handler);
        }

        awaitBarrier(barrier);

        checkExceptions(handlers);
        if(VERIFY_RETURN_VALUES)
        {
            verifyReturnValues(handlers);
        }

        return handlers.get(0).getReturnValue();
    }



    protected void checkExceptions(List<MethodHandlerThread> commands) throws Exception
    {
        for(MethodHandlerThread handler : commands)
        {
            if(handler.getException() != null)
            {
                throw new SeleniumException("Browser " + handler.getBrowser() +
                        " failed with the following exception", handler.getException());
            }
        }
    }


    protected void  verifyReturnValues(List<MethodHandlerThread> commands)
    {
        if(commands.size() > 0)
        {
            MethodHandlerThread first = commands.get(0);
            for (MethodHandlerThread handler : commands)
            {
                if (first.getReturnValue() == null)
                {
                    if(handler.getReturnValue() != null)
                    {
                        throw createValueMismatchException(first, handler); 
                    }
                }
                else
                {
                    if(!first.getReturnValue().equals(handler.getReturnValue()))
                    {
                        throw createValueMismatchException(first, handler);
                    }
                }
            }
        }
    }

    protected SeleniumReturnValueMismatch createValueMismatchException(MethodHandlerThread h1, MethodHandlerThread h2)
    {
          return new SeleniumReturnValueMismatch(h1.getBrowser(), h1.getReturnValue(),
                                h2.getBrowser(), h2.getReturnValue());
    }


    protected void awaitBarrier(CyclicBarrier barrier)
    {
        try {
            barrier.await(MAX_WAIT, TimeUnit.MILLISECONDS);
        }
        catch (InterruptedException ex) {
            throw new SeleniumException(ex);
        }
        catch (BrokenBarrierException ex) {
            throw new SeleniumException(ex);
        }
        catch (TimeoutException ex) {
            throw new SeleniumCommandTimedOutException();
        }
    }
}
