package com.atlassian.webdriver.pageobjects;

import com.atlassian.pageobjects.page.User;

/**
 *
 */
public interface UserDiscoverable {
    boolean isLoggedIn();

    boolean isLoggedInAsUser(User user);

    boolean isAdmin();

}
