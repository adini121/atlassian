package com.atlassian.pageobjects;

import com.atlassian.pageobjects.page.Page;
import com.atlassian.pageobjects.product.TestedProduct;

/**
 * Represents a link that, when activated, will return a page or component.  It is assumed that the current page
 * when it is built will contain the link.
 */
public interface Link<P>
{
    /**
     * Activates the link
     * @return The new component or page representing the content activated
     */
    public P activate();
}
