package com.atlassian.pageobjects.binder;

import com.atlassian.pageobjects.Page;
import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.ProductInstance;
import com.atlassian.pageobjects.TestedProduct;
import com.atlassian.pageobjects.Tester;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

/**
 *
 */
public class TestInjectPageBinder
{
    private MapTester tester;
    private MyTestedProduct product;
    private InjectPageBinder binder;

    @Before
    public void setUp() throws Exception
    {
        tester = new MapTester();
        product = new MyTestedProduct(tester);
        binder = new InjectPageBinder(product);
    }

    @Test
    public void testInject()
    {
        tester.add(String.class, "Bob");
        OneFieldPage page = binder.bind(OneFieldPage.class);
        assertEquals("Bob", page.name);
    }

    @Test
    public void testInjectDefaults()
    {
        DefaultsPage page = binder.bind(DefaultsPage.class);
        assertNotNull(page.testedProduct);
        assertNotNull(page.myTestedProduct);
        assertNotNull(page.tester);
        assertNotNull(page.mapTester);
        assertNotNull(page.pageBinder);
        assertNotNull(page.productInstance);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInjectMissing()
    {
        binder.bind(OneFieldPage.class);
    }

    @Test
    public void testInjectWithArgument()
    {
        ConstructorArgumentPage page = binder.bind(ConstructorArgumentPage.class, "foo");
        assertEquals("foo", page.name);
    }

    @Test
    public void testInjectWithArgumentSubclass()
    {
        ConstructorArgumentPage page = binder.bind(ConstructorArgumentPage.class, 43);
        assertEquals(43, page.age);
    }

    @Test
    public void testInitAfterInject()
    {
        tester.add(String.class, "Bob");
        OneFieldWithInitPage page = binder.bind(OneFieldWithInitPage.class);
        assertEquals("Bob Jones", page.name);
    }

    @Test
    public void testParentInject()
    {
        tester.add(String.class, "Bob");
        ChildNoNamePage page = binder.bind(ChildNoNamePage.class);
        assertEquals("Bob", page.name);
    }


    static class AbstractPage implements Page
    {
        public String getUrl()
        {
            return null;
        }
    }

    static class OneFieldPage extends AbstractPage
    {
        @Inject
        private String name;
    }

    static class ConstructorArgumentPage extends AbstractPage
    {
        private final String name;
        private final Number age;

        public ConstructorArgumentPage(String name)
        {
            this.name = name;
            this.age = null;
        }

        public ConstructorArgumentPage(Number age)
        {
            this.age = age;
            this.name = null;
        }
    }

    static class ParentNamePage extends AbstractPage
    {
        @Inject
        protected String name;
    }

    static class ChildNoNamePage extends ParentNamePage
    {
    }

    static class DefaultsPage extends AbstractPage
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
        private PageBinder pageBinder;
    }

    static class OneFieldWithInitPage extends AbstractPage
    {
        @Inject
        private String name;

        @Init
        public void init()
        {
            name += " Jones";
        }
    }

}
