package com.atlassian.webdriver.refapp.plugins;

import com.atlassian.plugin.webresource.WebResourceManager;
import com.atlassian.templaterenderer.TemplateRenderer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Plugin end point that is deployed to the RefApp. It serves a page that contains html elements with delays used to
 * test Element and TimedElement
 */
public class HtmlServlet extends HttpServlet
{
    private final TemplateRenderer templateRenderer;
    private final WebResourceManager webResourceManager;

     public HtmlServlet(TemplateRenderer templateRenderer, WebResourceManager webResourceManager)
    {
        this.templateRenderer = templateRenderer;
        this.webResourceManager = webResourceManager;
    }

      @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        webResourceManager.requireResource("integration-tests:jquery-js");

        res.setContentType("text/html; charset=utf-8");
        templateRenderer.render("templates/htmlpage.vm", res.getWriter());

    }
}
