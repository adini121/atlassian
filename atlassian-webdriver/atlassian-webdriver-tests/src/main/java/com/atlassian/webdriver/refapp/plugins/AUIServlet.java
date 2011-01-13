package com.atlassian.webdriver.refapp.plugins;

import com.atlassian.plugin.webresource.WebResourceManager;
import com.atlassian.templaterenderer.TemplateRenderer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Plugin end point that is deployed to the RefApp. It serves a page that contains AUI components that are used for testing.
 */
public class AUIServlet extends HttpServlet
{
    private final TemplateRenderer templateRenderer;
    private final WebResourceManager webResourceManager;

    public AUIServlet(TemplateRenderer templateRenderer, WebResourceManager webResourceManager)
    {
        this.templateRenderer = templateRenderer;
        this.webResourceManager = webResourceManager;
    }

      @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        res.setContentType("text/html; charset=utf-8");

        String view =req.getParameter("view");
        if(view != null && !view.isEmpty())
        {
            templateRenderer.render(String.format("templates/%s.vm", view), res.getWriter());
        }
        else
        {
            webResourceManager.requireResource("com.atlassian.auiplugin:ajs");
            templateRenderer.render("templates/auipage.vm", res.getWriter());
        }
    }
}
