package it.com.atlassian.webdriver.pageobjects.page;

import com.atlassian.pageobjects.binder.WaitUntil;
import com.atlassian.webdriver.pageobjects.AbstractWebDriverPage;
import it.com.atlassian.webdriver.pageobjects.components.RefappHeader;
import org.openqa.selenium.By;

public abstract class RefappAbstractPage extends AbstractWebDriverPage
{
    @WaitUntil
    public void doWait()
    {
        driver.waitUntilElementIsLocated(By.className("refapp-footer"));
    }

     public RefappHeader getHeader()
    {
        return pageBinder.bind(RefappHeader.class);
    }
}
