package com.atlassian.webdriver.refapp;

import com.atlassian.pageobjects.PageNavigator;
import com.atlassian.pageobjects.product.Defaults;
import com.atlassian.pageobjects.product.ProductInstance;
import com.atlassian.pageobjects.product.ProductType;
import com.atlassian.pageobjects.product.TestedProduct;
import com.atlassian.pageobjects.product.TestedProductFactory;
import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.browsers.pageobjects.AutoInstallWebDriverTester;
import com.atlassian.webdriver.pageobjects.WebDriverLink;
import com.atlassian.webdriver.pageobjects.WebDriverPageNavigator;
import com.atlassian.webdriver.pageobjects.WebDriverTester;
import com.atlassian.webdriver.refapp.page.RefappAbstractPage;
import com.atlassian.webdriver.refapp.page.RefappAdminHomePage;
import com.atlassian.webdriver.refapp.page.RefappHomePage;
import com.atlassian.webdriver.refapp.page.RefappLoginPage;
import org.openqa.selenium.By;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 *
 */
@Defaults(instanceId = "refapp", contextPath = "/refapp", httpPort = 5990)
public class RefappTestedProduct implements TestedProduct<WebDriverTester<AtlassianWebDriver>, RefappHomePage, RefappAdminHomePage, RefappLoginPage>
{
    private final PageNavigator pageNavigator;
    private final WebDriverTester<AtlassianWebDriver> webDriverTester;
    private final ProductInstance productInstance;

    public RefappTestedProduct(TestedProductFactory.TesterFactory<WebDriverTester<AtlassianWebDriver>> testerFactory, ProductInstance productInstance)
    {
        checkNotNull(productInstance);
        WebDriverTester<AtlassianWebDriver> tester = null;
        if (testerFactory == null)
        {
            tester = new AutoInstallWebDriverTester();
        }
        else
        {
            tester = testerFactory.create();
        }
        this.webDriverTester = tester;
        this.productInstance = productInstance;
        this.pageNavigator = new WebDriverPageNavigator<AtlassianWebDriver>(this);
    }

    public RefappHomePage gotoHomePage()
    {
        return pageNavigator.gotoPage(RefappHomePage.class);
    }

    public RefappAdminHomePage gotoAdminHomePage()
    {
        return pageNavigator.gotoPage(RefappAdminHomePage.class);
    }

    public RefappLoginPage gotoLoginPage()
    {
        return pageNavigator.gotoPage(RefappLoginPage.class);
    }

    public PageNavigator getPageNavigator()
    {
        return pageNavigator;
    }

    public ProductInstance getProductInstance()
    {
        return productInstance;
    }

    public WebDriverTester<AtlassianWebDriver> getTester()
    {
        return webDriverTester;
    }

    public ProductType getProductType()
    {
        return ProductType.REFAPP;
    }

    public static final void main(String[] args)
    {
        RefappTestedProduct product = TestedProductFactory.create(RefappTestedProduct.class);
        MyPage page = product.gotoLoginPage()
                             .loginAsSysAdmin(RefappAdminHomePage.class)
                             .gotoPage(product.getPageNavigator().createLink(MyLink.class));
        
        MyPage myPage = product.getPageNavigator().gotoPage(MyPage.class);
    }

    public static class MyLink extends WebDriverLink<MyPage>
    {
        public MyLink()
        {
            super(By.id("my-link-id"), MyPage.class);
        }
    }

    public static class MyPage extends RefappAbstractPage
    {
        public String getUrl()
        {
            return "/mypage";
        }
    }
}
