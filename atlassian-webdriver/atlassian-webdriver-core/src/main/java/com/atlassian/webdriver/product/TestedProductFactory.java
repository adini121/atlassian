package com.atlassian.webdriver.product;

import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.ng.product.RefappAdminHomePage;
import com.atlassian.webdriver.ng.product.RefappLoginPage;
import com.atlassian.webdriver.ng.product.RefappTestedProduct;
import com.atlassian.webdriver.page.AbstractPage;
import com.atlassian.webdriver.page.Link;
import com.atlassian.webdriver.page.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 *
 */
public class TestedProductFactory
{
    public static <P extends TestedProduct> P create(ProductInstance<P> instance)
    {
        return create(instance, AtlassianWebDriver.getDriver());
    }

    public static <P extends TestedProduct> P create(ProductInstance<P> instance, WebDriver webDriver)
    {
        switch (instance.getProductType())
        {
            case REFAPP: return (P) new RefappTestedProduct(webDriver, instance);
            default : throw new RuntimeException();
        }
    }

    public static final void main(String[] args)
    {
        RefappTestedProduct product = TestedProductFactory.create(ProductInstance.REFAPP);
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

    public static class MyPage extends AbstractPage
    {
        protected MyPage(TestedProduct testedProduct)
        {
            super(testedProduct);
        }

        public PageObject get(boolean activated)
        {
            return null;
        }
    }
}
