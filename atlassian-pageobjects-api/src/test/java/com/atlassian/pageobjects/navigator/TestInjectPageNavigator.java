package com.atlassian.pageobjects.navigator;

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
    private InjectPageNavigator navigator;

    @Before
    public void setUp() throws Exception
    {
        tester = new MapTester();
        product = new MyTestedProduct(tester);
        navigator = new InjectPageNavigator(product);
    }

    @Test
    public void testInject()
    {
        tester.add(String.class, "Bob");
        OneFieldPage page = navigator.build(OneFieldPage.class);
        assertEquals("Bob", page.name);
    }

    @Test
    public void testInjectDefaults()
    {
        DefaultsPage page = navigator.build(DefaultsPage.class);
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
        navigator.build(OneFieldPage.class);
    }

    @Test
    public void testInjectWithArgument()
    {
        ConstructorArgumentPage page = navigator.build(ConstructorArgumentPage.class, 43);
        assertEquals(43, page.age);
    }

    @Test
    public void testInitAfterInject()
    {
        tester.add(String.class, "Bob");
        OneFieldWithInitPage page = navigator.build(OneFieldWithInitPage.class);
        assertEquals("Bob Jones", page.name);
    }

    @Test
    public void testParentInject()
    {
        tester.add(String.class, "Bob");
        ChildNoNamePage page = navigator.build(ChildNoNamePage.class);
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
        private final int age;

        public ConstructorArgumentPage(int age)
        {
            this.age = age;
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
        private PageNavigator pageNavigator;
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
