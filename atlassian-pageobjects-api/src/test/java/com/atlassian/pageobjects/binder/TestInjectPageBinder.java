package com.atlassian.pageobjects.binder;

import com.atlassian.pageobjects.Page;
import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.ProductInstance;
import com.atlassian.pageobjects.TestedProduct;
import com.atlassian.pageobjects.Tester;
import com.atlassian.pageobjects.inject.ConfigurableInjectionContext;
import com.google.inject.Binder;
import com.google.inject.Module;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

/**
 *
 */
@SuppressWarnings("unchecked")
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
    private InjectPageBinder createBinder(final Class<?> key, final Class impl)
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
    public void testPrivateInitAfterInject()
    {
        PageBinder binder = createBinder(StringField.class, StringFieldImpl.class);
        OneFieldWithPrivateInitPage page = binder.bind(OneFieldWithPrivateInitPage.class);
        assertEquals("Bob Private", page.name);
    }

    @Test
    public void testOneFieldWithSuperClassInit()
    {
        PageBinder binder = createBinder(StringField.class, StringFieldImpl.class);
        OneFieldWithSuperClassInitPage page = binder.bind(OneFieldWithSuperClassInitPage.class);
        assertEquals("Bob Private", page.getName());

    }

    @Test
    public void testProtectedInitAfterInject()
    {
        PageBinder binder = createBinder(StringField.class, StringFieldImpl.class);
        OneFieldWithProtectedInitPage page = binder.bind(OneFieldWithProtectedInitPage.class);
        assertEquals("Bob Protected", page.name);
    }

    @Test
    public void testParentInject()
    {
        PageBinder binder = createBinder(StringField.class, StringFieldImpl.class);
        ChildNoNamePage page = binder.bind(ChildNoNamePage.class);
        assertEquals("Bob", page.name.getValue());
    }

    @Test
    public void shouldImplementConfigurableInjectionContext()
    {
        final PageBinder binder = createBinder(StringField.class, StringFieldImpl.class);
        assertThat(binder, CoreMatchers.instanceOf(ConfigurableInjectionContext.class));
        assertEquals("Bob", binder.bind(OneFieldPage.class).name.getValue());
        ConfigurableInjectionContext.class.cast(binder)
                .configure()
                .addImplementation(StringField.class, AnotherStringFieldImpl.class)
                .finish();
        assertEquals("Rob", binder.bind(OneFieldPage.class).name.getValue());
    }

    @Test
    public void shouldAllowConfiguringNewSingletonInstanceThatIsSubclassOfInterfaceType()
    {
        final PageBinder binder = createBinder();
        ConfigurableInjectionContext.class.cast(binder)
                .configure()
                .addSingleton(StringField.class, new StringFieldImpl())
                .finish();
        assertEquals("Bob", binder.bind(OneFieldPage.class).name.getValue());
    }

    @Test
    public void shouldAllowConfiguringNewImplementationInstance()
    {
        final PageBinder binder = createBinder(StringField.class, StringFieldImpl.class);
        assertEquals("Bob", binder.bind(OneFieldPage.class).name.getValue());
        ConfigurableInjectionContext.class.cast(binder)
                .configure()
                .addSingleton(StringField.class, new StringField()
                {
                    @Override
                    public String getValue()
                    {
                        return "Boom!";
                    }
                })
                .finish();
        assertEquals("Boom!", binder.bind(OneFieldPage.class).name.getValue());
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

    static class AnotherStringFieldImpl implements StringField
    {
        public String getValue()
        {
            return "Rob";
        }
    }

    static class OneFieldWithPrivateInitPage extends AbstractPage
    {
        @Inject
        private StringField field;

        private String name;

        @Init
        private void init()
        {
            name = field.getValue() + " Private";
        }

        public String getName()
        {
            return name;
        }
    }

    static class OneFieldWithProtectedInitPage extends AbstractPage
    {
        @Inject
        private StringField field;

        private String name;

        @Init
        protected void init()
        {
            name = field.getValue() + " Protected";
        }
    }

    static class OneFieldWithSuperClassInitPage extends OneFieldWithPrivateInitPage
    {
    }

}
