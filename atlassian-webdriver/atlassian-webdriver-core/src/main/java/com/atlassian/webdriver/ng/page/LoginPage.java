package com.atlassian.webdriver.ng.page;

import com.atlassian.webdriver.ng.ProductInstance;
import com.atlassian.webdriver.ng.TestedProduct;
import com.atlassian.webdriver.page.WebDriverPage;
import org.openqa.selenium.WebDriver;

/**
 *
 */
public abstract class LoginPage<M extends AbstractPage> extends AbstractPage
{
    protected LoginPage(TestedProduct testedProduct)
    {
        super(testedProduct);
    }

    public abstract M login(String username, String password);

    public M loginAsAdmin()
    {
        return login("admin", "admin");
    }
}
