package com.atlassian.webdriver.pageobjects.page;

import com.atlassian.pageobjects.page.User;

/**
 *
 */
public interface UserDiscoverable {
    boolean isLoggedIn();

    boolean isLoggedInAsUser(User user);

    boolean isAdmin();

}
