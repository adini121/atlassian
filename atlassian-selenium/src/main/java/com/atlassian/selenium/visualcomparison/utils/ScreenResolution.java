package com.atlassian.selenium.visualcomparison.utils;

import com.atlassian.selenium.SeleniumClient;

public class ScreenResolution implements Comparable<ScreenResolution>
{
    private int width;
    private int height;

    public ScreenResolution(int width, int height)
    {
        this.width = width;
        this.height = height;
    }

    public int compareTo(ScreenResolution other)
    {
        if (this.width < other.width)
        {
            return -1;
        }
        if (this.width > other.width)
        {
            return 1;
        }
        if (this.height < other.height)
        {
            return -1;
        }
        if (this.height > other.height)
        {
            return 1;
        }
        return 0;
    }

    public ScreenResolution(String value)
    {
        String[] parts = value.split("x");
        if (parts.length != 2)
        {
            throw new RuntimeException(value + " is not a valid screen resolution");
        }
        width = Integer.parseInt(parts[0]);
        height = Integer.parseInt(parts[1]);
    }

    public String toString()
    {
        return width + "x" + height;
    }

    public void resize(SeleniumClient client, boolean refresh)
    {
        client.getEval("window.resizeTo(" + width + ", " + height + ");");
        if (refresh)
        {
            client.refresh();
            client.waitForPageToLoad();
            client.waitForAjaxWithJquery();
        }
    }
}
