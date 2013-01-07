package com.atlassian.webdriver.visualcomparison;

import com.atlassian.selenium.visualcomparison.VisualComparableClient;
import com.atlassian.selenium.visualcomparison.utils.ScreenResolution;
import com.atlassian.webdriver.AtlassianWebDriver;
import com.google.common.base.Function;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @since 2.1
 */
public class WebDriverVisualComparableClient implements VisualComparableClient
{
    private final AtlassianWebDriver driver;
    private Dimension documentSize;
    private Dimension viewportSize;

    public WebDriverVisualComparableClient(final AtlassianWebDriver driver)
    {
        this.driver = driver;
    }

    public void captureEntirePageScreenshot (String filePath)
    {
        driver.takeScreenshotTo(new File(filePath));
        documentSize = getDimensionsFor("document");
        viewportSize = getDimensionsFor("window");
    }

    private Dimension getDimensionsFor(String selector)
    {
        int x = Integer.parseInt(execute("return jQuery(" + selector + ").width();").toString());
        int y = Integer.parseInt(execute("return jQuery(" + selector + ").height();").toString());
        return new Dimension(x,y);
    }

    public Object getElementAtPoint(int x, int y)
    {
        int delta = documentSize.height - viewportSize.height;
        int scrollY = Math.min(delta, y);
        int relY = y - scrollY; // number between 0 and viewportSize.height
        execute(String.format("window.scrollTo(%d,%d)",x,scrollY));
        WebElement el = driver.findElement(atPointInDom(x,relY));
        return el;
    }

    private By atPointInDom(int x, int relY)
    {
        final String domSel = String.format("return document.elementFromPoint(%d,%d);",x,relY);
        final Object o = driver.executeScript(domSel);

        if (o instanceof WebElement) {
            return new By() {
                @Override
                public List<WebElement> findElements(SearchContext searchContext) {
                    return new ArrayList<WebElement>() {
                        {
                            add((WebElement) o);
                        }
                    };
                }
            };
        }

        return null;
    }

    public void evaluate (String command)
    {
        execute(command);
    }

    public Object execute (String command, Object... arguments)
    {
        return driver.executeScript(command, arguments);
    }

    public boolean resizeScreen(ScreenResolution resolution, boolean refresh)
    {
        driver.getDriver().manage().window().setSize(new Dimension(resolution.width, resolution.height));
        viewportSize = getDimensionsFor("window");
        if (refresh)
        {
            refreshAndWait();
        }
        return true;
    }

    public void refreshAndWait ()
    {
        // WebDriver automatically waits, or so the docs say.
        driver.navigate().refresh();
    }

    public boolean waitForJQuery (long waitTimeMillis)
    {
        try
        {
            driver.waitUntil(new Function<WebDriver, Boolean>() {
                public Boolean apply(WebDriver webDriver) {
                    String jQueryActive = ((JavascriptExecutor)webDriver).executeScript("return (window.jQuery.active)").toString();
                    return (jQueryActive).equals ("0");
                }
            }, (int)waitTimeMillis);
            Thread.sleep(400);
        }
        catch (InterruptedException e)
        {
            return false;
        }
        return true;
    }
}
