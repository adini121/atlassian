package com.atlassian.webdriver.refapp;

import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.page.AdminHomePage;
import com.atlassian.pageobjects.page.Header;
import com.atlassian.pageobjects.page.HomePage;
import com.atlassian.pageobjects.page.LoginPage;
import com.atlassian.pageobjects.product.Defaults;
import com.atlassian.pageobjects.product.ProductInstance;
import com.atlassian.pageobjects.product.TestedProduct;
import com.atlassian.pageobjects.product.TestedProductFactory;
import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.pageobjects.WebDriverLink;
import com.atlassian.webdriver.pageobjects.WebDriverPageBinder;
import com.atlassian.webdriver.pageobjects.WebDriverTester;
import com.atlassian.webdriver.refapp.component.RefappHeader;
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
public class RefappTestedProduct implements TestedProduct<WebDriverTester, RefappHeader, RefappHomePage, RefappAdminHomePage, RefappLoginPage>
{
    private final PageBinder pageBinder;
    private final WebDriverTester webDriverTester;
    private final ProductInstance productInstance;

    public RefappTestedProduct(TestedProductFactory.TesterFactory<WebDriverTester> testerFactory, ProductInstance productInstance)
    {
        checkNotNull(productInstance);
        WebDriverTester tester = null;
        if (testerFactory == null)
        {
            tester = new WebDriverTester();
        }
        else
        {
            tester = testerFactory.create();
        }
        this.webDriverTester = tester;
        this.productInstance = productInstance;
        this.pageBinder = new WebDriverPageBinder<AtlassianWebDriver>(this);

        this.pageBinder.override(Header.class, RefappHeader.class);
        this.pageBinder.override(HomePage.class, RefappHomePage.class);
        this.pageBinder.override(AdminHomePage.class, RefappAdminHomePage.class);
        this.pageBinder.override(LoginPage.class, RefappLoginPage.class);
    }

    public RefappHomePage gotoHomePage()
    {
        return pageBinder.navigateToAndBind(RefappHomePage.class);
    }

    public RefappAdminHomePage gotoAdminHomePage()
    {
        return pageBinder.navigateToAndBind(RefappAdminHomePage.class);
    }

    public RefappLoginPage gotoLoginPage()
    {
        return pageBinder.navigateToAndBind(RefappLoginPage.class);
    }

    public PageBinder getPageBinder()
    {
        return pageBinder;
    }

    public ProductInstance getProductInstance()
    {
        return productInstance;
    }

    public WebDriverTester getTester()
    {
        return webDriverTester;
    }

    // ---- testing stuff ------------------

    public static final void main(String[] args)
    {
        RefappTestedProduct product = TestedProductFactory.create(RefappTestedProduct.class);
        MyPage page = product.gotoLoginPage()
                             .loginAsSysAdmin(MyPage.class);

        RefappAdminHomePage adminPage = product.gotoAdminHomePage();
        MyPage pageViaLink = product.getPageBinder().bind(MyLink.class).activate();

        MyPage myPage = product.getPageBinder().navigateToAndBind(MyPage.class);
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
