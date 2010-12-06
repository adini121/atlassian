package com.atlassian.pageobjects.navigator;

import com.atlassian.pageobjects.Link;
import com.atlassian.pageobjects.Tester;
import com.atlassian.pageobjects.page.Page;
import org.junit.Test;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class TestInjectPageNavigator
{
    @Test
    public void testInject()
    {
        MapTester tester = new MapTester(Collections.<Class<?>, Object>singletonMap(String.class, "Bob"));
        InjectPageNavigator<MapTester> navigator = new InjectPageNavigator<MapTester>(tester);

        OneFieldPage page = navigator.create(OneFieldPage.class);
        assertEquals("Bob", page.getName());
    }

    @Test
    public void testInjectWithArgument()
    {
        MapTester tester = new MapTester(Collections.<Class<?>, Object>emptyMap());
        InjectPageNavigator<MapTester> navigator = new InjectPageNavigator<MapTester>(tester);

        OneFieldPage page = navigator.create(OneFieldPage.class, "Bob");
        assertEquals("Bob", page.getName());
    }

    @Test
    public void testInitAfterInject()
    {
        MapTester tester = new MapTester(Collections.<Class<?>, Object>emptyMap());
        InjectPageNavigator<MapTester> navigator = new InjectPageNavigator<MapTester>(tester);

        OneFieldWithInitPage page = navigator.create(OneFieldWithInitPage.class, "Bob");
        assertEquals("Bob Jones", page.getName());
    }

    private static class MapTester implements Tester
    {
        private final Map<Class<?>,Object> injectables;

        public MapTester(Map<Class<?>, Object> injectables)
        {
            this.injectables = injectables;
        }

        public Map<Class<?>, Object> getInjectables()
        {
            return injectables;
        }
    }
    static class OneFieldPage implements Page<MapTester>
    {
        @Inject
        private String name;
        public Page gotoPage(Link link)
        {
            return null;
        }

        public String getName()
        {
            return name;
        }
    }

    static class OneFieldWithInitPage implements Page<MapTester>
    {
        @Inject
        private String name;
        public Page gotoPage(Link link)
        {
            return null;
        }

        @Init
        public void init()
        {
            name += " Jones";
        }

        public String getName()
        {
            return name;
        }
    }

}
