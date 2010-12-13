package com.atlassian.webdriver.refapp.page;

import com.atlassian.pageobjects.page.Page;
import com.atlassian.pageobjects.page.User;
import com.atlassian.webdriver.pageobjects.WebDriverTester;
import com.atlassian.webdriver.refapp.RefappTestedProduct;
import org.openqa.selenium.By;

import javax.inject.Inject;

public abstract class RefappAbstractPage implements Page<WebDriverTester>
{
    private final String uri;

    @Inject
    

    public RefappAbstractPage(RefappTestedProduct testedProduct, String uri)
    {
        this.uri = uri;
    }

    public boolean isLoggedIn()
    {
        return driver().findElement(By.id("login")).getText().equals("Logout");
    }

    public boolean isLoggedInAsUser(User user)
    {
        return isLoggedIn() && driver().findElement(By.id("user")).getText().contains(user.getFullName());
    }

    public boolean isAdmin()
    {
        return isLoggedIn() && driver().findElement(By.id("user")).getText().contains("(Sysadmin)");
    }

    public P get(boolean activated)
    {
        super.get(uri, activated);
        return (P) this;
    }

    @Override
    public void doWait()
    {
        testedProduct.getDriver().waitUntilElementIsLocated(By.className("refapp-footer"));
    }
}
