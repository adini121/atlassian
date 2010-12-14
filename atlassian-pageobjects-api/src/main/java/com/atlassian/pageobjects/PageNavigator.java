package com.atlassian.pageobjects;

import com.atlassian.pageobjects.component.Component;
import com.atlassian.pageobjects.page.Page;

/**
 *
 */
public interface PageNavigator
{
    <P extends Page> P gotoPage(Class<P> pageClass, Object... args);
    <P extends Page> P getPage(Class<P> pageClass, Object... args);

    <C extends Component> C getComponent(Class<C> componentClass, Object... args);

    <P extends PageObject> void override(Class<P> oldClass, Class<? extends P> newClass);

    public <P extends Page, L extends Link<P>> L createLink(Class<L> myLinkClass);
}
