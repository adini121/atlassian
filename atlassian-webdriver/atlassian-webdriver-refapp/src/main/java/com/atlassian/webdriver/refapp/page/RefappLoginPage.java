package com.atlassian.webdriver.refapp.page;

import com.atlassian.webdriver.component.user.User;
import com.atlassian.webdriver.page.HomePage;
import com.atlassian.webdriver.page.LoginPage;
import com.atlassian.webdriver.product.TestedProduct;
import com.atlassian.webdriver.page.PageObject;
import com.atlassian.webdriver.refapp.RefappTestedProduct;
import com.atlassian.webdriver.refapp.page.RefappHomePage;
import org.openqa.selenium.By;

/**
 *
 */
public class RefappLoginPage extends RefappAbstractPage<RefappLoginPage> implements LoginPage<RefappTestedProduct,
        RefappLoginPage, RefappHomePage>
{
    protected RefappLoginPage(RefappTestedProduct testedProduct)
    {
        super(testedProduct, "/plugins/servlet/login");
    }

    public RefappHomePage login(String username, String password)
    {
        driver().findElement(By.name("os_username")).sendKeys(username);
        driver().findElement(By.name("os_password")).sendKeys(password);
        driver().findElement(By.id("os_login")).submit();
        return new RefappHomePage(getTestedProduct()).get(true);
    }

    public RefappHomePage loginAsAdmin() {
        return login("admin", "admin");
    }
}
