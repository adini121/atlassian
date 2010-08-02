package com.atlassian.webdriver.component.link;

import com.atlassian.webdriver.page.Page;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO: Document this class / interface here
 *
 * @since v4.2
 */
public class PageLinkFactory
{

    private static Map<Link, Page> links = new HashMap<Link, Page>();

    public static void add(Link link, Page page)
    {
        links.put(link, page);
    }

    public static void add(Map<Link, Page> links)
    {
        PageLinkFactory.links.putAll(links);
    }

    public static Page get(Link link)
    {
        if (links.containsKey(link))
        {
            return links.get(link);
        }

        throw new IllegalArgumentException("No link was found for link: " + link);
    }

}
