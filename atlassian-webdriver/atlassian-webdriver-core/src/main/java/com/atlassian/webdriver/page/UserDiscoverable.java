package com.atlassian.webdriver.page;

import com.atlassian.webdriver.component.user.User;

/**
 *
 */
public interface UserDiscoverable {
    boolean isLoggedIn();

    boolean isLoggedInAsUser(User user);

    boolean isAdmin();

}
