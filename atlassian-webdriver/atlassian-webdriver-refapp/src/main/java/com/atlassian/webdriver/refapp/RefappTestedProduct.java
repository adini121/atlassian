package com.atlassian.webdriver.refapp;

import com.atlassian.webdriver.Link;
import com.atlassian.webdriver.product.Defaults;
import com.atlassian.webdriver.product.TestedProductFactory;
import com.atlassian.webdriver.refapp.page.RefappAbstractPage;
import com.atlassian.webdriver.refapp.page.RefappAdminHomePage;
import com.atlassian.webdriver.refapp.page.RefappHomePage;
import com.atlassian.webdriver.refapp.page.RefappLoginPage;
import com.atlassian.webdriver.product.AbstractTestedProduct;
import com.atlassian.webdriver.product.ProductInstance;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 *
 */
@Defaults(contextPath = "/refapp", httpPort = 5990)
public class RefappTestedProduct extends AbstractTestedProduct<RefappHomePage, RefappAdminHomePage, RefappLoginPage>
{
    public RefappTestedProduct(WebDriver webDriver, ProductInstance productInstance)
    {
        super(webDriver, productInstance);
    }

    public RefappHomePage gotoHomePage()
    {
        return gotoPage(RefappHomePage.class);
    }

    public RefappAdminHomePage gotoAdminHomePage()
    {
        return gotoPage(RefappAdminHomePage.class);
    }

    public RefappLoginPage gotoLoginPage()
    {
        return gotoPage(RefappLoginPage.class);
    }

    public static final void main(String[] args)
    {
        RefappTestedProduct product = TestedProductFactory.create(RefappTestedProduct.class, "refapp");
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
