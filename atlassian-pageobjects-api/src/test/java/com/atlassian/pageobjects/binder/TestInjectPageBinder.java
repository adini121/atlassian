package com.atlassian.pageobjects.binder;

import com.atlassian.pageobjects.Page;
import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.ProductInstance;
import com.atlassian.pageobjects.TestedProduct;
import com.atlassian.pageobjects.Tester;
import com.google.inject.Binder;
import com.google.inject.Module;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import static com.google.common.collect.Lists.asList;
import static java.util.Collections.singletonMap;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

/**
 *
 */
public class TestInjectPageBinder
{
    private MyTestedProduct product;

    @Before
    public void setUp() throws Exception
    {
        product = new MyTestedProduct(new SetTester());
    }

    private InjectPageBinder createBinder()
    {
        return createBinder(null, null);
    }
    private InjectPageBinder createBinder(final Class key, final Class impl)
    {
        return new InjectPageBinder(mock(ProductInstance.class), mock(Tester.class), new StandardModule(product),
                new Module()
                {
                    public void configure(Binder binder)
                    {
                        if (key != null)
                        {
                            binder.bind(key).to(impl);
                        }
                    }
                });
    }

    @Test
    public void testInject()
    {
        PageBinder binder = createBinder(StringField.class, StringFieldImpl.class);
        OneFieldPage page = binder.bind(OneFieldPage.class);
        assertEquals("Bob", page.name.getValue());
    }

    @Test
    public void testInjectDefaults()
    {
        PageBinder binder = createBinder();
        DefaultsPage page = binder.bind(DefaultsPage.class);
        assertNotNull(page.testedProduct);
        assertNotNull(page.myTestedProduct);
        assertNotNull(page.tester);
        assertNotNull(page.setTester);
        assertNotNull(page.pageBinder);
        assertNotNull(page.productInstance);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInjectMissing()
    {
        PageBinder binder = createBinder();
        OneFieldPage page = binder.bind(OneFieldPage.class);
        int x = 0;
    }

    @Test
    public void testInjectWithArgument()
    {
        PageBinder binder = createBinder();
        ConstructorArgumentPage page = binder.bind(ConstructorArgumentPage.class, "foo");
        assertEquals("foo", page.name);
    }

    @Test
    public void testInstantiateWithPrimitiveArguments()
    {
        PageBinder binder = createBinder();
        ConstructorArgumentPrimitive object = binder.bind(ConstructorArgumentPrimitive.class, 5, true);
        assertNotNull(object);
        assertEquals(5, object.intField);
        assertTrue(object.booleanField);
    }

    @Test
    public void testInjectWithArgumentSubclass()
    {
        PageBinder binder = createBinder();
        ConstructorArgumentPage page = binder.bind(ConstructorArgumentPage.class, 43);
        assertEquals(43, page.age);
    }

    @Test
    public void testInitAfterInject()
    {
        PageBinder binder = createBinder(StringField.class, StringFieldImpl.class);
        OneFieldWithInitPage page = binder.bind(OneFieldWithInitPage.class);
        assertEquals("Bob Jones", page.name);
    }

    @Test
    public void testParentInject()
    {
        PageBinder binder = createBinder(StringField.class, StringFieldImpl.class);
        ChildNoNamePage page = binder.bind(ChildNoNamePage.class);
        assertEquals("Bob", page.name.getValue());
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
        private StringField name;
    }

    static class ConstructorArgumentPrimitive
    {
        private final int intField;
        private final boolean booleanField;

        public ConstructorArgumentPrimitive(int intArg, boolean booleanArg)
        {
            this.intField = intArg;
            this.booleanField = booleanArg;
        }
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
        protected StringField name;
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
        private SetTester setTester;

        @Inject
        private PageBinder pageBinder;
    }

    static class OneFieldWithInitPage extends AbstractPage
    {
        @Inject
        private StringField field;

        private String name;
        @Init
        public void init()
        {
            name = field.getValue() + " Jones";
        }
    }

    static interface StringField
    {
        String getValue();
    }

    static class StringFieldImpl implements StringField
    {
        public String getValue()
        {
            return "Bob";
        }
    }

}
