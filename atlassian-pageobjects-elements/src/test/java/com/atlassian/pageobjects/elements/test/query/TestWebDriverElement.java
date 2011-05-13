package com.atlassian.pageobjects.elements.test.query;

import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.ProductInstance;
import com.atlassian.pageobjects.Tester;
import com.atlassian.pageobjects.binder.InjectPageBinder;
import com.atlassian.pageobjects.elements.PageElement;
import com.atlassian.pageobjects.elements.WebDriverElement;
import com.atlassian.pageobjects.elements.timeout.Timeouts;
import com.atlassian.webdriver.AtlassianWebDriver;
import com.google.inject.Binder;
import com.google.inject.Module;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.By;

import static org.mockito.Mockito.when;

/**
 * Test case for {@link com.atlassian.pageobjects.elements.WebDriverElement}.
 *
 * @since v4.4
 */
public class TestWebDriverElement
{
    @Mock
    private ProductInstance mockProductInstance;

    @Mock
    private Tester mockTester;

    @Mock
    private AtlassianWebDriver mockDriver;

    @Mock
    private Timeouts mockTimeouts;

    private PageBinder pageBinder;

    @Before
    public void initMocks()
    {
        MockitoAnnotations.initMocks(this);
        when(mockProductInstance.getBaseUrl()).thenReturn("http://test.atlassian.com:8080/test");
        pageBinder = new InjectPageBinder(mockProductInstance, mockTester, new Module() {
            public void configure(Binder binder) {
                binder.bind(AtlassianWebDriver.class).toInstance(mockDriver);
                binder.bind(Timeouts.class).toInstance(mockTimeouts);
            }
        });
    }

    public void shouldFindSinglePageElement()
    {
        final WebDriverElement tested = pageBinder.bind(WebDriverElement.class, By.id("test-id"));

        PageElement element = tested.find(By.className("inside"));

    }
}
