package com.atlassian.webdriver.browsers;

import org.apache.commons.lang.Validate;
import org.mortbay.jetty.Handler;
import org.mortbay.jetty.Request;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.AbstractHandler;

import java.io.IOException;
import java.net.ServerSocket;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
class HelloServer
{
    private int port = 0;
    private Server server = null;

    public HelloServer() {
        checkPort();
    }

    public HelloServer(int port)
    {
        Validate.isTrue(port > 0, "Port must be a positive number");
        this.port = port;

        checkPort();
    }

    public void stopServer() throws Exception
    {
        if (server != null)
        {
            server.stop();
        }
    }

    public void startServer() throws Exception
    {
        Handler handler=new AbstractHandler()
        {
            public void handle(String target, HttpServletRequest request, HttpServletResponse response, int dispatch)
                throws IOException, ServletException
            {
                response.setContentType("text/html");
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().println("<h1>Hello</h1>");
                ((Request)request).setHandled(true);
            }
        };
        
        server = new Server(port);
        server.setHandler(handler);
        server.start();
    }

    public int getPort()
    {
        return port;
    }

    private void checkPort()
    {
        ServerSocket socket = null;
        try
        {
            socket = new ServerSocket(port);
            this.port = socket.getLocalPort();
            return;
        }
        catch (IOException e)
        {
            throw new RuntimeException("Error opening socket", e);
        }
        finally
        {
            if (socket != null)
            {
                try
                {
                    socket.close();
                }
                catch (IOException e)
                {
                    throw new RuntimeException("Error closing socket", e);
                }
            }
        }
    }

}
