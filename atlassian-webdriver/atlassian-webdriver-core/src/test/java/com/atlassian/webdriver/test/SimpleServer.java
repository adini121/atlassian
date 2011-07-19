package com.atlassian.webdriver.test;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.Validate;
import org.mortbay.jetty.Handler;
import org.mortbay.jetty.Request;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.util.Map;

/**
 * Simple server for serving up html pages.
 */
public class SimpleServer
{
    private int port = 0;
    private Server server = null;

    private final Map<String,String> urlMappings;

    public SimpleServer(Map<String, String> urlMappings) {
        checkPort();
        this.urlMappings = urlMappings;
    }

    public SimpleServer(Map<String, String> urlMappings, int port)
    {
        Validate.isTrue(port > 0, "Port must be a positive number");
        this.port = port;
        this.urlMappings = urlMappings;

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
                String uri = request.getRequestURI();

                if(uri.endsWith("css"))
                {
                    response.setContentType("text/css");
                }
                else  if(uri.endsWith("js"))
                {
                    response.setContentType("text/javascript");
                }
                else
                {
                    response.setContentType("text/html");
                }

                if (urlMappings.containsKey(uri))
                {
                    String filename = urlMappings.get(uri);

                    InputStream is = getClass().getClassLoader().getResourceAsStream(filename);
                    if (is == null)
                    {
                        response.getWriter().println("<h1>Cannot read file at: " + filename + "</h1>");
                    }
                    else
                    {
                        response.getWriter().print(IOUtils.toString(is));
                    }
                }
                else
                {
                    response.getWriter().println("<h1>File not found at: " + uri + "</h1>");
                }
                response.setStatus(HttpServletResponse.SC_OK);
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
