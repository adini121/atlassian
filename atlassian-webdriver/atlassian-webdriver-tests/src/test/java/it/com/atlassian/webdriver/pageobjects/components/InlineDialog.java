package it.com.atlassian.webdriver.pageobjects.components;

import com.atlassian.pageobjects.PageBinder;
import com.atlassian.pageobjects.binder.Init;
import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.pageobjects.component.ActivatedComponent;
import com.atlassian.webdriver.pageobjects.element.Element;
import it.com.atlassian.webdriver.pageobjects.components.aui.AuiInlineDialog;
import org.openqa.selenium.By;

import javax.inject.Inject;

/**
 * Represents the inline dialog that is shown when clicking a link on the AuiPage. This page is generated by the
 * AUIServlet plugin that is installed in the RefApp
 */
public class InlineDialog implements ActivatedComponent<InlineDialog>
{
    @Inject
    private AtlassianWebDriver driver;

    @Inject
    private PageBinder pageBinder;

    private AuiInlineDialog dialog;

    @Init
    public void initialize()
    {
         dialog = pageBinder.bind(AuiInlineDialog.class, By.id("popupLink"), "1");
    }

    public String content()
    {
        return view().find(By.id("dialog-content")).text();
    }


    public Element trigger()
    {
        return dialog.trigger();
    }

    public Element view()
    {
        return dialog.view();
    }

    public InlineDialog open()
    {
        dialog.open();
        return this;
    }

    public boolean isOpen()
    {
        return dialog.isOpen();
    }
}