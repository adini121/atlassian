package com.atlassian.webdriver.refapp;

import com.atlassian.pageobjects.PageNavigator;
import com.atlassian.pageobjects.navigator.InjectPageNavigator;
import com.atlassian.pageobjects.product.TestedProduct;
import com.atlassian.pageobjects.product.TestedProductFactory;
import com.atlassian.webdriver.Link;
import com.atlassian.webdriver.browsers.pageobjects.AutoInstallWebDriverTester;
import com.atlassian.webdriver.pageobjects.WebDriverTester;
import com.atlassian.webdriver.product.Defaults;
import com.atlassian.webdriver.refapp.page.RefappAbstractPage;
import com.atlassian.webdriver.refapp.page.RefappAdminHomePage;
import com.atlassian.webdriver.refapp.page.RefappHomePage;
import com.atlassian.webdriver.refapp.page.RefappLoginPage;
import com.atlassian.webdriver.product.ProductInstance;
import org.openqa.selenium.By;

/**
 *
 */
@Defaults(instanceId = "refapp", contextPath = "/refapp", httpPort = 5990)
public class RefappTestedProduct implements TestedProduct<WebDriverTester, RefappHomePage, RefappAdminHomePage, RefappLoginPage>
{
    private final PageNavigator pageNavigator;
    private final WebDriverTester webDriverTester;
    private final ProductInstance productInstance;

    public RefappTestedProduct(com.atlassian.pageobjects.product.TestedProductFactory.TesterFactory<WebDriverTester> testerFactory, ProductInstance productInstance)
    {
        WebDriverTester tester = null;
        if (testerFactory == null)
        {
            tester = new AutoInstallWebDriverTester();
        }
        else
        {
            tester = testerFactory.create();
        }
        this.webDriverTester = tester;
        this.pageNavigator = new InjectPageNavigator<WebDriverTester>(this);
        this.productInstance = productInstance;
    }

    public RefappHomePage gotoHomePage()
    {
        return pageNavigator.gotoPageObject(RefappHomePage.class);
    }

    public RefappAdminHomePage gotoAdminHomePage()
    {
        return pageNavigator.gotoPageObject(RefappAdminHomePage.class);
    }

    public RefappLoginPage gotoLoginPage()
    {
        return pageNavigator.gotoPageObject(RefappLoginPage.class);
    }

    public static final void main(String[] args)
    {
        RefappTestedProduct product = TestedProductFactory.create(RefappTestedProduct.class);
        RefappLoginPage login = product.gotoLoginPage();
        login.loginAsAdmin();
        RefappAdminHomePage admin = product.gotoAdminHomePage();
        MyPage page = admin.gotoPage(new MyLink());
    }

    public static class MyLink extends Link<MyPage>
    {
        public MyLink()
        {
            super(By.id("my-link-id"), MyPage.class);
        }
    }

    public static class MyPage extends RefappAbstractPage<MyPage>
    {
        protected MyPage(RefappTestedProduct testedProduct)
        {
            super(testedProduct, "/mypage");
        }
    }
}
