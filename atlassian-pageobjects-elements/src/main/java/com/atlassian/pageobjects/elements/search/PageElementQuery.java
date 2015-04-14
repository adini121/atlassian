package com.atlassian.pageobjects.elements.search;

import com.atlassian.pageobjects.elements.PageElement;
import com.atlassian.pageobjects.elements.timeout.TimeoutType;
import com.google.common.base.Predicate;
import org.openqa.selenium.By;

import javax.annotation.Nonnull;

/**
 */
public interface PageElementQuery<E extends PageElement> extends GenericQuery<E, PageElementQuery<E>>
{

    @Nonnull
    PageElementQuery<E> by(@Nonnull By by);

    @Nonnull
    PageElementQuery<E> by(@Nonnull By by, @Nonnull Predicate<? super PageElement> filter);

    @Nonnull
    PageElementQuery<E> withTimeout(@Nonnull TimeoutType timeoutType);

    @Nonnull
    <PE extends E> PageElementQuery<PE> as(@Nonnull Class<PE> pageElementClass);
}
