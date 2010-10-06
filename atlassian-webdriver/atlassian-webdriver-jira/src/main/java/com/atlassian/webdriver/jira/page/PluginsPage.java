package com.atlassian.webdriver.jira.page;

import com.atlassian.webdriver.utils.ByJquery;
import com.google.common.collect.ImmutableSet;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Page object implementation for the Plugins page for JIRA.
 * TODO: add plugin details method, which also returns loaded modules per plugin
 */
public class PluginsPage extends JiraAdminAbstractPage
{
    private final static String URI = "/secure/admin/jira/ViewPlugins!default.jspa";

    private static final String PLUGIN_KEY = "pluginKey=";
    private final Map<String, WebElement> loadedPlugins;
    private String activePluginKey;
    private final Set<String> pluginsWithErrors;
    private final Set<String> disabledPlugins;

    public PluginsPage(WebDriver driver)
    {
        super(driver);

        loadedPlugins = new HashMap<String, WebElement>();
        pluginsWithErrors = new HashSet<String>();
        disabledPlugins = new HashSet<String>();
    }

    public PluginsPage get(boolean activated)
    {
        get(URI, activated);

        waitForLoadedPlugins();

        return this;
    }

    public PluginsPage selectPlugin(String pluginKey)
    {
        if (pluginIsLoaded(pluginKey))
        {
            loadedPlugins.get(pluginKey).click();
            return JiraPages.PLUGINSPAGE.get(driver, true);
        }

        return null;
    }

    public boolean pluginIsLoaded(String pluginKey)
    {
        return loadedPlugins.containsKey(pluginKey);
    }

    public boolean pluginIsDisabled(String pluginKey)
    {
        return disabledPlugins.contains(pluginKey);
    }

    public boolean pluginHasErrors(String pluginKey)
    {
        return pluginsWithErrors.contains(pluginKey);
    }

    public Set<String> getPluginsWithLoadingErrors()
    {
        return ImmutableSet.copyOf(pluginsWithErrors);
    }

    public Set<String> getDisabledPlugins()
    {
        return ImmutableSet.copyOf(disabledPlugins);
    }

    public String getActivePluginKey()
    {
        return activePluginKey;
    }

    private void waitForLoadedPlugins()
    {
        WebElement table = driver.findElement(ByJquery.$("table table table table"));

        List<WebElement> pluginAnchors = table.findElements(ByJquery.$("tr td a[href^=ViewPlugins.jspa]"));
        List<WebElement> pluginAnchorsWithErrors = table.findElements(ByJquery.$("tr td:contains(Errors loading plugin) a"));
        List<WebElement> disabledPluginAnchors = table.findElements(ByJquery.$("tr td:contains(Plugin currently disabled) a"));

        WebElement activePluginAnchor = table.findElement(ByJquery.$("tr td > div > b > a"));
        String activePluginUrl = activePluginAnchor.getAttribute("href");
        int activePluginKeyIndex = activePluginUrl.indexOf(PLUGIN_KEY);
        this.activePluginKey = activePluginUrl.substring(activePluginKeyIndex + PLUGIN_KEY.length());

        for (WebElement pluginAnchor : pluginAnchors)
        {
            String pluginUrl = pluginAnchor.getAttribute("href");
            int pluginKeyIndex = pluginUrl.indexOf(PLUGIN_KEY);
            String pluginKey = pluginUrl.substring(pluginKeyIndex + PLUGIN_KEY.length());

            loadedPlugins.put(pluginKey, pluginAnchor);
        }

        for (WebElement pluginAnchor : pluginAnchorsWithErrors)
        {
            String pluginUrl = pluginAnchor.getAttribute("href");
            int pluginKeyIndex = pluginUrl.indexOf(PLUGIN_KEY);
            String pluginKey = pluginUrl.substring(pluginKeyIndex + PLUGIN_KEY.length());

            pluginsWithErrors.add(pluginKey);
        }

        for (WebElement pluginAnchor : disabledPluginAnchors)
        {
            String pluginUrl = pluginAnchor.getAttribute("href");
            int pluginKeyIndex = pluginUrl.indexOf(PLUGIN_KEY);
            String pluginKey = pluginUrl.substring(pluginKeyIndex + PLUGIN_KEY.length());

            disabledPlugins.add(pluginKey);
        }

    }

}