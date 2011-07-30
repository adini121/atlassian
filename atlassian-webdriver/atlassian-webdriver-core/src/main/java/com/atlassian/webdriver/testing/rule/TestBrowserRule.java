package com.atlassian.webdriver.testing.rule;

import com.atlassian.webdriver.WebDriverFactory;
import com.atlassian.webdriver.testing.annotation.TestBrowser;
import org.junit.Before;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

/**
 * If a test method, class or package has been annotated
 * with the {@link TestBrowser} annotation then the
 * webdriver.browser system property will be set to use to the
 * value of the annotation otherwise it will use the default value.
 * In order for this to be used in tests it requires that the test
 * creates the TestedProduct each test method execution using {@link Before}
 * or uses the {@link TestedProductRule}
 *
 * If using the {@link TestedProductRule} ensure that it is defined
 * before this rule.
 *
 * eg.
 * <code>
 *     @Rule public TestedProductRule product = new TestedProductRule()
 *     @Rule public TestBrowserRule testBrowserRule = new TestBrowserRule();
 * </code>
 *
 * @since v2.1
 */
public class TestBrowserRule implements MethodRule
{
    private String originalBrowserValue = WebDriverFactory.getBrowserProperty();

    public Statement apply(final Statement base, final FrameworkMethod method, final Object target)
    {
        return new Statement()
        {
            @Override
            public void evaluate() throws Throwable
            {
                TestBrowser testBrowser = getTestBrowserAnnotation(method, target);
                if (testBrowser != null)
                {
                    System.setProperty("webdriver.browser", testBrowser.value());
                }
                else
                {
                    System.setProperty("webdriver.browser", originalBrowserValue);
                }
                base.evaluate();
            }
        };
    }

    /**
     * Checks if there is a {@link TestBrowser} annotation on the test method, class or package
     * @return the {@link TestBrowser} annotation or null if there isn't one defined.
     */
    private TestBrowser getTestBrowserAnnotation(FrameworkMethod method, Object target) {
        TestBrowser methodTestBrowser = method.getAnnotation(TestBrowser.class);
        TestBrowser classTestBrowser = target.getClass().getAnnotation(TestBrowser.class);
        TestBrowser packageTestBrowser = target.getClass().getPackage().getAnnotation(TestBrowser.class);

        return methodTestBrowser != null ? methodTestBrowser :
            (classTestBrowser != null ? classTestBrowser : packageTestBrowser);

    }

}
