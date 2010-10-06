package com.atlassian.webdriver.refapp.page;

import com.atlassian.webdriver.component.user.User;
import com.atlassian.webdriver.page.AbstractPage;
import com.atlassian.webdriver.page.PageObject;
import com.atlassian.webdriver.page.UserDiscoverable;
import com.atlassian.webdriver.product.TestedProduct;
import com.atlassian.webdriver.refapp.RefappTestedProduct;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public abstract class RefappAbstractPage<P extends PageObject> extends AbstractPage<RefappTestedProduct, P>
    implements UserDiscoverable
{
    private final String uri;

    public RefappAbstractPage(RefappTestedProduct testedProduct, String uri)
    {
        super(testedProduct);
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
        waitUntilLocated(By.className("refapp-footer"));
        return (P) this;
    }
}
