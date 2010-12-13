package com.atlassian.webdriver.refapp.page;

import com.atlassian.pageobjects.navigator.ValidateLocation;
import com.atlassian.webdriver.utils.user.User;
import com.atlassian.webdriver.page.LoginPage;
import com.atlassian.webdriver.refapp.RefappTestedProduct;
import org.openqa.selenium.By;

/**
 *
 */
public class RefappLoginPage extends RefappAbstractPage<RefappLoginPage> implements LoginPage<RefappTestedProduct,
        RefappLoginPage, RefappHomePage>
{
    public RefappLoginPage(RefappTestedProduct testedProduct)
    {
        super(testedProduct, "/plugins/servlet/login");
    }

    public RefappHomePage login(User user)
    {
        driver().findElement(By.name("os_username")).sendKeys(user.getUsername());
        driver().findElement(By.name("os_password")).sendKeys(user.getPassword());
        driver().findElement(By.id("os_login")).submit();
        return getTestedProduct().gotoPage(RefappHomePage.class, true);
    }

    public RefappHomePage loginAsAdmin() {

        return login(new User("admin", "admin", "fullname", "email"));
    }

    @ValidateLocation
    public void doWait()
    {
        testedProduct.getDriver().waitUntilElementIsLocated(By.className("refapp-footer"));
    }
}
