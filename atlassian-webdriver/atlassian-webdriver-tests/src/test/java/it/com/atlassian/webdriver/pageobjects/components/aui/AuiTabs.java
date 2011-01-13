package it.com.atlassian.webdriver.pageobjects.components.aui;

import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.binder.Init;
import com.atlassian.pageobjects.binder.InvalidPageStateException;
import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.pageobjects.component.TabbedComponent;
import com.atlassian.webdriver.pageobjects.element.Element;
import org.openqa.selenium.By;

import javax.inject.Inject;
import java.util.List;

/**
 * Represents a tabbed content area created via AUI.
 *
 * This is an example of a reusable component.
 */
public class AuiTabs implements TabbedComponent
{
     @Inject
    private AtlassianWebDriver driver;

    @Inject
    private PageBinder pageBinder;

    private final By rootLocator;

    private Element rootElement;
    private Element tabTrigger;
    private Element tabView;

    public AuiTabs(By locator)
    {
        this.rootLocator = locator;
    }

    @Init
    public void initialize()
    {
        this.rootElement = driver.find(rootLocator);
    }

    public List<Element> triggers()
    {
        return rootElement.find(By.className("tabs-menu")).findAll(By.tagName("li"));
    }

    public Element trigger(String identifier)
    {
         List<Element> items = triggers();

        for(int i = 0; i < items.size(); i++)
        {
            Element tab = items.get(i);
            if(tab.text().equals(identifier))
            {
                return tab;
            }
        }

        throw new InvalidPageStateException("Tab not found", this);
    }

    public Element panel(String identifier)
    {
        Element tab = trigger(identifier);
        String tabViewClassName = tab.find(By.tagName("a")).attribute("href").substring(1);
        return rootElement.find(By.id(tabViewClassName));
    }

    public String selectedTabText()
    {
        List<Element> items = rootElement.find(By.className("tabs-menu")).findAll(By.tagName("li"));

        for(int i = 0; i < items.size(); i++)
        {
            Element tab = items.get(i);
            if(tab.hasClass("active-tab"))
            {
                return tab.text();
            }
        }
        
        throw new InvalidPageStateException("A tab must be active.", this);
    }

    public Element selectedTabPane()
    {
        List<Element> panes = rootElement.findAll(By.className("tabs-pane"));
        for(int i = 0; i < panes.size(); i++)
        {
            Element pane = panes.get(i);
            if(pane.hasClass("active-pane"))
            {
                return pane; 
            }
        }
        throw new InvalidPageStateException("A pane must be active", this);
    }

    public Element selectedTab()
    {
        List<Element> items = rootElement.find(By.className("tabs-menu")).findAll(By.tagName("li"));

        for(int i = 0; i < items.size(); i++)
        {
            Element tab = items.get(i);
            if(tab.hasClass("active-tab"))
            {
                return tab;
            }
        }

        throw new InvalidPageStateException("A tab must be active.", this);
    }

    public Element selectedView()
    {
         List<Element> panes = rootElement.findAll(By.className("tabs-pane"));
        for(int i = 0; i < panes.size(); i++)
        {
            Element pane = panes.get(i);
            if(pane.hasClass("active-pane"))
            {
                return pane;
            }
        }
        
        throw new InvalidPageStateException("A pane must be active", this);
    }

    public List<Element> tabs()
    {
        return rootElement.find(By.className("tabs-menu")).findAll(By.tagName("li"));
    }

    public Element openTab(String tabText)
    {
        List<Element> tabs = rootElement.find(By.className("tabs-menu")).findAll(By.tagName("a"));

        for(int i = 0; i < tabs.size(); i++)
        {
            if(tabs.get(i).text().equals(tabText))
            {
                Element listItem = tabs.get(i);
                listItem.click();

                // find the pane and wait until it has class "active-pane"
                String tabViewClassName = listItem.attribute("href").substring(1);
                Element pane = rootElement.find(By.id(tabViewClassName));
                pane.timed().hasClass("active-pane").waitFor(true);

                return pane;
            }
        }

        throw new InvalidPageStateException("Tab not found", this);
    }

    public Element openTab(Element tab)
    {
        String tabIdentifier = tab.text();
        return openTab(tabIdentifier);
    }
}
