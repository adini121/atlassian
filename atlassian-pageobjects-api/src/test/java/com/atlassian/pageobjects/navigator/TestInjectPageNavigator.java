package com.atlassian.pageobjects.navigator;

import com.atlassian.pageobjects.Link;
import com.atlassian.pageobjects.PageNavigator;
import com.atlassian.pageobjects.Tester;
import com.atlassian.pageobjects.page.Page;
import com.atlassian.pageobjects.product.ProductInstance;
import com.atlassian.pageobjects.product.TestedProduct;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

/**
 *
 */
public class TestInjectPageNavigator
{
    private MapTester tester;
    private MyTestedProduct product;
    private InjectPageNavigator<MapTester> navigator;

    @Before
    public void setUp() throws Exception
    {
        tester = new MapTester();
        product = new MyTestedProduct(tester);
        navigator = new InjectPageNavigator<MapTester>(product);
    }

    @Test
    public void testInject()
    {
        tester.add(String.class, "Bob");
        OneFieldPage page = navigator.create(OneFieldPage.class);
        assertEquals("Bob", page.name);
    }

    @Test
    public void testInjectDefaults()
    {
        DefaultsPage page = navigator.create(DefaultsPage.class);
        assertNotNull(page.testedProduct);
        assertNotNull(page.myTestedProduct);
        assertNotNull(page.tester);
        assertNotNull(page.mapTester);
        assertNotNull(page.pageNavigator);
        assertNotNull(page.productInstance);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInjectMissing()
    {
        navigator.create(OneFieldPage.class);
    }

    @Test
    public void testInjectWithArgument()
    {
        OneFieldPage page = navigator.create(OneFieldPage.class, "Bob");
        assertEquals("Bob", page.name);
    }

    @Test
    public void testInitAfterInject()
    {
        OneFieldWithInitPage page = navigator.create(OneFieldWithInitPage.class, "Bob");
        assertEquals("Bob Jones", page.name);
    }



    static class OneFieldPage implements Page<MapTester>
    {
        @Inject
        private String name;
        public Page gotoPage(Link link)
        {
            return null;
        }
    }

    static class DefaultsPage implements Page<MapTester>
    {
        @Inject
        private ProductInstance productInstance;

        @Inject
        private TestedProduct testedProduct;

        @Inject
        private MyTestedProduct myTestedProduct;

        @Inject
        private Tester tester;

        @Inject
        private MapTester mapTester;

        @Inject
        private PageNavigator pageNavigator;
        public Page gotoPage(Link link)
        {
            return null;
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
    }

}
