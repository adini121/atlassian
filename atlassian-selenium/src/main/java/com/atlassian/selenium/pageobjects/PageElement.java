package com.atlassian.selenium.pageobjects;

import com.atlassian.selenium.SeleniumClient;

public class PageElement {
    protected final String locator;
    protected final SeleniumClient client;


    public PageElement(String locator, SeleniumClient client)
    {
        this.locator = locator;
        this.client = client;
    }

    public boolean isPresent()
    {
        return client.isElementPresent(locator);
    }

    public void click()
    {
        client.click(locator);
    }

    public String getLocator()
    {
        return locator;
    }

    public String toString()
    {
        return locator;
    }

    public String getText()
    {
        return client.getText(locator);
    }

    public int getPositionTop()
    {
        return client.getElementPositionTop(locator).intValue();
    }

    public int getPositionLeft()
    {
        return client.getElementPositionLeft(locator).intValue();
    }

    public int getWidth()
    {
        return client.getElementWidth(locator).intValue();
    }

    public int getHeight()
    {
        return client.getElementHeight(locator).intValue();
    }

    public void contextMenu(PageElement element)
    {
        client.contextMenu(element.getLocator());
    }

    public void doubleClick()
    {
        client.doubleClick(getLocator());
    }

    public void dragAndDrop(String movementsString)
    {
        client.dragAndDrop(getLocator(), movementsString);
    }

    public void dragAndDropToObject(PageElement dest)
    {
        client.dragAndDropToObject(getLocator(), dest.getLocator());
    }

    public void focus()
    {
        client.focus(getLocator());
    }

    public void getCursorPosition()
    {
        client.getCursorPosition(getLocator());
    }

    public Number getIndex()
    {
        return client.getElementIndex(getLocator());
    }


    public void highlight()
    {
        client.highlight(getLocator());
    }


    public void mouseDown()
    {
        client.mouseDown(getLocator());
    }

    public void mouseDownRight()
    {
        client.mouseDownRight(getLocator());
    }

    public void mouseMove()
    {
        client.mouseDown(getLocator());
    }

    public void mouseOut()
    {
        client.mouseOut(getLocator());
    }

    public void mouseOver()
    {
        client.mouseOver(getLocator());
    }

    public void mouseUp()
    {
        client.mouseUp(getLocator());
    }

    public void mouseUpRight()
    {
        client.mouseUpRight(getLocator());
    }

    public boolean isVisible()
    {
        return client.isElementPresent(getLocator()) && client.isVisible(getLocator());
    }

    public boolean isEditable()
    {
        return client.isEditable(getLocator());
    }

    public void keyDown(String keyCode)
    {
        client.keyDown(getLocator(), keyCode);
    }

    public void keyPress(String keyCode)
    {
        client.keyPress(getLocator(), keyCode);
    }

    public void keyUp(String keyCode)
    {
        client.keyUp(getLocator(), keyCode);
    }
    
    public void type(String value)
    {
        client.type(getLocator(), value);
    }

    public void typeKeys(String value)
    {
        client.typeKeys(getLocator(), value);
    }

    public void typeWithFullKeyEvents(String value)
    {
        client.typeWithFullKeyEvents(getLocator(), value);
    }    
}